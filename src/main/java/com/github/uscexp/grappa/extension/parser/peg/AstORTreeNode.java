/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.parser.peg;

import java.util.Stack;

import org.parboiled.Node;

/**
 * Command implementation for the <code>PegParser</code> rule: OR.
 */
public class AstORTreeNode<V> extends AstPegBaseTreeNode<V> {
	public static final String FIRST_OF = "firstOf";

	public AstORTreeNode(Node<?> node, String value) {
		super(node, value);
	}

	@Override
	protected void interpretAfterChilds(Long id) throws ReflectiveOperationException {
		super.interpretAfterChilds(id);
		Stack<Object> openStack = this.openProcessStore.getTierStack();
		if ((openStack.isEmpty()) || (!"firstOf".equals(openStack.peek()))) {
			openStack.push("firstOf");
		}
		if (!this.tearedUp) {
			this.processStore.tierOneUp(true);
			this.openProcessStore.tierOneUp(true);
		}
		lastTreeNode = this;
	}
}
