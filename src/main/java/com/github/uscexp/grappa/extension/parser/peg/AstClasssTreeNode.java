/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.parser.peg;

import java.util.Stack;

/**
 * Command implementation for the <code>PegParser</code> rule: classs.
 */
public class AstClasssTreeNode<V> extends AstPegBaseTreeNode<V> {
	public AstClasssTreeNode(String rule, String value) {
		super(rule, value);
	}

	@Override
	protected void interpretBeforeChilds(Long id) throws ReflectiveOperationException {
		super.interpretBeforeChilds(id);
		Stack<Object> openStack = this.openProcessStore.getTierStack();
		String peek = "";
		
		if (!openStack.isEmpty()) {
			peek = (String) openStack.peek();
		}
		this.processStore.tierOneUp(true);
		this.openProcessStore.tierOneUp(true);
		if ((peek.equals(ZERO_OR_MORE)) || (peek.equals(ONE_OR_MORE)) || (peek.equals(OPTIONAL))) {
			this.openProcessStore.getTierStack().push(openStack.pop());
		}
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
			rule = FIRST_OF + "(" + rule + ")";
		}
		rule = checkPostponedAction(rule);
		
		this.processStore.tierOneDown(true);
		this.openProcessStore.tierOneDown(true);
		if(!rule.isEmpty())
			this.processStore.getTierStack().push(rule);
	}
}
