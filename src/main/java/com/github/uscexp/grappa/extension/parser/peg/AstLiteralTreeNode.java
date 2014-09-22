/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.parser.peg;

import org.parboiled.Node;

/**
 * Command implementation for the <code>PegParser</code> rule: literal.
 */
public class AstLiteralTreeNode<V> extends AstPegBaseTreeNode<V> {

	public AstLiteralTreeNode(Node<?> node, String value) {
		super(node, value);
	}

	@Override
	protected void interpret(Long id)
		throws ReflectiveOperationException {
		super.interpret(id);
		if ((value != null) & !value.isEmpty()) {
			char quote = value.charAt(0);
			int idx = value.lastIndexOf(quote);
			String compareValue = value.substring(1, idx);
			if(!closeProcessStore.getStack().isEmpty()) {
				String character = (String) closeProcessStore.getStack().peek();
				character = character.substring(4, character.length() - 2);
				
				if(character.indexOf(compareValue) == -1) {
					String chars = (String) closeProcessStore.getStack().pop();
					chars = chars.substring(4, chars.length() - 2);
					
					while (!closeProcessStore.getStack().isEmpty()) {
						character = (String) closeProcessStore.getStack().pop();
						character = character.substring(4, character.length() - 2);
						chars += character;
						if(chars.indexOf(compareValue) != -1) {
							closeProcessStore.getStack().push("string(\"" + compareValue + "\")");
							break;
						}
					}
				}
			}
		}
	}

}
