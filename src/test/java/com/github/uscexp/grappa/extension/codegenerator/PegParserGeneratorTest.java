/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.codegenerator;

import java.net.URL;

import org.junit.Test;

/**
 * @author haui
 *
 */
public class PegParserGeneratorTest {
	
	private static final String PEG_INPUT_PATH = "PEG.peg";
	private static final String SOURCE_OUTPUT_PATH = "target";
	private static final String TEST_PARSER_CLASS = "com.github.uscexp.grappa.extension.codegenerator.testparser.TestPegParser";
	
	private PegParserGenerator pegParserGeneratorSUT = new PegParserGenerator();

	@Test
	public void testGenerateParserStringStringStringCharset() throws Exception {
		URL url = this.getClass().getClassLoader().getResource(PEG_INPUT_PATH);
		pegParserGeneratorSUT.generateParser(TEST_PARSER_CLASS, SOURCE_OUTPUT_PATH, url.getFile(), null);
	}

}
