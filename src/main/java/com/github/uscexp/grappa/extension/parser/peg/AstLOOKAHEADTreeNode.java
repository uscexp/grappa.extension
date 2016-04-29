/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.parser.peg;

import java.util.Stack;

/**
 * Command implementation for the <code>PegParser</code> rule: LOOKAHEAD.
 */
public class AstLOOKAHEADTreeNode<V> extends AstPegBaseTreeNode<V> {
	public AstLOOKAHEADTreeNode(String rule, String value) {
		super(rule, value);
	}

	@Override
	protected void interpretAfterChilds(Long id) throws ReflectiveOperationException {
		super.interpretAfterChilds(id);
		Stack<Object> stack = this.processStore.getTierStack();
		String param = (String) stack.pop();
		stack.push("test(" + param + ")");
	}
}
