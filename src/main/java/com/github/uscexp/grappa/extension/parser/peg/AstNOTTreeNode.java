/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.parser.peg;

import com.github.uscexp.grappa.extension.util.IStack;

/**
 * Command implementation for the <code>PegParser</code> rule: NOT.
 */
public class AstNOTTreeNode<V> extends AstPegBaseTreeNode<V> {
	public AstNOTTreeNode(String rule, String value) {
		super(rule, value);
	}

	@Override
	protected void interpretAfterChilds(Long id) throws ReflectiveOperationException {
		super.interpretAfterChilds(id);
		IStack<Object> stack = this.processStore.getTierStack();
		String param = (String) stack.pop();
		stack.push("testNot(" + param + ")");
	}
}
