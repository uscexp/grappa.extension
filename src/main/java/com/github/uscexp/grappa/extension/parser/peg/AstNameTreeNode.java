/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.parser.peg;

import org.parboiled.Node;

/**
 * Command implementation for the <code>PegParser</code> rule: name.
 */
public class AstNameTreeNode<V> extends AstPegBaseTreeNode<V> {

	public AstNameTreeNode(Node<?> node, String value) {
		super(node, value);
	}

	@Override
	protected void interpret(Long id)
		throws ReflectiveOperationException {
		super.interpret(id);
		if ((value != null) & !value.isEmpty()) {
			int idx = value.indexOf('#');
			String realValue = value;
			if(idx != -1) {
				realValue = value.substring(0, idx);
			}
			realValue = getMethodName(realValue.trim());
			if(!checkExistence(realValue)) {
				realValue += "()";
			}
			
			realValue = checkPostponedAction(realValue);
			
			closeProcessStore.getStack().push(realValue);
		}
	}

}
