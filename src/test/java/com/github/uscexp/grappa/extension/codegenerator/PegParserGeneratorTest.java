/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.codegenerator;

import static org.junit.Assert.assertNotNull;

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

import com.github.fge.grappa.Grappa;
import com.github.fge.grappa.exceptions.InvalidGrammarException;
import com.github.fge.grappa.parsers.BaseParser;
import com.github.fge.grappa.rules.Rule;
import com.github.uscexp.grappa.extension.exception.PegParserGeneratorException;
import com.github.uscexp.grappa.extension.nodes.AstTreeNode;
import com.github.uscexp.grappa.extension.parser.Parser;

/**
 * @author haui
 */
public class PegParserGeneratorTest {

	private static final String PEG_INPUT_PATH = "PEG.peg";
	private static final String SOURCE_OUTPUT_PATH = "target";
	private static final String TEST_PARSER_CLASS = "com.github.uscexp.grappa.extension.codegenerator.testparser.TestPegParser";
	private static final String TEST_GENERIC_TYPE_CLASS = "java.lang.String";

	private PegParserGenerator pegParserGeneratorSUT = new PegParserGenerator();

	@Test
	public void testGenerateParserFromStringFromFileStringStringStringCharset() throws Exception {
		File rootFile = new File(SOURCE_OUTPUT_PATH);
		URL url = this.getClass().getClassLoader().getResource(PEG_INPUT_PATH);
		URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] { rootFile.toURI().toURL() }, getClass().getClassLoader());
		Thread.currentThread().setContextClassLoader(classLoader);

		pegParserGeneratorSUT.generateParserFromFile(TEST_PARSER_CLASS, TEST_GENERIC_TYPE_CLASS, SOURCE_OUTPUT_PATH, url.getFile(), null);

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
		@SuppressWarnings("unchecked")
		Class<? extends BaseParser<String>> cls = (Class<? extends BaseParser<String>>) Class.forName(TEST_PARSER_CLASS, true, classLoader);

		@SuppressWarnings("rawtypes")
		BaseParser parser = Grappa.createParser(cls);

		Method method = parser.getClass().getDeclaredMethod("grammar", (Class<?>[]) null);

		String input = null;

		Charset encoding = Charset.defaultCharset();

		File inputFile = new File(url.getFile());

		byte[] encoded = Files.readAllBytes(Paths.get(inputFile.getAbsolutePath()));
		input = new String(encoded, encoding);

		AstTreeNode<String> rootNode = Parser.parseInput(cls, (Rule) method.invoke(parser, (Object[]) null), input, true);

		assertNotNull(rootNode);
		
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
	public void testGenerateParserFromStringFromFileFileInputError() throws Exception {
		pegParserGeneratorSUT.generateParserFromFile(TEST_PARSER_CLASS, TEST_GENERIC_TYPE_CLASS, SOURCE_OUTPUT_PATH, "nonexistent", null);
	}

	@Test(expected = InvalidGrammarException.class)
	public void testGenerateParserFromStringFromFileInputError() throws Exception {
		pegParserGeneratorSUT.generateParserFromString(TEST_PARSER_CLASS, TEST_GENERIC_TYPE_CLASS, SOURCE_OUTPUT_PATH, "a - bc");
	}
}
