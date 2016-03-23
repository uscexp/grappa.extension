/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.parser.peg;

import org.parboiled.Node;

/**
 * Command implementation for the <code>PegParser</code> rule: OPTION.
 */
public class AstOPTIONTreeNode<V> extends AstPegBaseTreeNode<V> {
	public static final String OPTION = "optional";

	public AstOPTIONTreeNode(Node<?> node, String value) {
		super(node, value);
	}

	@Override
	protected void interpretAfterChilds(Long id) throws ReflectiveOperationException {
		super.interpretAfterChilds(id);
		this.openProcessStore.getTierStack().push("optional");

		lastTreeNode = this;
	}
}
