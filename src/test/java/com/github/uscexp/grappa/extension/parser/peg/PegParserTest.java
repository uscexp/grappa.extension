/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.parser.peg;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.github.fge.grappa.Grappa;
import com.github.uscexp.grappa.extension.nodes.AstTreeNode;
import com.github.uscexp.grappa.extension.parser.Parser;


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

		PegParser parser = Grappa.createParser(PegParser.class);
		AstTreeNode<String> rootNode = Parser.parseInput(PegParser.class, parser.grammar(), input);
		
		assertNotNull(rootNode);

		System.out.println("Root node text: " + rootNode.getValue());
	}
}
