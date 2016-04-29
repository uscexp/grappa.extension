/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.parser.peg;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.github.fge.grappa.Grappa;
import com.github.fge.grappa.run.ListeningParseRunner;
import com.github.fge.grappa.run.ParsingResult;
import com.github.uscexp.grappa.extension.nodes.AstTreeNode;
import com.github.uscexp.grappa.extension.nodes.treeconstruction.AstTreeNodeBuilder;


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
		final AstTreeNodeBuilder<String> treeNodeBuilder = new AstTreeNodeBuilder<String>(PegParser.class, false);
		ListeningParseRunner<String> parseRunner = new ListeningParseRunner<>(parser.grammar());
		
		parseRunner.registerListener(treeNodeBuilder);

		ParsingResult<String> parsingResult = parseRunner.run(input);
		
		assertNotNull(parsingResult);
		
		AstTreeNode<String> root = treeNodeBuilder.getRootNode();
		
		assertNotNull(root);

		System.out.println("Root node text: " + root.getValue());
		
		assertNotNull(parsingResult);
		
	}
}
