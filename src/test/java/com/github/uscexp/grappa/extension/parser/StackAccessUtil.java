package com.github.uscexp.grappa.extension.parser;

import java.util.Stack;

public final class StackAccessUtil {

	private StackAccessUtil() {
	}
	
	public static <V> Object pop(Stack<Object> stack, Class<?> type) {
		Object result = null;
		
		Object value = null;
		while (!((value = stack.pop()).getClass().isAssignableFrom(type))) {
			;
		}
		if(value != null && value.getClass().isAssignableFrom(type))
			result = value;
		return result;
	}

	
	public static <V> Object peek(Stack<Object> stack, Class<?> type) {
		Object result = null;
		
		Object value = null;
		while (!((value = stack.peek()).getClass().isAssignableFrom(type))) {
			stack.pop();
		}
		if(value != null && value.getClass().isAssignableFrom(type))
			result = value;
		return result;
	}
}
