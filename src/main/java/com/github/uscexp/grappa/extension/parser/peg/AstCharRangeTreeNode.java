/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.parser.peg;

import org.parboiled.Node;

/**
 * Command implementation for the <code>PegParser</code> rule: charRange.
 */
public class AstCharRangeTreeNode<V> extends AstPegBaseTreeNode<V> {

	public AstCharRangeTreeNode(Node<?> node, String value) {
		super(node, value);
	}

	@Override
	protected void interpret(Long id)
		throws ReflectiveOperationException {
		super.interpret(id);
		String rangeStart = (String) closeProcessStore.getStack().pop();
		String rangeEnd = (String) closeProcessStore.getStack().peek();
		if(rangeEnd.startsWith("ch('") && value.length() > 1) {
			closeProcessStore.getStack().pop();
			closeProcessStore.getStack().push("charRange(" + rangeStart + ", " + rangeEnd + ")");
		} else {
			closeProcessStore.getStack().push(rangeStart);
		}
	}

}
