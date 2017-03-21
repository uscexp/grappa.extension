package com.github.uscexp.grappa.extension.util;

import java.io.PrintStream;
import java.io.Serializable;

/**
 * @author haui
 *
 * @param <E>
 */
public class LogStack<E> extends AbstractDecoratorStack<E> implements IStack<E>, Serializable {

	private static final long serialVersionUID = 641782022622557318L;
	
	private PrintStream out;
	
	public LogStack(IStack<E> stack, PrintStream out) {
		this.stack = stack;
		this.out = out;
	}

	@Override
	public E push(E item) {
		E result = null;
		result = stack.push(item);
		try {
			out.println(String.format("push: %s", String.valueOf(item)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public synchronized E pop() {
		E result = null;
		result = stack.pop();
		try {
			out.println(String.format("pop: %s", String.valueOf(result)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public synchronized E peek() {
		E result = null;
		result = stack.peek();
		try {
			out.println(String.format("peek: %s", String.valueOf(result)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean empty() {
		boolean result = false;
		result = stack.empty();
		try {
			out.println(String.format("empty: %s", String.valueOf(result)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public synchronized int search(Object o) {
		int result = -1;
		result = stack.search(o);
		try {
			out.println(String.format("search: %s, fount: %d", String.valueOf(o), result));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
