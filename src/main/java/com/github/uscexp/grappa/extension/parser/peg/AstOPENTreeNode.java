/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.parser.peg;

import org.parboiled.Node;

/**
 * Command implementation for the <code>PegParser</code> rule: OPEN.
 * 
 */
public class AstOPENTreeNode<V> extends AstPegBaseTreeNode<V> {

	public AstOPENTreeNode(Node<?> node, String value) {
		super(node, value);
	}

	@Override
	protected void interpret(Long id) throws ReflectiveOperationException {
		super.interpret(id);
		String openMethod = (String) openProcessStore.getStack().pop();
		if(openMethod.equals(")"))
			openMethod = (String) openProcessStore.getStack().pop();
		String bodyString = openMethod + "(";

		int size = closeProcessStore.getStack().size();
		for (int i = 0; i < size; i++) {
			String param = (String) closeProcessStore.getStack().pop();
			if(param.equals(")")) {
				break;
			}
			if (!bodyString.endsWith("(")) {
				bodyString += ", ";
			}
			bodyString += param;
		}
		bodyString += ")";
		// remove ) from open function stack
		if(!openProcessStore.getStack().isEmpty() && ((String)openProcessStore.getStack().peek()).equals(")"))
			openProcessStore.getStack().pop();

		bodyString = checkPostponedAction(bodyString);
		
		closeProcessStore.getStack().push(bodyString);
	}

}
