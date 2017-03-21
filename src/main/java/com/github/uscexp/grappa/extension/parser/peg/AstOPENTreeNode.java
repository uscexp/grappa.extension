/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.parser.peg;

import com.github.uscexp.grappa.extension.util.IStack;

/**
 * Command implementation for the <code>PegParser</code> rule: OPEN.
 * 
 */
public class AstOPENTreeNode<V> extends AstPegBaseTreeNode<V> {
	public AstOPENTreeNode(String rule, String value) {
		super(rule, value);
	}

	@Override
	protected void interpretAfterChilds(Long id) throws ReflectiveOperationException {
		super.interpretAfterChilds(id);
		IStack<Object> stack = this.processStore.getTierStack();
		String bodyString = "";

		boolean init = false;
		while (!stack.isEmpty()) {
			if (init) {
				bodyString = bodyString + ", ";
			} else {
				init = true;
			}
			bodyString = bodyString + stack.pop();
		}
		bodyString = checkPostponedAction(bodyString);
		this.processStore.tierOneDown(true);
		this.openProcessStore.tierOneDown(true);
		if (!bodyString.isEmpty()) {
			bodyString = checkPostponedAction(bodyString);
			this.processStore.getTierStack().push(bodyString);
		}
	}
}
