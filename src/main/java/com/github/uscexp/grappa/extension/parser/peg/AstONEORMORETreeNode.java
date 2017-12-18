/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.parser.peg;

/**
 * Command implementation for the <code>PegParser</code> rule: ONEORMORE.
 */
public class AstONEORMORETreeNode<V> extends AstPegBaseTreeNode<V> {
	public AstONEORMORETreeNode(String rule, String value) {
		super(rule, value);
	}

	@Override
	protected void interpretAfterChilds(Long id) throws ReflectiveOperationException {
		super.interpretAfterChilds(id);
		this.openProcessStore.getTierStack().push(ONE_OR_MORE);
	}
}