/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.parser.peg;

import org.parboiled.Node;

/**
 * Command implementation for the <code>PegParser</code> rule: ONEORMORE.
 */
public class AstONEORMORETreeNode<V> extends AstPegBaseTreeNode<V> {
	public static final String ONE_OR_MORE = "oneOrMore";

	public AstONEORMORETreeNode(Node<?> node, String value) {
		super(node, value);
	}

	@Override
	protected void interpretAfterChilds(Long id) throws ReflectiveOperationException {
		super.interpretAfterChilds(id);
		this.openProcessStore.getTierStack().push("oneOrMore");

		lastTreeNode = this;
	}
}