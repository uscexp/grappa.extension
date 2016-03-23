/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.parser.peg;

import org.parboiled.Node;

/**
 * Command implementation for the <code>PegParser</code> rule: ZEROORMORE.
 */
public class AstZEROORMORETreeNode<V> extends AstPegBaseTreeNode<V> {
	public static final String ZERO_OR_MORE = "zeroOrMore";

	public AstZEROORMORETreeNode(Node<?> node, String value) {
		super(node, value);
	}

	@Override
	protected void interpretAfterChilds(Long id) throws ReflectiveOperationException {
		super.interpretAfterChilds(id);
		this.openProcessStore.getTierStack().push("zeroOrMore");

		lastTreeNode = this;
	}
}