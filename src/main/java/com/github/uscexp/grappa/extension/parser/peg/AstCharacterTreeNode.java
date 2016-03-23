/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.parser.peg;

import org.parboiled.Node;

/**
 * Command implementation for the <code>PegParser</code> rule: character.
 */
public class AstCharacterTreeNode<V> extends AstPegBaseTreeNode<V> {
	public AstCharacterTreeNode(Node<?> node, String value) {
		super(node, value);
	}

	@Override
	protected void interpretAfterChilds(Long id) throws ReflectiveOperationException {
		super.interpretAfterChilds(id);
		if ((this.value != null) && (!this.value.isEmpty())) {
			this.processStore.getTierStack().push("ch('" + this.value + "')");
		}
		lastTreeNode = this;
	}
}
