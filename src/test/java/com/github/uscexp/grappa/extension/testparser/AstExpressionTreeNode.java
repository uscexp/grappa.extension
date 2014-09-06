/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.testparser;

import java.util.Stack;

import org.parboiled.Node;

import com.github.uscexp.grappa.extension.interpreter.ProcessStore;
import com.github.uscexp.grappa.extension.nodes.AstCommandTreeNode;

/**
 * @author haui
 *
 */
public class AstExpressionTreeNode extends AstCommandTreeNode<Double> {

	public AstExpressionTreeNode(Node<?> node, String value) {
		super(node, value);
	}

	@Override
	protected void interpret(Long id)
		throws ReflectiveOperationException {
		int sumIdx = value.indexOf('+');
		int subIdx = value.indexOf('-');
		if ((sumIdx > -1) || (subIdx > -1)) {
			Stack<Object> stack = ProcessStore.getInstance(id).getStack();
			Double left = (Double) StackAccessUtil.pop(stack, Double.class);
			Double right = (Double) StackAccessUtil.pop(stack, Double.class);
			double result = 0;
			if (sumIdx > -1)
				result = left.doubleValue() + right.doubleValue();

			if (subIdx > -1)
				result = left.doubleValue() - right.doubleValue();
			stack.push(result);
		}
	}

}
