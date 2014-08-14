/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.interpreter;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.uscexp.grappa.extension.nodes.AstNopTreeNode;
import com.github.uscexp.grappa.extension.nodes.AstTreeNode;

/**
 * @author haui
 *
 */
public class ProcessStoreTest {

	private ProcessStore<Object> processStoreSUT;
	
	@Before
	public void setup() {
		processStoreSUT = ProcessStore.getInstance(1L);
	}
	
	@After
	public void teardown() {
		ProcessStore.removeInstance(1L);
	}
	
	@Test
	public void testRemoveInstance() throws Exception {
		ProcessStore<Object> processStore = ProcessStore.getInstance(2L);
		
		assertNotNull(processStore);
		
		ProcessStore.removeInstance(2L);
		
		Field field = ProcessStore.class.getDeclaredField("instances");
		field.setAccessible(true);
		@SuppressWarnings("unchecked")
		Map<Long, ProcessStore<?>> map = (Map<Long, ProcessStore<?>>) field.get(null);
		
		ProcessStore<?> result = map.get(2L);
		
		assertNull(result);
	}

	@Test
	public void testCheckPrecondition() throws Exception {
		boolean result = processStoreSUT.checkPrecondition();
		
		assertTrue(result);
	}

	@Test
	public void testGetArgs() throws Exception {
		
		String[] args = { "arg1", "arg2" };
		processStoreSUT.setArgs(args );
		
		String[] result = processStoreSUT.getArgs();
		
		assertArrayEquals(args, result);
	}

	@Test
	public void testGetExecState() throws Exception {
		processStoreSUT.setExecState(ProcessStore.CONT_STATE);
		
		short result = processStoreSUT.getExecState();
		
		assertEquals(ProcessStore.CONT_STATE, result);
	}

	@Test
	public void testGetStack() throws Exception {
		Stack<Object> stack = processStoreSUT.getStack();
		
		assertNotNull(stack);
	}

	@Test
	public void testGetVariable() throws Exception {
		String key = "testKey";
		String value = "testValue";
		processStoreSUT.setNewVariable(key, value);
		
		String result = (String) processStoreSUT.getVariable(key);
		
		assertEquals(value, result);
	}

	@Test
	public void testSetVariableGlobal() throws Exception {
		String key = "testKey";
		String value = "testValue";
		processStoreSUT.setNewVariable(key, value);
		
		String value1 = "testValue1";
		processStoreSUT.setVariable(key, value1);
		
		String result = (String) processStoreSUT.getVariable(key);
		
		assertEquals(value1, result);
	}

	@Test
	public void testSetVariableGlobalNotExisting() throws Exception {
		String key = "testKey";
		String value = "testValue";
		processStoreSUT.setVariable(key, value);
		
		String result = (String) processStoreSUT.getVariable(key);
		
		assertNull(result);
	}

	@Test
	public void testSetVariableLocal() throws Exception {
		processStoreSUT.createNewBlockVariableMap();
		String key = "testKey";
		String value = "testValue";
		processStoreSUT.setNewVariable(key, value);
		
		String value1 = "testValue1";
		processStoreSUT.setVariable(key, value1);
		
		String result = (String) processStoreSUT.getVariable(key);
		
		assertEquals(value1, result);
	}

	@Test
	public void testSetNewVariable() throws Exception {
		String key = "testKey";
		String value = "testValue";
		processStoreSUT.setNewVariable(key, value);
		
		String result = (String) processStoreSUT.getVariable(key);
		
		assertEquals(value, result);
	}

	@Test
	public void testSetLocalVariable() throws Exception {
		processStoreSUT.createNewBlockVariableMap();
		String key = "testKey";
		String value = "testValue";
		processStoreSUT.setLocalVariable(key, value);
		
		String result = (String) processStoreSUT.getVariable(key);
		
		assertEquals(value, result);
	}

	@Test
	public void testSetGlobalVariable() throws Exception {
		String key = "testKey";
		String value = "testValue";
		processStoreSUT.setGlobalVariable(key, value);
		
		Field field = ProcessStore.class.getDeclaredField("global");
		field.setAccessible(true);
		@SuppressWarnings("unchecked")
		Map<Object, Object> map = (Map<Object, Object>) field.get(processStoreSUT);
		
		String result = (String) map.get(key);
		
		assertEquals(value, result);
	}

	@Test
	public void testCreateNewBlockVariableMap() throws Exception {
		processStoreSUT.createNewBlockVariableMap();
		
		Field field = ProcessStore.class.getDeclaredField("working");
		field.setAccessible(true);
		@SuppressWarnings("unchecked")
		List<Map<Object, Object>> result = (List<Map<Object, Object>>) field.get(processStoreSUT);
		
		assertEquals(1, result.size());
	}

