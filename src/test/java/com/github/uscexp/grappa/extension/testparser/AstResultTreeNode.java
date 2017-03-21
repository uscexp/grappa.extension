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
public class AstResultTreeNode extends AstCommandTreeNode<Double> {

	public AstResultTreeNode(String rule, String value) {
		super(rule, value);
	}

	@Override
	protected void interpretBeforeChilds(Long id) throws Exception {
	}

	@Override
	protected void interpretAfterChilds(Long id) throws Exception {
		IStack<Object> stack = ProcessStore.getInstance(id).getStack();
		Double result = (Double) StackAccessUtil.peek(stack, Double.class);
		System.out.println(result);
	}

}
