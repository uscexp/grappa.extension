/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.parser.peg;

import java.util.Stack;

/**
 * Command implementation for the <code>PegParser</code> rule: literal.
 */
public class AstLiteralTreeNode<V> extends AstPegBaseTreeNode<V> {
	public AstLiteralTreeNode(String rule, String value) {
		super(rule, value);
	}

	@Override
	protected void interpretAfterChilds(Long id) throws ReflectiveOperationException {
		super.interpretAfterChilds(id);
		Stack<Object> stack = this.processStore.getTierStack();
		String item = "";
		if ((this.value != null) && (!this.value.isEmpty())) {
			char quote = this.value.charAt(0);
			int idx = this.value.lastIndexOf(quote);
			String compareValue = this.value.substring(1, idx);
			if (!stack.isEmpty()) {
				String character = (String) stack.peek();
				character = character.substring(4, character.length() - 2);
				if (character.indexOf(compareValue) == -1) {
					String chars = (String) stack.pop();
					chars = chars.substring(4, chars.length() - 2);
					while (!stack.isEmpty()) {
						character = (String) stack.pop();
						character = character.substring(4, character.length() - 2);
						chars = chars + character;
						if (chars.indexOf(compareValue) != -1) {
							item = "string(\"" + compareValue + "\")";
							break;
						}
					}
				}
			}
		}
		if (!item.isEmpty()) {
			stack.push(item);
		}
	}
}