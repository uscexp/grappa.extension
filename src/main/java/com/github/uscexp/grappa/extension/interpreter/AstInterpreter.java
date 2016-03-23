/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.interpreter;

import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

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
import com.github.uscexp.grappa.extension.util.AstTreeUtil;

/**
 * The {@link AstInterpreter} interpretes a PEG defined language based on {@link BaseParser}. First it creates an AST (Abstract Syntax Tree)
 * based on the {@link ParsingResult} and the annotated parser methods, annotated with {@link AstValue} or {@link AstCommand}. {@link
 * AstValue} writes the parse node value to the interpreter stack. By default the interpreter stores the values as {@link String}, if one
 * wants to change that, one has to define the corresponding parameters of the {@link AstValue} annotation. {@link AstCommand} must be
 * implemented from a developer, which has to create the Ast<Name>TreeNode and implement it. This represents the logic of your language
 * commands. The {@link AstCommand} classes by default are located in the same package as the parser implementation and have the naming
 * convetion of Ast + name of your parser rule (first char upper case) + TreeNode. If one want to change that, one has to define the
 * corresponding parameters of the {@link AstCommand} annotation.
 *
 * @author  haui
 */
public class AstInterpreter<V> {

	private static final String AST_COMMAND_TREENODE_SUFFIX = "TreeNode";
	private static final String AST_COMMAND_TREENODE_PREFIX = "Ast";
	private AstTreeNode<V> root;
	private MethodNameToTreeNodeInfoMaps methodNameToTreeNodeInfoMaps;
	private OutputStream astTreeOutputStream = null;
	private boolean skipCreatingAstNopTreeNodes = false;
	

	public AstInterpreter() {
		this(false, null);
	}

	public AstInterpreter(boolean skipCreatingAstNopTreeNodes) {
		this(skipCreatingAstNopTreeNodes, null);
	}

	public AstInterpreter(OutputStream astTreeOutputStream) {
		this(false, astTreeOutputStream);
	}

	public AstInterpreter(boolean skipCreatingAstNopTreeNodes, OutputStream astTreeOutputStream) {
		super();
		this.astTreeOutputStream = astTreeOutputStream;
		this.skipCreatingAstNopTreeNodes = skipCreatingAstNopTreeNodes;
	}

	public void interpretBackwardOrder(Class<? extends BaseParser<V>> parserClass, ParsingResult<?> parsingResult, Long id)
		throws AstInterpreterException {
		interpret(parserClass, parsingResult, id, false);
	}

	public void interpretForewardOrder(Class<? extends BaseParser<V>> parserClass, ParsingResult<?> parsingResult, Long id)
		throws AstInterpreterException {
		interpret(parserClass, parsingResult, id, true);
	}

	private void interpret(Class<? extends BaseParser<V>> parserClass, ParsingResult<?> parsingResult, Long id, boolean forewardOrder)
		throws AstInterpreterException {
		try {
			createAstTreeNodeAndInterpret(parserClass, parsingResult, id, forewardOrder);
		} catch (Exception e) {
			throw new AstInterpreterException(String.format("Can't build/interpret AstTreeNodes for parser class %s",
					parserClass.getName()), e);
		}
	}

	private void createAstTreeNodeAndInterpret(Class<? extends BaseParser<V>> parserClass, ParsingResult<?> parsingResult, Long id,
			boolean forewardOrder)
		throws ReflectiveOperationException, Exception {
		ProcessStore.getInstance(id);

		methodNameToTreeNodeInfoMaps = findImplementationClassesAndAnnotationTypes(parserClass);

		root = createAstTreeNode(parsingResult.parseTreeRoot, parsingResult.inputBuffer, forewardOrder);
		
		if(astTreeOutputStream != null) {
			AstTreeUtil.printAstTree(root, astTreeOutputStream);
		}

		root.interpretIt(id);
	}

	public static MethodNameToTreeNodeInfoMaps findImplementationClassesAndAnnotationTypes(Class<?> parserClass) {
		MethodNameToTreeNodeInfoMaps methodNameToTreeNodeInfoMaps = new MethodNameToTreeNodeInfoMaps();
		Method[] declaredMethods = parserClass.getDeclaredMethods();
		for (int i = 0; i < declaredMethods.length; i++) {
			AstValue astValue = declaredMethods[i].getAnnotation(AstValue.class);
			AstCommand astCommand = declaredMethods[i].getAnnotation(AstCommand.class);

			if ((astCommand != null) || (astValue != null)) {
				Class<?> implementationClass = findImplementationClass(parserClass.getSuperclass(), declaredMethods[i].getName());
				if (implementationClass == null) {
					implementationClass = parserClass;
				}
				methodNameToTreeNodeInfoMaps.putMethodNameToTreeNodeImplementation(declaredMethods[i].getName(), implementationClass);
			}
			if(astCommand != null)
				methodNameToTreeNodeInfoMaps.putMethodNameToTreeNodeAnnotationType(declaredMethods[i].getName(), astCommand);
			else if(astValue != null)
				methodNameToTreeNodeInfoMaps.putMethodNameToTreeNodeAnnotationType(declaredMethods[i].getName(), astValue);
		}
		return methodNameToTreeNodeInfoMaps;
	}

