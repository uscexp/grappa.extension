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
	protected void interpret(Long id)
		throws ReflectiveOperationException {
		super.interpret(id);
		if ((value != null) & !value.isEmpty()) {
			closeProcessStore.getStack().push("ch('" + value + "')");
		}
	}

}
