/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.codegenerator;

import static org.junit.Assert.assertFalse;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.junit.Test;
import org.parboiled.BaseParser;
import org.parboiled.Parboiled;
import org.parboiled.Rule;
import org.parboiled.errors.ErrorUtils;
import org.parboiled.parserunners.RecoveringParseRunner;
import org.parboiled.support.ParsingResult;

import com.github.uscexp.grappa.extension.exception.PegParserGeneratorException;
import com.github.uscexp.grappa.extension.parser.peg.PegParser;

/**
 * @author  haui
 */
public class PegParserGeneratorTest {

	private static final String PEG_INPUT_PATH = "PEG.peg";
	private static final String SOURCE_OUTPUT_PATH = "target";
	private static final String TEST_PARSER_CLASS = "com.github.uscexp.grappa.extension.codegenerator.testparser.TestPegParser";
	private static final String TEST_GENERIC_TYPE_CLASS = "java.lang.String";

	private PegParserGenerator pegParserGeneratorSUT = new PegParserGenerator();

	@Test
	public void testGenerateParserStringStringStringCharset()
		throws Exception {
		File rootFile = new File(SOURCE_OUTPUT_PATH);
		URL url = this.getClass().getClassLoader().getResource(PEG_INPUT_PATH);
		URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] { rootFile.toURI().toURL() }, getClass().getClassLoader());
		Thread.currentThread().setContextClassLoader(classLoader);

		pegParserGeneratorSUT.generateParser(TEST_PARSER_CLASS, TEST_GENERIC_TYPE_CLASS, SOURCE_OUTPUT_PATH, url.getFile(), null);

		String pathname = SOURCE_OUTPUT_PATH + "/" + TEST_PARSER_CLASS.replace('.', '/');
		File file = new File(pathname + ".java");
		List<File> files = new ArrayList<>();
		files.add(file);
		// Compile source file.
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);
		Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(files);
		compiler.getTask(null, fileManager, diagnostics, null, null, compilationUnits).call();
		for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
			System.out.format("Error on line %d in %s%n", diagnostic.getLineNumber(), diagnostic.getSource().toUri());
			System.out.format("Message: %s", diagnostic.getMessage(Locale.getDefault()));
		}
		fileManager.close();

		// Load and instantiate compiled class.
		Class<? extends BaseParser<?>> cls = (Class<? extends BaseParser<?>>) Class.forName(TEST_PARSER_CLASS, true, classLoader);
		
		BaseParser<?> parser = Parboiled.createParser(cls);

		Method method = parser.getClass().getDeclaredMethod("grammar", (Class<?>[]) null);

		RecoveringParseRunner<PegParser> recoveringParseRunner = new RecoveringParseRunner<>((Rule) method.invoke(parser, (Object[]) null));

		String input = null;

		Charset encoding = Charset.defaultCharset();

		File inputFile = new File(url.getFile());

		byte[] encoded = Files.readAllBytes(Paths.get(inputFile.getAbsolutePath()));
		input = new String(encoded, encoding);

		ParsingResult<PegParser> parsingResult = recoveringParseRunner.run(input);

		if (parsingResult.hasErrors()) {
			throw new PegParserGeneratorException(String.format("PEG definition parse error(s): %s",
					ErrorUtils.printParseErrors(parsingResult)));
		}
		assertFalse(parsingResult.hasErrors());
		
		// clean up
		file.delete();
		file = new File(pathname + ".class");
		file.delete();
		
		int idx = -1;
		
		
		while ((idx = pathname.lastIndexOf('/')) != -1) {
			pathname = pathname.substring(0, idx);
			file = new File(pathname);
			file.delete();
		}
	}

	@Test(expected = PegParserGeneratorException.class)
	public void testGenerateParserFileInputError()
		throws Exception {
		pegParserGeneratorSUT.generateParser(TEST_PARSER_CLASS, TEST_GENERIC_TYPE_CLASS, SOURCE_OUTPUT_PATH, "nonexistent", null);
	}

	@Test(expected = PegParserGeneratorException.class)
	public void testGenerateParserInputError()
		throws Exception {
		pegParserGeneratorSUT.generateParser(TEST_PARSER_CLASS, TEST_GENERIC_TYPE_CLASS, SOURCE_OUTPUT_PATH, "a - bc");
	}
}
