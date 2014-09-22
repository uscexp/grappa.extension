/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.parser.peg;

import org.parboiled.Node;

/**
 * Command implementation for the <code>PegParser</code> rule: zerooOrMore.
 * 
 */
public class AstZerooOrMoreTreeNode<V> extends AstPegBaseTreeNode<V> {

	public AstZerooOrMoreTreeNode(Node<?> node, String value) {
		super(node, value);
	}

	@Override
	protected void interpret(Long id) throws ReflectiveOperationException {
		super.interpret(id);
		if(value == null || value.isEmpty())
			if(closeProcessStore.getStack().isEmpty() || !AstSequenceTreeNode.isStartSequence(((String)closeProcessStore.getStack().peek())))
				closeProcessStore.getStack().push(AstSequenceTreeNode.START_SEQUENCE);
	}

}
