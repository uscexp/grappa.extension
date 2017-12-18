package com.github.uscexp.grappa.extension.util;

import java.util.List;

/**
 * @author haui
 *
 * @param <E>
 */
public interface IStack<E> extends Iterable<E>, List<E> {

	E push(E item);

	E pop();

	E peek();

	boolean empty();

	int search(Object o);

	boolean isEmpty();
	
	void clear();
}