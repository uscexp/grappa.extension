package com.github.uscexp.grappa.extension.testparser;

import com.github.uscexp.grappa.extension.util.IStack;

public final class StackAccessUtil {

	private StackAccessUtil() {
	}
	
	public static <V> Object pop(IStack<Object> stack, Class<?> type) {
		Object result = null;
		
		Object value = null;
		while (!((value = stack.pop()).getClass().isAssignableFrom(type))) {
			;
		}
		if(value != null && value.getClass().isAssignableFrom(type))
			result = value;
		return result;
	}

	
	public static <V> Object peek(IStack<Object> stack, Class<?> type) {
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
