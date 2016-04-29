/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.testparser;

import java.util.Stack;

import com.github.uscexp.grappa.extension.interpreter.ProcessStore;
import com.github.uscexp.grappa.extension.nodes.AstCommandTreeNode;

/**
 * @author haui
 *
 */
public class AstFactorTreeNode extends AstCommandTreeNode<Double> {

	public AstFactorTreeNode(String rule, String value) {
		super(rule, value);
	}

	@Override
	protected void interpretBeforeChilds(Long id) throws Exception {
	}

	@Override
	protected void interpretAfterChilds(Long id) throws Exception {
		if(value.indexOf('^') > -1) {
			Stack<Object> stack = ProcessStore.getInstance(id).getStack();
			Double right = (Double) StackAccessUtil.pop(stack, Double.class);
			if(!(stack.peek() instanceof Double))
				stack.pop();
			Double left = (Double) StackAccessUtil.pop(stack, Double.class);
			double result = 0;
			result = Math.pow(left, right);
			stack.push(result);
		}
	}

}
