/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.parser.peg;

/**
 * Command implementation for the <code>PegParser</code> rule: CLOSE.
 */
public class AstCLOSETreeNode<V> extends AstPegBaseTreeNode<V> {
	public static final String CLOSE = ")";

	public AstCLOSETreeNode(String rule, String value) {
		super(rule, value);
	}

	@Override
	protected void interpretAfterChilds(Long id) throws ReflectiveOperationException {
		super.interpretAfterChilds(id);

		this.processStore.tierOneUp(true);
		this.openProcessStore.tierOneUp(true);
	}
}