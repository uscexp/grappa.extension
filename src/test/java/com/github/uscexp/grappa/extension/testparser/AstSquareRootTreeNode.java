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
public class AstSquareRootTreeNode extends AstCommandTreeNode<Double> {

	@Override
	protected void interpretBeforeChilds(Long id) throws Exception {
	}

	public AstSquareRootTreeNode(Node<?> node, String value) {
		super(node, value);
	}

	@Override
	protected void interpretAfterChilds(Long id) throws Exception {
		Stack<Object> stack = ProcessStore.getInstance(id).getStack();
		Double left = (Double) StackAccessUtil.pop(stack, Double.class);
		double result = 0;
		result = Math.sqrt(left);
		stack.push(result);
	}

}