	@Test
	public void testRemoveLastBlockVariableMap() throws Exception {
		processStoreSUT.createNewBlockVariableMap();
		
		boolean success = processStoreSUT.removeLastBlockVariableMap();
		
		assertTrue(success);
		
		Field field = ProcessStore.class.getDeclaredField("working");
		field.setAccessible(true);
		@SuppressWarnings("unchecked")
		List<Map<Object, Object>> result = (List<Map<Object, Object>>) field.get(processStoreSUT);
		
		assertEquals(0, result.size());
	}

	@Test
	public void testRemoveAllBlockVariableMaps() throws Exception {
		processStoreSUT.createNewBlockVariableMap();
		processStoreSUT.createNewBlockVariableMap();
		
		processStoreSUT.removeAllBlockVariableMaps();
		
		Field field = ProcessStore.class.getDeclaredField("working");
		field.setAccessible(true);
		@SuppressWarnings("unchecked")
		List<Map<Object, Object>> result = (List<Map<Object, Object>>) field.get(processStoreSUT);
		
		assertEquals(0, result.size());
	}

	@Test
	public void testMoveWorkingMapToArchive() throws Exception {
		processStoreSUT.createNewBlockVariableMap();
		String key = "testKey";
		String value = "testValue";
		processStoreSUT.setNewVariable(key, value);
		
		processStoreSUT.moveWorkingMapToArchive();
		
		Field field = ProcessStore.class.getDeclaredField("working");
		field.setAccessible(true);
		@SuppressWarnings("unchecked")
		List<Map<Object, Object>> result = (List<Map<Object, Object>>) field.get(processStoreSUT);
		
		assertEquals(1, result.size());
		assertEquals(0, result.get(0).size());

		field = ProcessStore.class.getDeclaredField("oldBlockHierarchy");
		field.setAccessible(true);
		@SuppressWarnings("unchecked")
		List<List<Map<Object, Object>>> result1 = (List<List<Map<Object, Object>>>) field.get(processStoreSUT);
		
		assertEquals(1, result1.size());
	}

	@Test
	public void testRestoreLastMapFromArchive() throws Exception {
		processStoreSUT.createNewBlockVariableMap();
		String key = "testKey";
		String value = "testValue";
		processStoreSUT.setNewVariable(key, value);
		
		processStoreSUT.moveWorkingMapToArchive();

		boolean success = processStoreSUT.restoreLastMapFromArchive();
		
		assertTrue(success);

		Field field = ProcessStore.class.getDeclaredField("working");
		field.setAccessible(true);
		@SuppressWarnings("unchecked")
		List<Map<Object, Object>> result = (List<Map<Object, Object>>) field.get(processStoreSUT);
		
		assertEquals(1, result.size());
		assertEquals(1, result.get(0).size());

		field = ProcessStore.class.getDeclaredField("oldBlockHierarchy");
		field.setAccessible(true);
		@SuppressWarnings("unchecked")
		List<List<Map<Object, Object>>> result1 = (List<List<Map<Object, Object>>>) field.get(processStoreSUT);
		
		assertEquals(0, result1.size());
	}

	@Test
	public void testRemoveAllMapsFromArchive() throws Exception {
		processStoreSUT.createNewBlockVariableMap();
		
		processStoreSUT.moveWorkingMapToArchive();

		processStoreSUT.removeAllMapsFromArchive();
		
		Field field = ProcessStore.class.getDeclaredField("working");
		field.setAccessible(true);
		@SuppressWarnings("unchecked")
		List<Map<Object, Object>> result = (List<Map<Object, Object>>) field.get(processStoreSUT);
		
		assertEquals(1, result.size());

		field = ProcessStore.class.getDeclaredField("oldBlockHierarchy");
		field.setAccessible(true);
		@SuppressWarnings("unchecked")
		List<List<Map<Object, Object>>> result1 = (List<List<Map<Object, Object>>>) field.get(processStoreSUT);
		
		assertEquals(0, result1.size());
	}

	@Test
	public void testGetMethod() throws Exception {
		AstNopTreeNode<Object> astNopTreeNode = new AstNopTreeNode<>(null, null);
		String method = "testMethod";
		processStoreSUT.addMethod(method, astNopTreeNode);
		
		AstTreeNode<Object> result = processStoreSUT.getMethod(method);
		
		assertEquals(astNopTreeNode, result);
	}

	@Test
	public void testRemoveMethod() throws Exception {
		AstNopTreeNode<Object> astNopTreeNode = new AstNopTreeNode<>(null, null);
		String method = "testMethod";
		processStoreSUT.addMethod(method, astNopTreeNode);
		
		processStoreSUT.removeMethod(method);
		
		AstTreeNode<Object> result = processStoreSUT.getMethod(method);
		
		assertNull(result);
	}

	@Test
	public void testClearMethods() throws Exception {
		AstNopTreeNode<Object> astNopTreeNode = new AstNopTreeNode<>(null, null);
		String method = "testMethod";
		processStoreSUT.addMethod(method, astNopTreeNode);
		
		processStoreSUT.clearMethods();
		
		AstTreeNode<Object> result = processStoreSUT.getMethod(method);
		
		assertNull(result);
	}

}
