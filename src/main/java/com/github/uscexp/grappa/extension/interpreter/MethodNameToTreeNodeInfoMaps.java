/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.interpreter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.github.uscexp.grappa.extension.annotations.AstCommand;
import com.github.uscexp.grappa.extension.annotations.AstValue;

/**
 * @author haui
 *
 */
public class MethodNameToTreeNodeInfoMaps {

	private Map<String, Object> methodNameToAnnotationTypeMap = new HashMap<>();
	private Map<String, Class<?>> methodNameToNodeImplementationClassMap = new HashMap<>();
	
	public void putMethodNameToTreeNodeImplementation(String name,
			Class<?> implementationClass) {
		methodNameToNodeImplementationClassMap.put(name, implementationClass);
	}

	public void putMethodNameToTreeNodeAnnotationType(String name,
			AstValue astValue) {
		if(astValue != null)
			methodNameToAnnotationTypeMap.put(name, astValue);
	}

	public void putMethodNameToTreeNodeAnnotationType(String name,
			AstCommand astCommand) {
		if(astCommand != null)
			methodNameToAnnotationTypeMap.put(name, astCommand);
	}

	public Object getAnnotationTypeForMethodName(String methodName) {
		return methodNameToAnnotationTypeMap.get(methodName);
	}

	public Class<?> getImplementationClassForMethodName(String methodName) {
		return methodNameToNodeImplementationClassMap.get(methodName);
	}

	public Set<String> getMethodNames() {
		return methodNameToNodeImplementationClassMap.keySet();
	}

	public Object getAnnotationType(String methodName) {
		return methodNameToAnnotationTypeMap.get(methodName);
	}
}
