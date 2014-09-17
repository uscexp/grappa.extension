/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.parser.peg;

import org.parboiled.Node;

/**
 * Command implementation for the <code>PegParser</code> rule: CLOSE.
 */
public class AstCLOSETreeNode<V> extends AstPegBaseTreeNode<V> {

	public static final String CLOSE = ")";

	public AstCLOSETreeNode(Node<?> node, String value) {
		super(node, value);
	}

	@Override
	protected void interpret(Long id)
		throws ReflectiveOperationException {
		super.interpret(id);
		closeProcessStore.getStack().push(CLOSE);
		openProcessStore.getStack().push(CLOSE);
	}

}
