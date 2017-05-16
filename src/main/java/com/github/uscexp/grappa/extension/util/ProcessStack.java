package com.github.uscexp.grappa.extension.util;

import java.util.Stack;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author haui
 *
 * @param <E>
 */
public class ProcessStack<E> extends Stack<E> implements IStack<E> {

	private static final long serialVersionUID = 5942488043278324226L;

	@Override
	public synchronized String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}