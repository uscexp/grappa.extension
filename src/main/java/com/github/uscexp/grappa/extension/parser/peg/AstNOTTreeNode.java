/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.parser.peg;

import org.parboiled.Node;

/**
 * Command implementation for the <code>PegParser</code> rule: NOT.
 */
public class AstNOTTreeNode<V> extends AstPegBaseTreeNode<V> {

	public AstNOTTreeNode(Node<?> node, String value) {
		super(node, value);
	}

	@Override
	protected void interpret(Long id)
		throws ReflectiveOperationException {
		super.interpret(id);
		String param = (String) closeProcessStore.getStack().pop();
		closeProcessStore.getStack().push("testNot(" + param + ")");
	}

}
