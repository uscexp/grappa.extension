/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.parser.peg;

import java.util.Stack;

import org.parboiled.Node;

/**
 * Command implementation for the <code>PegParser</code> rule: NOT.
 */
public class AstNOTTreeNode<V> extends AstPegBaseTreeNode<V> {
	public AstNOTTreeNode(Node<?> node, String value) {
		super(node, value);
	}

	@Override
	protected void interpretAfterChilds(Long id) throws ReflectiveOperationException {
		super.interpretAfterChilds(id);
		Stack<Object> stack = this.processStore.getTierStack();
		String param = (String) stack.pop();
		stack.push("testNot(" + param + ")");

		lastTreeNode = this;
	}
}
