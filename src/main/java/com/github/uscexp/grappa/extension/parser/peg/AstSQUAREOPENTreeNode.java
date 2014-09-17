/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.parser.peg;

import org.parboiled.Node;

/**
 * Command implementation for the <code>PegParser</code> rule: SQUAREOPEN.
 */
public class AstSQUAREOPENTreeNode<V> extends AstPegBaseTreeNode<V> {

	public AstSQUAREOPENTreeNode(Node<?> node, String value) {
		super(node, value);
	}

	@Override
	protected void interpret(Long id)
		throws ReflectiveOperationException {
		super.interpret(id);
		if (!closeProcessStore.getStack().isEmpty()) {
			String rule = "";
			String stackValue = (String) closeProcessStore.getStack().pop();
			
			int i = 0;
			while (!closeProcessStore.getStack().isEmpty() && !stackValue.equals(AstSQUARECLOSETreeNode.CLOSE)) {
				if(i > 0) {
					rule += ", ";
				}
				rule += stackValue;
				++i;
				stackValue = (String) closeProcessStore.getStack().pop();
			}
			if(i > 1) {
				rule = "firstOf(" + rule + ")";
			}
			closeProcessStore.getStack().push(rule);
		}
	}

}
