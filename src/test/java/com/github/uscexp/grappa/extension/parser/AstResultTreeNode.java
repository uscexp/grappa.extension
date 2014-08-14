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
public class AstResultTreeNode extends AstCommandTreeNode<Double> {

	public AstResultTreeNode(Node<?> node, String value) {
		super(node, value);
	}

	@Override
	protected void interpret(Long id) throws ReflectiveOperationException {
		Stack<Object> stack = ProcessStore.getInstance(id).getStack();
		Double result = (Double) StackAccessUtil.peek(stack, Double.class);
		System.out.println(result);
	}

}
