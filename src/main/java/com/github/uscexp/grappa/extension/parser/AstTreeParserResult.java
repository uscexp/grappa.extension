/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.parser;

import com.github.fge.grappa.run.ParsingResult;
import com.github.uscexp.grappa.extension.nodes.AstTreeNode;

/**
 * @author haui
 *
 */
public class AstTreeParserResult<V> {

	private ParsingResult<V> parsingResult;
	
	private AstTreeNode<V> rootNode;
	
	public AstTreeParserResult(ParsingResult<V> parsingResult, AstTreeNode<V> rootNode) {
		super();
		this.parsingResult = parsingResult;
		this.rootNode = rootNode;
	}

	public ParsingResult<V> getParsingResult() {
		return parsingResult;
	}

	public AstTreeNode<V> getRootNode() {
		return rootNode;
	}
}
