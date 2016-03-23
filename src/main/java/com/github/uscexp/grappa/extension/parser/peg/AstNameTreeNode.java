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
	protected void interpretAfterChilds(Long id) throws ReflectiveOperationException {
		super.interpretAfterChilds(id);
		if ((this.value != null) && (!this.value.isEmpty()) && (!((Node<?>) this.parseNode.getParent()).getLabel().equals("definition"))) {
			int idx = this.value.indexOf('#');
			String realValue = this.value;
			if (idx != -1) {
				realValue = this.value.substring(0, idx);
			}
			realValue = getMethodName(realValue.trim());
			if (!checkExistence(realValue)) {
				realValue = realValue + "()";
			}
			realValue = checkPostponedAction(realValue);

			this.processStore.getTierStack().push(realValue);
		}
		lastTreeNode = this;
	}
}