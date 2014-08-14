/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.nodes;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.parboiled.Node;
import org.parboiled.support.ParsingResult;
import org.parboiled.trees.MutableTreeNodeImpl;

import com.github.uscexp.grappa.extension.interpreter.AstInterpreter;
import com.github.uscexp.grappa.extension.interpreter.ProcessStore;

/**
 * base class of all {@link AstInterpreter} tree nodes. contains the {@link ParsingResult}
 * node and the value of the parse node (from input).
 * 
 * @author haui
 *
 */
public abstract class AstTreeNode<V> extends MutableTreeNodeImpl<AstTreeNode<V>> {

	protected Node<?> parseNode;
	protected String value;

	public AstTreeNode(Node<?> node, String value) {
		super();
		this.parseNode = node;
		this.value = value;
	}
	
	public void interpretIt(Long id) throws ReflectiveOperationException {
	    if( !ProcessStore.getInstance(id).checkPrecondition())
	        return;
		for (AstTreeNode<V> astTreeNode : getChildren()) {
			astTreeNode.interpretIt(id);
		}
		interpret(id);
	}

	/**
	 * Interpret method
	 *
	 * @param id instance id
	 */
	protected abstract void interpret(Long id) throws ReflectiveOperationException;

	@SuppressWarnings("unchecked")
	protected <F> F convert(String value, Class<F> valueType, Class<?> factoryClass, String factoryMethod)
		throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		F result = null;
		if (!valueType.equals(Object.class)) {
			if (!factoryClass.equals(Object.class) && !factoryMethod.equals("")) {
				Method method = factoryClass.getDeclaredMethod(factoryMethod, String.class);
				result = (F) method.invoke(null, value);
			} else {
				Constructor<F> constructor = valueType.getDeclaredConstructor(String.class);
				result = constructor.newInstance(value);
			}
		} else {
			result = (F) value;
		}
		return result;
	}
}
