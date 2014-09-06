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

import org.parboiled.Parboiled;
import org.parboiled.errors.ErrorUtils;
import org.parboiled.parserunners.RecoveringParseRunner;
import org.parboiled.support.ParsingResult;

import com.github.uscexp.grappa.extension.exception.AstInterpreterException;
import com.github.uscexp.grappa.extension.exception.PegParserGeneratorException;
import com.github.uscexp.grappa.extension.interpreter.AstInterpreter;
import com.github.uscexp.grappa.extension.interpreter.ProcessStore;
import com.github.uscexp.grappa.extension.parser.peg.PegParser;
import com.google.common.base.Preconditions;
import com.sun.codemodel.JCodeModel;

/**
 * @author haui
 *
 */
public class PegParserGenerator {

	public static final String CODE_MODEL = "codeModel";
	//      private static Logger logger = Logger.getLogger(PegParserGenerator.class.getName());

	public void generateParser(String parserClassString, String sourceOutputPath, String pegInputPath, Charset encoding)
		throws PegParserGeneratorException, AstInterpreterException {
		String input = null;

		if (encoding == null)
			encoding = Charset.defaultCharset();

		File file = new File(pegInputPath);

		if (!file.exists()) {
			throw new PegParserGeneratorException(String.format("File %s does not exist!", pegInputPath));
		}

		try {
			byte[] encoded = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
			input = new String(encoded, encoding);
		} catch (IOException e) {
			throw new PegParserGeneratorException(String.format("File %s couldn't be read!", pegInputPath), e);
		}

		generateParser(parserClassString, sourceOutputPath, input);
	}

	public void generateParser(String parserClassString, String sourceOutputPath, String pegInput)
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
		try {
			ProcessStore<String> processStore = ProcessStore.getInstance(id);
			// set the result object
			processStore.setNewVariable(CODE_MODEL, codeModel);
			astInterpreter.execute(parser.getClass(), parsingResult, id);
		} finally {
			astInterpreter.cleanUp(id);
		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
