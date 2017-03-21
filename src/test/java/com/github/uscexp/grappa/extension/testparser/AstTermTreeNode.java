/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.testparser;

import com.github.uscexp.grappa.extension.interpreter.ProcessStore;
import com.github.uscexp.grappa.extension.nodes.AstCommandTreeNode;
import com.github.uscexp.grappa.extension.util.IStack;

/**
 * @author haui
 *
 */
public class AstTermTreeNode extends AstCommandTreeNode<Double> {

	public AstTermTreeNode(String rule, String value) {
		super(rule, value);
	}

	@Override
	protected void interpretBeforeChilds(Long id) throws Exception {
	}

	@Override
	protected void interpretAfterChilds(Long id)
		throws Exception {
		int multIdx = value.indexOf('*');
		int divIdx = value.indexOf('/');
		if ((multIdx > -1) || (divIdx > -1)) {
			IStack<Object> stack = ProcessStore.getInstance(id).getStack();
			Double right = (Double) StackAccessUtil.pop(stack, Double.class);
			Double left = (Double) StackAccessUtil.pop(stack, Double.class);
			double result = 0;
			if (multIdx > -1)
				result = left.doubleValue() * right.doubleValue();

			if (divIdx > -1)
				result = left.doubleValue() / right.doubleValue();
			stack.push(result);
		}
	}

}
