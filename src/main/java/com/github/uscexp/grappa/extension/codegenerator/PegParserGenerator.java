/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.codegenerator;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.parboiled.BaseParser;
import org.parboiled.Parboiled;
import org.parboiled.annotations.BuildParseTree;
import org.parboiled.errors.ErrorUtils;
import org.parboiled.parserunners.RecoveringParseRunner;
import org.parboiled.support.ParsingResult;

import com.github.uscexp.grappa.extension.exception.AstInterpreterException;
import com.github.uscexp.grappa.extension.exception.PegParserGeneratorException;
import com.github.uscexp.grappa.extension.interpreter.AstInterpreter;
import com.github.uscexp.grappa.extension.interpreter.ProcessStore;
import com.github.uscexp.grappa.extension.parser.peg.PegParser;
import com.google.common.base.Preconditions;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JDocComment;

/**
 * The {@link PegParserGenerator} generates a {@link BaseParser} extended parser java class file, from a given Parser Expression Grammar (PEG) file.
 * The allowed PEG syntax is based on Bryan Ford's definitions (http://bford.info/pub/lang/peg).
 * 
 * @author  haui
 */
public class PegParserGenerator {

	private static Logger logger = Logger.getLogger(PegParserGenerator.class.getName());

	public static final long CLOSE = "close".hashCode();
	public static final long OPEN = "open".hashCode();
	public static final String CODE_MODEL = "codeModel";
	public static final String DEFINED_CLASS = "definedClass";
	private File file = null;

	public void generateParser(String parserClassString, String genericTypeName, String sourceOutputPath, String pegInputPath, Charset encoding)
		throws PegParserGeneratorException, AstInterpreterException {
		String input = null;

		if (encoding == null)
			encoding = Charset.defaultCharset();

		file  = new File(pegInputPath);

		if (!file .exists()) {
			throw new PegParserGeneratorException(String.format("File %s does not exist!", pegInputPath));
		}

		try {
			byte[] encoded = Files.readAllBytes(Paths.get(file .getAbsolutePath()));
			input = new String(encoded, encoding);
		} catch (IOException e) {
			throw new PegParserGeneratorException(String.format("File %s couldn't be read!", pegInputPath), e);
		}

		generateParser(parserClassString, genericTypeName, sourceOutputPath, input);
	}

	public void generateParser(String parserClassString, String genericTypeName, String sourceOutputPath, String pegInput)
		throws PegParserGeneratorException, AstInterpreterException {
		Preconditions.checkNotNull(pegInput);

		JCodeModel codeModel = new JCodeModel();
		PegParser parser = Parboiled.createParser(PegParser.class);

		RecoveringParseRunner<PegParser> recoveringParseRunner = new RecoveringParseRunner<>(parser.grammar());

		ParsingResult<PegParser> parsingResult = recoveringParseRunner.run(pegInput);

		if (parsingResult.hasErrors()) {
			throw new PegParserGeneratorException(String.format("PEG definition parse error(s): %s",
					ErrorUtils.printParseErrors(parsingResult)));
		}

		AstInterpreter<String> astInterpreter = new AstInterpreter<>();

		Long id = new Date().getTime();
		Long idOpen = id + OPEN;
		Long idClose = id + CLOSE;
		try {
			ProcessStore<String> processStore = ProcessStore.getInstance(id);
			ProcessStore.getInstance(idOpen);
			ProcessStore.getInstance(idClose);
			JDefinedClass definedClass = codeModel._class(parserClassString);
			Class<?> genericClass = null;
			try {
				genericClass = Class.forName(genericTypeName);
			} catch (ClassNotFoundException e) {
				genericClass = null;
			}
			JClass genericType = codeModel.ref(genericTypeName);
			if(genericClass == null) {
				genericType = codeModel.ref(genericTypeName);
			} else {
				genericType = codeModel.ref(genericClass);
			}
			JClass superClass = codeModel.ref(BaseParser.class).narrow(genericType);
			definedClass._extends(superClass);
			definedClass.annotate(BuildParseTree.class);
			JDocComment javadoc = definedClass.javadoc();
			String filename = file != null ? "'" + file.getName() + "'" : "";
			String lastWord = file != null ? "input file" : "input string";
			String jdComment = String.format("Generated {@link BaseParser} implementation form %s PEG %s.", filename, lastWord);
			javadoc.add(jdComment);
			javadoc.add("\n\n@author PegParserGenerator");
			// set the result object
			processStore.setNewVariable(CODE_MODEL, codeModel);
			processStore.setNewVariable(DEFINED_CLASS, definedClass);
			
			astInterpreter.execute(parser.getClass(), parsingResult, id);
			
			codeModel.build(new File(sourceOutputPath));
		} catch (JClassAlreadyExistsException | IOException e) {
			throw new PegParserGeneratorException("PEG parser generator unexpected error", e);
		} finally {
			astInterpreter.cleanUp(id);
		}

	}

	public static void main(String[] args) {
		try {
			PegParserGenerator pegParserGenerator = new PegParserGenerator();

			String parserClassString = args[0];
			String genericTypeName = args[1];
			String sourceOutputPath = args[2];
			String pegFileInputPath = args[3];
			Charset inputFileEncoding = null;
			
			if(args.length == 5) {
				inputFileEncoding = Charset.forName(args[4]);
			}
			pegParserGenerator.generateParser(parserClassString, genericTypeName, sourceOutputPath, pegFileInputPath, inputFileEncoding);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Unexpected error occured", e);

			logger.log(Level.SEVERE,
				"Syntax: java -cp <dependencies> com.github.uscexp.grappa.extension.codegenerator.PegParserGenerator parserClassString genericTypeName sourceOutputPath pegFileInputPath <inputFileEncoding>");
		}
	}

}
