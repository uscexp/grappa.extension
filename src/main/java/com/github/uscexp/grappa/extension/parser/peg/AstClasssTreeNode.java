/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.parser.peg;

import org.parboiled.Node;

/**
 * Command implementation for the <code>PegParser</code> rule: classs.
 */
public class AstClasssTreeNode<V> extends AstPegBaseTreeNode<V> {

	public AstClasssTreeNode(Node<?> node, String value) {
		super(node, value);
	}

	@Override
	protected void interpret(Long id)
		throws ReflectiveOperationException {
		super.interpret(id);

		String body = (String) closeProcessStore.getStack().pop();
		
		body = checkPostponedAction(body);

		closeProcessStore.getStack().push(body);
	}

}
