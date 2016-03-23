/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.parser.peg;

import java.util.Stack;

import org.parboiled.Node;

/**
 * Command implementation for the <code>PegParser</code> rule: charRange.
 */
public class AstCharRangeTreeNode<V> extends AstPegBaseTreeNode<V> {
	public AstCharRangeTreeNode(Node<?> node, String value) {
		super(node, value);
	}

	@Override
	protected void interpretAfterChilds(Long id) throws ReflectiveOperationException {
		super.interpretAfterChilds(id);
		Stack<Object> stack = this.processStore.getTierStack();
		String rangeStart = (String) stack.pop();
		String rangeEnd = "";
		if (!stack.isEmpty()) {
			rangeEnd = (String) stack.peek();
		}
		if ((rangeEnd.startsWith("ch('")) && (this.value.length() > 1)) {
			rangeStart = rangeStart.substring(3, rangeStart.length() - 1);
			rangeEnd = rangeEnd.substring(3, rangeEnd.length() - 1);
			stack.pop();
			stack.push("charRange(" + rangeStart + ", " + rangeEnd + ")");
		} else {
			stack.push(rangeStart);
		}
		lastTreeNode = this;
	}
}
