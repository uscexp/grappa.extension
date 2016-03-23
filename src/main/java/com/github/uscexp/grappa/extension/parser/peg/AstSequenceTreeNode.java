/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.parser.peg;

import java.util.Stack;

import org.parboiled.Node;

/**
 * Command implementation for the <code>PegParser</code> rule: sequence.
 */
public class AstSequenceTreeNode<V> extends AstPegBaseTreeNode<V> {
	public AstSequenceTreeNode(Node<?> node, String value) {
		super(node, value);
	}

	@Override
	protected void interpretAfterChilds(Long id) throws ReflectiveOperationException {
		super.interpretAfterChilds(id);
		String methodBody = "";
		Stack<Object> stack = this.processStore.getTierStack();
		Stack<Object> openStack = this.openProcessStore.getTierStack();

		String peek = "";
		if (!openStack.isEmpty()) {
			peek = (String) openStack.peek();
		}
		if (stack.size() == 1) {
			if ((peek.equals("zeroOrMore")) || (peek.equals("oneOrMore")) || (peek.equals("optional"))) {
				methodBody = (String) openStack.pop() + "(";
				methodBody = methodBody + stack.pop() + ")";
			} else {
				methodBody = (String) stack.pop();
			}
		} else if (stack.size() > 1) {
			methodBody = "sequence(";
			while (!stack.isEmpty()) {
				if (!methodBody.endsWith("(")) {
					methodBody = methodBody + ", ";
				}
				methodBody = methodBody + stack.pop();
			}
			methodBody = methodBody + ")";
		}
		this.processStore.tierOneDown(true);
		this.openProcessStore.tierOneDown(true);
		this.processStore.getTierStack().push(methodBody);

		lastTreeNode = this;
	}
}
