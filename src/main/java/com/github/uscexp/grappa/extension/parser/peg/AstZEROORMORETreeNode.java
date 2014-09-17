/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.parser.peg;

import org.parboiled.Node;

/**
 * Command implementation for the <code>PegParser</code> rule: ZEROORMORE.
 */
public class AstZEROORMORETreeNode<V> extends AstPegBaseTreeNode<V> {

	public static final String ZERO_OR_MORE = "zeroOrMore";
	public static final String START_ZERO_OR_MORE = "#zeroOrMore#";

	public AstZEROORMORETreeNode(Node<?> node, String value) {
		super(node, value);
	}

	@Override
	protected void interpret(Long id)
		throws ReflectiveOperationException {
		super.interpret(id);
		openProcessStore.getStack().push(ZERO_OR_MORE);
		closeProcessStore.getStack().push(START_ZERO_OR_MORE);
	}

}
