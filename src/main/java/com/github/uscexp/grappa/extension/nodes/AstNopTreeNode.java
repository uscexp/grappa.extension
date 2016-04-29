/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.nodes;

/**
 * {@link AstTreeNode} for parser rules without interpreter annotation.
 * does not do anything, is only needed for the tree structure.
 * 
 * @author haui
 *
 */
public class AstNopTreeNode<V> extends AstTreeNode<V> {

	public AstNopTreeNode(String rule, String value) {
		super(rule, value);
	}

	@Override
	protected void interpretBeforeChilds(Long id) throws Exception {
	}

	@Override
	protected void interpretAfterChilds(Long id) throws Exception {
	}

}
