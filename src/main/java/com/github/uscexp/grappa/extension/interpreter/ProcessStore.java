/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.interpreter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.uscexp.grappa.extension.interpreter.type.MethodDeclaration;
import com.github.uscexp.grappa.extension.interpreter.type.MethodSignature;
import com.github.uscexp.grappa.extension.interpreter.type.Primitive;
import com.github.uscexp.grappa.extension.util.IStack;
import com.github.uscexp.grappa.extension.util.LogStack;
import com.github.uscexp.grappa.extension.util.ProcessStack;

/**
 * VariableStore for a process.
 *
 * @author haui
 */
public final class ProcessStore<V> {

	private static Map<Long, ProcessStore<?>> instances = new HashMap<>();

	public static short OK_STATE = 0;
	public static short BREAK_STATE = 1;
	public static short CONT_STATE = 2;

	private short execState = OK_STATE;

	private int tier = -1;

	/** globale variables. */
	private Map<Object, Object> global = new HashMap<>();

	/** store for method nodes. */
	private Map<MethodSignature, MethodDeclaration> methods = new HashMap<>();

	/** Stack for calculations. */
	private IStack<Object> stack = new ProcessStack<>();

	/** Stack for several tiers. */
	private List<IStack<Object>> tierStack = new ArrayList<>();

	/** Block variable hierarchy: stores variable Maps. */
	private List<Map<Object, Object>> working = new ArrayList<>();

	/**
	 * stores old block variable hierarchies for later retreval e.g. returning
	 * from a method call<br>
	 * stores Lists of 'working' variable Maps
	 */
	private List<List<Map<Object, Object>>> oldBlockHierarchy = new ArrayList<>();

	private String[] args;

	private boolean testing;
	private boolean logging;

	private ProcessStore() {
		this(false, false);
	}

	private ProcessStore(boolean testing, boolean logging) {
		this.testing = testing;
		this.logging = logging;
	}

	public boolean isTesting() {
		return testing;
	}

	public boolean isLogging() {
		return logging;
	}

	public static <V> ProcessStore<V> getInstance(Long id) {
		return getInstance(id, false);
	}

	public static <V> ProcessStore<V> getInstance(Long id, boolean testing) {
		return getInstance(id, testing, false);
	}

	public static <V> ProcessStore<V> getInstance(Long id, boolean testing, boolean logging) {
		@SuppressWarnings("unchecked")
		ProcessStore<V> store = (ProcessStore<V>) instances.get(id);
		if (store == null) {
			store = new ProcessStore<>(testing, logging);
			instances.put(id, store);
		}

		return store;
	}

	public static <V> ProcessStore<V> removeInstance(Long id) {
		@SuppressWarnings("unchecked")
		ProcessStore<V> store = (ProcessStore<V>) instances.remove(id);

		return store;
	}

	public boolean checkPrecondition() {
		return execState == OK_STATE;
	}

	public String[] getArgs() {
		return args;
	}

	public void setArgs(String[] args) {
		this.args = args;
	}

	public short getExecState() {
		return execState;
	}

	public void setExecState(short execState) {
		this.execState = execState;
	}

	/**
	 * get current tierStack for this shell.
	 *
	 * @return Stack
	 */
	public IStack<Object> getTierStack() {
		if (tier < 0)
			return null;
		return tierStack.get(tier);
	}

	/**
	 * get Stack of the next tier, if no exist get a new one.
	 *
	 * @return Stack
	 */
	public IStack<Object> tierOneUp(boolean newStack) {
		++tier;
		IStack<Object> result;
		if (newStack || tierStack.size() == tier) {
			if (logging) {
				result = new LogStack<>(new ProcessStack<>(), System.out);
			} else {
				result = new ProcessStack<>();
			}
			tierStack.add(result);
		} else {
			result = getTierStack();
		}
		return result;
	}

	public IStack<Object> tierOneDown(boolean remove) {
		IStack<Object> result = null;
		if (remove) {
			result = removeTierStack();
		} else {
			if (tier > 0)
				--tier;
			result = getTierStack();
		}
		return result;
	}

	/**
	 * remove tierStack. Remove a tier.
	 *
	 * @return Stack
	 */
	protected IStack<Object> removeTierStack() {
		IStack<Object> result = tierStack.remove(tier);
		--tier;
		return result;
	}

	/**
	 * get stack for this shell.
	 *
	 * @return Stack
	 */
	public IStack<Object> getStack() {
		return stack;
	}

	/**
	 * get a Primitive variable, first from the highest block hierarchy down to
	 * the global variables
	 *
	 * @param key
	 *            name of the variable
	 * @return value of the variable
	 */
	public Primitive getPrimitiveVariable(Object key) {
		Primitive object = null;

		for (int i = working.size() - 1; i >= 0; --i) {
			Map<Object, Object> map = working.get(i);
			object = (Primitive) map.get(key);
			if (object != null)
				break;
		}
		if (object == null)
			object = (Primitive) global.get(key);

		return object;
	}

