/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.parser.peg;

import org.parboiled.Node;

/**
 * Command implementation for the <code>PegParser</code> rule: SQUARECLOSE.
 */
public class AstSQUARECLOSETreeNode<V> extends AstPegBaseTreeNode<V> {

	public static final String CLOSE = "]";

	public AstSQUARECLOSETreeNode(Node<?> node, String value) {
		super(node, value);
	}

	@Override
	protected void interpret(Long id)
		throws ReflectiveOperationException {
		super.interpret(id);
		closeProcessStore.getStack().push(CLOSE);
	}

}
