/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.nodes;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.github.fge.grappa.run.ParsingResult;
import com.github.uscexp.grappa.extension.interpreter.AstInterpreter;
import com.github.uscexp.grappa.extension.interpreter.ProcessStore;

/**
 * base class of all {@link AstInterpreter} tree nodes. contains the
 * {@link ParsingResult} node and the value of the parse node (from input).
 * 
 * @author haui
 *
 */
public abstract class AstTreeNode<V> {

	protected boolean skip = false;
	protected String rule;
	protected String value;
	protected AstTreeNode<V> parent;
	protected List<AstTreeNode<V>> children = new ArrayList<>();

	public AstTreeNode(String rule, String value) {
		super();
		this.rule = rule;
		this.value = value;
	}

	public void interpretIt(Long id, boolean forewardOrder) throws Exception {
		if (!ProcessStore.getInstance(id).checkPrecondition() || skip)
			return;
		interpretBeforeChilds(id);
		if(skip)
			return;
		if (forewardOrder) {
			for (AstTreeNode<V> astTreeNode : getChildren()) {
				astTreeNode.interpretIt(id, forewardOrder);
			}
		} else {
			for (int i = children.size() - 1; i >= 0; i--) {
				AstTreeNode<V> childNode = children.get(i);
				childNode.interpretIt(id, forewardOrder);
			}
		}
		if(skip)
			return;
		interpretAfterChilds(id);
	}

	/**
	 * Interpret before children method.
	 *
	 * @param id
	 *            instance id
	 */
	protected abstract void interpretBeforeChilds(Long id) throws Exception;

	/**
	 * Interpret after children method.
	 *
	 * @param id
	 *            instance id
	 */
	protected abstract void interpretAfterChilds(Long id) throws Exception;

	@SuppressWarnings("unchecked")
	protected <F> F convert(String value, Class<F> valueType, Class<?> factoryClass, String factoryMethod)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
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

	@Override
	public String toString() {
		String text = "[" + getClass().getSimpleName() + "].[" + rule + "] '" + value + "'";
		text = text.replaceAll("(\\r|\\n|\\r\\n)+", "\\\\n");
		return text;
	}

	public AstTreeNode<V> getParent() {
		return parent;
	}

	public void setParent(AstTreeNode<V> parent) {
		this.parent = parent;
	}

	public List<AstTreeNode<V>> getChildren() {
		return children;
	}

	public void setChildren(List<AstTreeNode<V>> children) {
		this.children = children;
	}

	public String getRule() {
		return rule;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
