/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.parser.peg;

import org.parboiled.Node;

/**
 * Command implementation for the <code>PegParser</code> rule: OR.
 */
public class AstORTreeNode<V> extends AstPegBaseTreeNode<V> {

	public static final String FIRST_OF = "firstOf";

	public AstORTreeNode(Node<?> node, String value) {
		super(node, value);
	}

	@Override
	protected void interpret(Long id)
		throws ReflectiveOperationException {
		super.interpret(id);
		
		if(openProcessStore.getStack().isEmpty() || !FIRST_OF.equals(openProcessStore.getStack().peek())) {
			openProcessStore.getStack().push(FIRST_OF);
		}
		closeProcessStore.getStack().push(AstSequenceTreeNode.START_SEQUENCE);
	}

}
