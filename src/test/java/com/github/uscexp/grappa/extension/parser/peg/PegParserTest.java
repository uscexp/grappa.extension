/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.parser.peg;

import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.parboiled.Parboiled;
import org.parboiled.errors.ErrorUtils;
import org.parboiled.parserunners.RecoveringParseRunner;
import org.parboiled.support.ParsingResult;

import com.github.uscexp.grappa.extension.parser.peg.PegParser;


/**
 * @author haui
 *
 */
public class PegParserTest {

	@Test
	public void test() {
		
		String input = "S <- &(A c) a+ B !(a/b/c)\n" + 
				"A <- a A? b\n" + 
				"B <- b B? c";

		PegParser parser = Parboiled.createParser(PegParser.class);
		RecoveringParseRunner<PegParser> recoveringParseRunner = new RecoveringParseRunner<>(parser.grammar());
		
		ParsingResult<PegParser> parsingResult = recoveringParseRunner.run(input);
		
		if(parsingResult.hasErrors()) {
			System.err.println(String.format("Input parse error(s): %s", ErrorUtils.printParseErrors(parsingResult)));
		}
		
		assertFalse(parsingResult.hasErrors());
		
	}
}
