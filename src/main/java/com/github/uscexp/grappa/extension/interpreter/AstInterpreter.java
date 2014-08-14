/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.interpreter;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.parboiled.BaseParser;
import org.parboiled.Node;
import org.parboiled.buffers.InputBuffer;
import org.parboiled.support.ParseTreeUtils;
import org.parboiled.support.ParsingResult;

import com.github.uscexp.grappa.extension.annotations.AstCommand;
import com.github.uscexp.grappa.extension.annotations.AstValue;
import com.github.uscexp.grappa.extension.exception.AstInterpreterException;
import com.github.uscexp.grappa.extension.nodes.AstNopTreeNode;
import com.github.uscexp.grappa.extension.nodes.AstTreeNode;
import com.github.uscexp.grappa.extension.nodes.AstValueTreeNode;

/**
 * The {@link AstInterpreter} interpretes a PEG defined language based on {@link BaseParser}.
 * First it creates an AST (Abstract Syntax Tree) based on the {@link ParsingResult} and
 * the annotated parser methods, annotated with {@link AstValue} or {@link AstCommand}.
 * {@link AstValue} writes the parse node value to the interpreter stack.
 * By default the interpreter stores the values as {@link String}, if one wants to change that, one
 * has to define the corresponding parameters of the {@link AstValue} annotation.
 * {@link AstCommand} must be implemented from a developer, which has to create the Ast<Name>TreeNode
 * and implement it. This represents the logic of your language commands.
 * The {@link AstCommand} classes by default are located in the same package as the parser implementation
 * and have the naming convetion of Ast + name of your parser rule (first char upper case) + TreeNode.
 * If one want to change that, one has to define the corresponding parameters of the {@link AstCommand}
 * annotation.
 * 
 * @author haui
 *
 */
public class AstInterpreter<V> {

	private static final String AST_COMMAND_TREENODE_SUFFIX = "TreeNode";
	private static final String AST_COMMAND_TREENODE_PREFIX = "Ast";
	private Class<? extends BaseParser<V>> parserClass;
	private AstTreeNode<V> root;
	private Map<String, Object> annotationMap = new HashMap<>();
	private Map<String, Class<?>> classMap = new HashMap<>();

	public void execute(Class<? extends BaseParser<V>> parserClass, ParsingResult<?> parsingResult, Long id)
		throws AstInterpreterException {
		this.parserClass = parserClass;
		try {
			ProcessStore.getInstance(id);

			getAnnotations(parserClass);

			root = createAstTreeNode(parsingResult.parseTreeRoot, parsingResult.inputBuffer);
			
			root.interpretIt(id);
		} catch (Exception e) {
			throw new AstInterpreterException(String.format("Can't build/interpret AstTreeNodes for parser class %s",
					parserClass.getName()), e);
		}
	}
	
	public void cleanUp(Long id) {
		ProcessStore.removeInstance(id);
	}

	private void getAnnotations(Class<?> parserClass) {
		Method[] declaredMethods = parserClass.getDeclaredMethods();
		for (int i = 0; i < declaredMethods.length; i++) {
			AstValue astValue = declaredMethods[i].getAnnotation(AstValue.class);
			AstCommand astCommand = declaredMethods[i].getAnnotation(AstCommand.class);

			if (astCommand != null || astValue != null) {
				Class<?> implementationClass = findImplementationClass(parserClass.getSuperclass(), declaredMethods[i].getName());
				if(implementationClass == null) {
					implementationClass = parserClass;
				}
				classMap.put(declaredMethods[i].getName(), implementationClass);
			}
			if (astCommand != null) {
				// find class which implements the method. skip parserClass because it is a byte code enhanced
				// class which overides every method.
				annotationMap.put(declaredMethods[i].getName(), astCommand);
			} else if (astValue != null) {
				annotationMap.put(declaredMethods[i].getName(), astValue);
			}
		}
	}
	
	protected Class<?> findImplementationClass(Class<?> clazz, String methodName) {
		Class<?> result = null;
		
		Method[] methods = findMethods(clazz, methodName);
		
		if(methods.length > 0) {
			result = clazz;
		} else {
			Class<?> superClass = clazz.getSuperclass();
			
			if(superClass != null) {
				result = findImplementationClass(superClass, methodName);
			}
		}
		
		return result;
	}
	
	protected Method[] findMethods(Class<?> clazz, String methodName) {
		List<Method> methods = new ArrayList<>();
		
		Method[] declaredMethods = clazz.getDeclaredMethods();
		for (int i = 0; i < declaredMethods.length; i++) {
			if(declaredMethods[i].getName().equals(methodName)) {
				methods.add(declaredMethods[i]);
			}
		}
		
		return methods.toArray(new Method[methods.size()]);
	}

	private AstTreeNode<V> createAstTreeNode(Node<?> node, InputBuffer inputBuffer)
		throws ReflectiveOperationException {
		Object annotation = annotationMap.get(node.getLabel());
		List<?> parseNodeChildren = node.getChildren();
		String value = ParseTreeUtils.getNodeText(node, inputBuffer);
		AstTreeNode<V> result = new AstNopTreeNode<>(node, value);

		if (annotation != null) {
			if (annotation instanceof AstValue) {
				Class<?> valueType = ((AstValue) annotation).valueType();
				Class<?> factoryClass = ((AstValue) annotation).factoryClass();
				String factoryMethod = ((AstValue) annotation).factoryMethod();
				result = new AstValueTreeNode<>(node, value, valueType, factoryClass, factoryMethod);
			} else if (annotation instanceof AstCommand) {
				AstCommand astCommand = (AstCommand) annotation;
				String classname = astCommand.classname();
				if (classname.equals("")) {
					String label = node.getLabel();
					label = label.substring(0, 1).toUpperCase() + label.substring(1);
					String methodName = AST_COMMAND_TREENODE_PREFIX + label + AST_COMMAND_TREENODE_SUFFIX;
					Class<?> clazz = classMap.get(node.getLabel()); 
					Package astTreeNodePackage = clazz.getPackage();
					classname = astTreeNodePackage.getName() + "." + methodName;
				}
				@SuppressWarnings("unchecked")
				Class<AstTreeNode<V>> commandClass = (Class<AstTreeNode<V>>) Class.forName(classname);
				Constructor<AstTreeNode<V>> constructor = commandClass.getDeclaredConstructor(Node.class, String.class);
				result = constructor.newInstance(node, value);
			}
		} else {
			result = new AstNopTreeNode<>(node, value);
		}
		if(parseNodeChildren != null && !parseNodeChildren.isEmpty()) {
			for (Object parseNode : parseNodeChildren) {
				AstTreeNode<V> astTreeNode = createAstTreeNode((Node<?>) parseNode, inputBuffer);
				result.addChild(0, astTreeNode);
			}
		}
		return result;
	}

}
