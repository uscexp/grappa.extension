/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.parser.peg;

/**
 * Command implementation for the <code>PegParser</code> rule: OR.
 */
public class AstORTreeNode<V> extends AstPegBaseTreeNode<V> {
	public AstORTreeNode(String rule, String value) {
		super(rule, value);
	}

	@Override
	protected void interpretAfterChilds(Long id) throws ReflectiveOperationException {
		super.interpretAfterChilds(id);
		if ((this.openProcessStore.getTierStack().isEmpty()) || (!FIRST_OF.equals(this.openProcessStore.getTierStack().peek()))) {
			this.openProcessStore.getTierStack().push(FIRST_OF);
		}
	}
}
