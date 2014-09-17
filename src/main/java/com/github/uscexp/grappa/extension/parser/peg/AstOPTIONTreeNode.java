/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.parser.peg;

import org.parboiled.Node;

/**
 * Command implementation for the <code>PegParser</code> rule: OPTION.
 */
public class AstOPTIONTreeNode<V> extends AstPegBaseTreeNode<V> {

	public static final String OPTION = "option";
	public static final String START_OPTION = "#option#";

	public AstOPTIONTreeNode(Node<?> node, String value) {
		super(node, value);
	}

	@Override
	protected void interpret(Long id)
		throws ReflectiveOperationException {
		super.interpret(id);
		openProcessStore.getStack().push(OPTION);
		closeProcessStore.getStack().push(START_OPTION);
	}

}