	protected static Class<?> findImplementationClass(Class<?> clazz, String methodName) {
		Class<?> result = null;

		Method[] methods = findMethods(clazz, methodName);

		if (methods.length > 0) {
			result = clazz;
		} else {
			result = findImplementationClassInSuperclass(clazz, methodName);
		}

		return result;
	}

	private static Class<?> findImplementationClassInSuperclass(Class<?> clazz, String methodName) {
		Class<?> result = null;
		Class<?> superClass = clazz.getSuperclass();

		if (superClass != null) {
			result = findImplementationClass(superClass, methodName);
		}
		return result;
	}

	protected static Method[] findMethods(Class<?> clazz, String methodName) {
		List<Method> methods = new ArrayList<>();

		Method[] declaredMethods = clazz.getDeclaredMethods();
		for (int i = 0; i < declaredMethods.length; i++) {
			if (declaredMethods[i].getName().equals(methodName)) {
				methods.add(declaredMethods[i]);
			}
		}

		return methods.toArray(new Method[methods.size()]);
	}

	private AstTreeNode<V> createAstTreeNode(Node<?> node, InputBuffer inputBuffer, boolean forewardOrder)
		throws ReflectiveOperationException {
		Object annotation = methodNameToTreeNodeInfoMaps.getAnnotationTypeForMethodName(node.getLabel());
		List<?> parseNodeChildren = node.getChildren();
		String value = ParseTreeUtils.getNodeText(node, inputBuffer);
		AstTreeNode<V> result = null;

		if (annotation != null) {
			result = createTreeNode(node, annotation, value);
		} else if(!skipCreatingAstNopTreeNodes) {
			result = new AstNopTreeNode<>(node, value);
		}
		result = iterateOverChildren(inputBuffer, forewardOrder, parseNodeChildren, result);
		return result;
	}

	private AstTreeNode<V> createTreeNode(Node<?> node, Object annotation, String value)
		throws ReflectiveOperationException {
		AstTreeNode<V> result = new AstNopTreeNode<>(node, value);
		if (annotation instanceof AstValue) {
			result = createAstValue(node, (AstValue) annotation, value);
		} else if (annotation instanceof AstCommand) {
			result = createAstCommand(node, (AstCommand) annotation, value);
		}
		return result;
	}

	private AstTreeNode<V> createAstValue(Node<?> node, AstValue annotation, String value) {
		AstTreeNode<V> result;
		Class<?> valueType = annotation.valueType();
		Class<?> factoryClass = annotation.factoryClass();
		String factoryMethod = annotation.factoryMethod();
		result = new AstValueTreeNode<>(node, value, valueType, factoryClass, factoryMethod);
		return result;
	}

	private AstTreeNode<V> createAstCommand(Node<?> node, AstCommand astCommand, String value)
		throws ReflectiveOperationException {
		AstTreeNode<V> result;
		String classname = getClassname(node.getLabel(), astCommand, methodNameToTreeNodeInfoMaps);
		@SuppressWarnings("unchecked")
		Class<AstTreeNode<V>> commandClass = (Class<AstTreeNode<V>>) Class.forName(classname);
		Constructor<AstTreeNode<V>> constructor = commandClass.getDeclaredConstructor(Node.class, String.class);
		result = constructor.newInstance(node, value);
		return result;
	}

	public static String getClassname(String method, AstCommand astCommand, MethodNameToTreeNodeInfoMaps methodNameToTreeNodeInfoMaps) {
		String classname = astCommand.classname();
		if (classname.equals("")) {
			String label = method.substring(0, 1).toUpperCase() + method.substring(1);
			String methodName = AST_COMMAND_TREENODE_PREFIX + label + AST_COMMAND_TREENODE_SUFFIX;
			Class<?> clazz = methodNameToTreeNodeInfoMaps.getImplementationClassForMethodName(method);
			Package astTreeNodePackage = clazz.getPackage();
			classname = astTreeNodePackage.getName() + "." + methodName;
		}
		return classname;
	}

	private AstTreeNode<V> iterateOverChildren(InputBuffer inputBuffer, boolean forewardOrder, List<?> parseNodeChildren, AstTreeNode<V> result)
		throws ReflectiveOperationException {
		if ((parseNodeChildren != null) && !parseNodeChildren.isEmpty()) {
			for (Object parseNode : parseNodeChildren) {
				AstTreeNode<V> astTreeNode = createAstTreeNode((Node<?>) parseNode, inputBuffer, forewardOrder);
				if(result == null) {
					result = astTreeNode;
				} else {
					if(astTreeNode != null) {
						if (forewardOrder)
							result.addChild(result.getChildren().size(), astTreeNode);
						else
							result.addChild(0, astTreeNode);
					}
				}
			}
		}
		return result;
	}

	public void cleanUp(Long id) {
		ProcessStore.removeInstance(id);
	}

}
