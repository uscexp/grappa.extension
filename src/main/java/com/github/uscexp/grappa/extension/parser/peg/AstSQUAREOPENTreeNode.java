/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.parser.peg;

import java.util.Stack;

import org.parboiled.Node;

/**
 * Command implementation for the <code>PegParser</code> rule: SQUAREOPEN.
 */
public class AstSQUAREOPENTreeNode<V> extends AstPegBaseTreeNode<V> {
	public AstSQUAREOPENTreeNode(Node<?> node, String value) {
		super(node, value);
	}

	@Override
	protected void interpretAfterChilds(Long id) throws ReflectiveOperationException {
		super.interpretAfterChilds(id);
		Stack<Object> stack = this.processStore.getTierStack();

		String rule = "";
		String stackValue = "";

		int i = 0;
		while (!stack.isEmpty()) {
			stackValue = (String) stack.pop();
			if (i > 0) {
				rule = rule + ", ";
			}
			rule = rule + stackValue;
			i++;
		}
		if (i > 1) {
			rule = "firstOf(" + rule + ")";
		}
		this.processStore.tierOneDown(true);
		this.openProcessStore.tierOneDown(true);
		this.processStore.getTierStack().push(rule);

		lastTreeNode = this;
	}
}
