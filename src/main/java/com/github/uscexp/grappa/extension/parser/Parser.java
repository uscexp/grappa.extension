/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.parser;

import com.github.fge.grappa.exceptions.InvalidGrammarException;
import com.github.fge.grappa.parsers.BaseParser;
import com.github.fge.grappa.rules.Rule;
import com.github.fge.grappa.run.ListeningParseRunner;
import com.github.uscexp.grappa.extension.nodes.AstTreeNode;
import com.github.uscexp.grappa.extension.nodes.treeconstruction.AstTreeNodeBuilder;

/**
 * @author haui
 *
 */
public class Parser {
	
	private Parser() {}
	
	public static <V> AstTreeNode<V> parseInput(Class<? extends BaseParser<V>> parserClass, Rule rule, String input) {
		return parseInput(parserClass, rule, input, false);
	}
	
	public static <V> AstTreeNode<V> parseInput(Class<? extends BaseParser<V>> parserClass, Rule rule, String input, boolean removeAstNopTreeNodes) {
		final AstTreeNodeBuilder<V> treeNodeBuilder = new AstTreeNodeBuilder<V>(parserClass, removeAstNopTreeNodes);
		
		final ListeningParseRunner<V> parseRunner = new ListeningParseRunner<>(rule);
		
		parseRunner.registerListener(treeNodeBuilder);

		parseRunner.run(input);
		
		if(treeNodeBuilder.getRootNode() == null) {
			String errors = treeNodeBuilder.getParsingErrors();
			String errorMessage = String.format("Error parsing input: %s", errors);
			throw new InvalidGrammarException(errorMessage);
		}
		
		return treeNodeBuilder.getRootNode();
	}
}
