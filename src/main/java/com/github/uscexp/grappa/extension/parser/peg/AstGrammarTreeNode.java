/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.parser.peg;

import java.util.logging.Logger;

import org.parboiled.Node;

/**
 * Command implementation for the <code>PegParser</code> rule: grammar.
 */
public class AstGrammarTreeNode<V> extends AstPegBaseTreeNode<V> {

	private static Logger logger = Logger.getLogger(AstGrammarTreeNode.class.getName());

	public AstGrammarTreeNode(Node<?> node, String value) {
		super(node, value);
	}

	@Override
	protected void interpret(Long id)
		throws ReflectiveOperationException {
		super.interpret(id);
		logger.info("create class");
	}

}
