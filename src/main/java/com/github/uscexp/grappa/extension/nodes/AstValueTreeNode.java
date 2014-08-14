/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.nodes;

import org.parboiled.Node;

import com.github.uscexp.grappa.extension.annotations.AstValue;
import com.github.uscexp.grappa.extension.interpreter.AstInterpreter;
import com.github.uscexp.grappa.extension.interpreter.ProcessStore;


/**
 * {@link AstTreeNode} for {@link AstValue} annotated parser rules.
 * will be instanciated automatically from the {@link AstInterpreter}.
 * 
 * @author haui
 *
 */
public class AstValueTreeNode<V> extends AstTreeNode<V> {

	private Class<?> valueType;
	private Class<?> factoryClass;
	private String factoryMethod;
	
	public AstValueTreeNode(Node<?> node, String value, Class<?> valueType, Class<?> factoryClass, String factoryMethod) {
		super(node, value);
		this.valueType = valueType;
		this.factoryClass = factoryClass;
		this.factoryMethod = factoryMethod;
	}

	@Override
	protected void interpret(Long id) throws ReflectiveOperationException {
		ProcessStore.getInstance(id).getStack().push(convert(value, valueType, factoryClass, factoryMethod));
	}
}
