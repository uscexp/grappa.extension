/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.parser.peg;

import com.github.uscexp.grappa.extension.util.IStack;

/**
 * Command implementation for the <code>PegParser</code> rule: sequence.
 */
public class AstSequenceTreeNode<V> extends AstPegBaseTreeNode<V> {
	public AstSequenceTreeNode(String rule, String value) {
		super(rule, value);
	}

	@Override
	protected void interpretBeforeChilds(Long id) throws ReflectiveOperationException {
		super.interpretBeforeChilds(id);
		IStack<Object> openStack = this.openProcessStore.getTierStack();
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
		String methodBody = "";
		IStack<Object> stack = this.processStore.getTierStack();
		IStack<Object> openStack = this.openProcessStore.getTierStack();

		if(!AstLiteralTreeNode.class.isAssignableFrom(getParent().getClass())
				&& !AstCharRangeTreeNode.class.isAssignableFrom(getParent().getClass())) {
			if(getChildren().size() > 1 || !stack.isEmpty()) {
		
				String peek = "";
				if (!openStack.isEmpty()) {
					peek = (String) openStack.peek();
				}
				if (stack.size() == 1) {
					if ((peek.equals(ZERO_OR_MORE)) || (peek.equals(ONE_OR_MORE)) || (peek.equals(OPTIONAL))) {
						methodBody = (String) openStack.pop() + "(";
						methodBody = methodBody + stack.pop() + ")";
					} else {
						methodBody = (String) stack.pop();
					}
				} else if (stack.size() > 1) {
					if(!peek.isEmpty()) 
						methodBody = openStack.pop() + "(";
					else
						methodBody = "sequence(";
					while (!stack.isEmpty()) {
						if (!methodBody.endsWith("(")) {
							methodBody = methodBody + ", ";
						}
						methodBody = methodBody + stack.pop();
					}
					methodBody = methodBody + ")";
				}
				
			}
		}
		this.processStore.tierOneDown(true);
		this.openProcessStore.tierOneDown(true);
		String peek = null;
		if(!this.openProcessStore.getTierStack().isEmpty()) {
			peek = (String) this.openProcessStore.getTierStack().peek();
		}
		for (Object object : openStack) {
			String val = (String)object;
			if(peek == null || !FIRST_OF.equals(peek))
				this.openProcessStore.getTierStack().push(val);
		}
		
		if(!AstLiteralTreeNode.class.isAssignableFrom(getParent().getClass())
				&& !AstCharRangeTreeNode.class.isAssignableFrom(getParent().getClass())) {
			if(methodBody != null && !methodBody.isEmpty())
				this.processStore.getTierStack().push(methodBody);
		} else {
			for (Object object : stack) {
				this.processStore.getTierStack().push((String)object);
			}
		}
	}
}
