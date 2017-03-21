package com.github.uscexp.grappa.extension.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * @author haui
 *
 * @param <E>
 */
public abstract class AbstractDecoratorStack<E> implements IStack<E> {

	protected IStack<E> stack;

	public AbstractDecoratorStack() {
		super();
	}

	@Override
	public boolean isEmpty() {
		return stack.isEmpty();
	}

	@Override
	public void clear() {
		stack.clear();
	}

	@Override
	public Iterator<E> iterator() {
		return stack.iterator();
	}

	@Override
	public int size() {
		return stack.size();
	}

	@Override
	public boolean contains(Object o) {
		return stack.contains(o);
	}

	@Override
	public Object[] toArray() {
		return stack.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return stack.toArray(a);
	}

	@Override
	public boolean add(E e) {
		return stack.add(e);
	}

	@Override
	public boolean remove(Object o) {
		return stack.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return stack.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		return stack.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		return stack.addAll(index, c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return stack.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return stack.retainAll(c);
	}

	@Override
	public E get(int index) {
		return stack.get(index);
	}

	@Override
	public E set(int index, E element) {
		return stack.set(index, element);
	}

	@Override
	public void add(int index, E element) {
		stack.add(index, element);
	}

	@Override
	public E remove(int index) {
		return stack.remove(index);
	}

	@Override
	public int indexOf(Object o) {
		return stack.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return stack.lastIndexOf(o);
	}

	@Override
	public ListIterator<E> listIterator() {
		return stack.listIterator();
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		return stack.listIterator(index);
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		return stack.subList(fromIndex, toIndex);
	}

}