/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.parser;

import java.util.Stack;

import org.parboiled.Node;

import com.github.uscexp.grappa.extension.interpreter.ProcessStore;
import com.github.uscexp.grappa.extension.nodes.AstCommandTreeNode;

/**
 * @author haui
 *
 */
public class AstTermTreeNode extends AstCommandTreeNode<Double> {

	public AstTermTreeNode(Node<?> node, String value) {
		super(node, value);
	}

	@Override
	protected void interpret(Long id)
		throws ReflectiveOperationException {
		int multIdx = value.indexOf('*');
		int divIdx = value.indexOf('/');
		if ((multIdx > -1) || (divIdx > -1)) {
			Stack<Object> stack = ProcessStore.getInstance(id).getStack();
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
