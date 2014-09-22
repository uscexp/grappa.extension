/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.parser.peg;

import org.parboiled.Node;

/**
 * Command implementation for the <code>PegParser</code> rule: ANY.
 */
public class AstANYYTreeNode<V> extends AstPegBaseTreeNode<V> {

	public AstANYYTreeNode(Node<?> node, String value) {
		super(node, value);
	}

	@Override
	protected void interpret(Long id)
		throws ReflectiveOperationException {
		super.interpret(id);
		closeProcessStore.getStack().push("ANY");
	}

}
