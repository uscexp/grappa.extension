/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.nodes;

import com.github.uscexp.grappa.extension.annotations.AstCommand;

/**
 * base class for all {@link AstCommand} annotated parser rules.
 * the subclass has to contain the command logic.
 * 
 * @author haui
 *
 */
public abstract class AstCommandTreeNode<V> extends AstTreeNode<V> {

	public AstCommandTreeNode(String rule, String value) {
		super(rule, value);
	}
}
