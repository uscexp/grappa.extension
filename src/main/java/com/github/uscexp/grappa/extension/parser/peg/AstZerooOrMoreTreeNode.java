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
	protected void interpretAfterChilds(Long id) throws ReflectiveOperationException {
		super.interpretAfterChilds(id);
		if ((!this.tearedUp) && (!AstCLOSETreeNode.class.isAssignableFrom(lastTreeNode.getClass())) && (!AstORTreeNode.class.isAssignableFrom(lastTreeNode.getClass()))) {
			this.processStore.tierOneUp(true);
			this.openProcessStore.tierOneUp(true);
		}
		lastTreeNode = this;
	}
}