	/**
	 * get a variable, first from the highest block hierarchy down to the global
	 * variables.
	 *
	 * @param key
	 *            name of the variable
	 * @return value of the variable
	 */
	public Object getVariable(Object key) {
		Object object = null;

		for (int i = working.size() - 1; i >= 0; --i) {
			Map<Object, Object> map = working.get(i);
			object = map.get(key);
			if (object != null)
				break;
		}
		if (object == null)
			object = global.get(key);

		return object;
	}

	//      /**
	//       * get a Primitive variable, first from the highest block hierarchy down to the
	//       * global variables.
	//       *
	//       * @param key name of the variable
	//       * @return value of the variable
	//       */
	//      public Primitive getPrimitiveVariable(Object key) {
	//              Primitive object = null;
	//
	//              for (int i = working.size() - 1; i >= 0; --i) {
	//                      ExtHashMap map = (ExtHashMap) working.get(i);
	//                      object = map.getPrimitive(key);
	//                      if (object != null)
	//                              break;
	//              }
	//              if (object == null)
	//                      object = global.getPrimitive(key);
	//
	//              return object;
	//      }

	/**
	 * set an already defined variable, first from the highest block hierarchy
	 * down to the global variables.
	 *
	 * @param key
	 *            name of the variable
	 * @param value
	 *            value of the variable
	 * @return true if successfully assignd to an existing variable else false
	 */
	public boolean setVariable(Object key, Object value) {
		boolean success = false;
		Object object = null;

		for (int i = working.size() - 1; i >= 0; --i) {
			Map<Object, Object> map = working.get(i);
			object = map.get(key);
			if (object != null) {
				map.put(key, value);
				success = true;
				break;
			}
		}
		if (!success) {
			object = global.get(key);
			if (object != null) {
				global.put(key, value);
				success = true;
			}
		}
		return success;
	}

	/**
	 * set a new variable, on the highest block hierarchy or global if no
	 * hierarchy exists.
	 *
	 * @param key
	 *            name of the variable
	 * @param value
	 *            value of the variable
	 * @return true if at least one local block hierarchy exists else false
	 */
	public boolean setNewVariable(Object key, Object value) {
		boolean success = false;

		success = setLocalVariable(key, value);

		if (!success) {
			setGlobalVariable(key, value);
			success = true;
		}

		return success;
	}

	/**
	 * set a new local variable, on the highest block hierarchy.
	 *
	 * @param key
	 *            name of the variable
	 * @param value
	 *            value of the variable
	 * @return true if at least one local block hierarchy exists else false
	 */
	public boolean setLocalVariable(Object key, Object value) {
		boolean success = false;

		if (working.size() > 0) {
			Map<Object, Object> map = working.get(working.size() - 1);
			map.put(key, value);
			success = true;
		}
		return success;
	}

	/**
	 * set a new global variable, on the global hierarchy.
	 *
	 * @param key
	 *            name of the variable
	 * @param value
	 *            value of the variable
	 */
	public void setGlobalVariable(Object key, Object value) {
		global.put(key, value);
	}

	/**
	 * create and add a new variable map for a new block hierarchy.
	 */
	public void createNewBlockVariableMap() {
		working.add(new HashMap<>());
	}

	/**
	 * remove the last block hierarchy variable map.
	 *
	 * @return true if the last local block hierarchy could be removed else
	 *         false
	 */
	public boolean removeLastBlockVariableMap() {
		boolean success = false;

		if (testing) {
			success = true;
		} else {
			if (working.size() > 0) {
				if (working.remove(working.size() - 1) != null)
					success = true;
			}
		}
		return success;
	}

	/**
	 * removes all block hierarchy variable maps.
	 */
	public void removeAllBlockVariableMaps() {
		working.clear();
	}

	/**
	 * move current block hierarchy variable maps to the archive for later
	 * retreval.
	 */
	public void moveWorkingMapToArchive() {
		oldBlockHierarchy.add(working);
		working = new ArrayList<>();
		createNewBlockVariableMap();
	}

	/**
	 * restore the last block hierarchy variable map from archive to working.
	 *
	 * @return true if restored successfully else false
	 */
	public boolean restoreLastMapFromArchive() {
		boolean success = false;
		List<Map<Object, Object>> object = null;
		if (oldBlockHierarchy.size() > 0) {
			object = oldBlockHierarchy.remove(oldBlockHierarchy.size() - 1);
			if (object != null) {
				working = object;
				success = true;
			}
		}
		return success;
	}

	/**
	 * removes all block hierarchy variable maps from archive.
	 */
	public void removeAllMapsFromArchive() {
		oldBlockHierarchy.clear();
	}

	public void addMethod(MethodSignature methodSignature, MethodDeclaration method) {
		methods.put(methodSignature, method);
	}

	public MethodDeclaration getMethod(MethodSignature methodSignature) {
		return methods.get(methodSignature);
	}

	public MethodDeclaration removeMethod(MethodSignature methodSignature) {
		return methods.remove(methodSignature);
	}

	public Map<MethodSignature, MethodDeclaration> getMethods() {
		return methods;
	}

	public void clearMethods() {
		methods.clear();
	}
}
