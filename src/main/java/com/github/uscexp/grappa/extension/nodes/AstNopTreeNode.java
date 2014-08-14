/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.nodes;

import org.parboiled.Node;

/**
 * {@link AstTreeNode} for parser rules without interpreter annotation.
 * does not do anything, is only needed for the tree structure.
 * 
 * @author haui
 *
 */
public class AstNopTreeNode<V> extends AstTreeNode<V> {

	public AstNopTreeNode(Node<?> node, String value) {
		super(node, value);
	}

	@Override
	protected void interpret(Long id) throws ReflectiveOperationException {
	}

}
