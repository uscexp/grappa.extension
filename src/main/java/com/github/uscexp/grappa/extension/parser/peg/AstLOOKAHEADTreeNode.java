/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.parser.peg;

import java.util.Stack;

import org.parboiled.Node;

/**
 * Command implementation for the <code>PegParser</code> rule: LOOKAHEAD.
 */
public class AstLOOKAHEADTreeNode<V> extends AstPegBaseTreeNode<V> {
	public AstLOOKAHEADTreeNode(Node<?> node, String value) {
		super(node, value);
	}

	@Override
	protected void interpretAfterChilds(Long id) throws ReflectiveOperationException {
		super.interpretAfterChilds(id);
		Stack<Object> stack = this.processStore.getTierStack();
		String param = (String) stack.pop();
		stack.push("test(" + param + ")");

		lastTreeNode = this;
	}
}
