/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.nodes.treeconstruction;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import com.github.fge.grappa.matchers.base.Matcher;
import com.github.fge.grappa.parsers.BaseParser;
import com.github.fge.grappa.run.ParseRunnerListener;
import com.github.fge.grappa.run.context.Context;
import com.github.fge.grappa.run.context.MatcherContext;
import com.github.fge.grappa.run.events.MatchFailureEvent;
import com.github.fge.grappa.run.events.MatchSuccessEvent;
import com.github.fge.grappa.run.events.PostParseEvent;
import com.github.fge.grappa.run.events.PreMatchEvent;
import com.github.fge.grappa.run.events.PreParseEvent;
import com.github.fge.grappa.support.Position;
import com.github.uscexp.grappa.extension.annotations.AstCommand;
import com.github.uscexp.grappa.extension.annotations.AstValue;
import com.github.uscexp.grappa.extension.interpreter.MethodNameToTreeNodeInfoMaps;
import com.github.uscexp.grappa.extension.nodes.AstNopTreeNode;
import com.github.uscexp.grappa.extension.nodes.AstTreeNode;
import com.github.uscexp.grappa.extension.nodes.AstValueTreeNode;

/**
 * @author haui
 *
 */
public class AstTreeNodeBuilder<V> extends ParseRunnerListener<V> {
	
	private static final String AST_COMMAND_TREENODE_SUFFIX = "TreeNode";
	private static final String AST_COMMAND_TREENODE_PREFIX = "Ast";

	private MethodNameToTreeNodeInfoMaps methodNameToTreeNodeInfoMaps;
	final private boolean removeAstNopTreeNodes;
    private final SortedMap<Integer, AstTreeNode<V>> nodes = new TreeMap<>();
	final private Class<BaseParser<V>> parserClass;
	private AstTreeNode<V> rootNode;
	private List<String> matchFailureMessages = new ArrayList<>();

	@SuppressWarnings("unchecked")
	public AstTreeNodeBuilder(Class<? extends BaseParser<?>> parserClass, boolean removeAstNopTreeNodes) {
		super();
		this.parserClass = (Class<BaseParser<V>>) parserClass;
		methodNameToTreeNodeInfoMaps = findImplementationClassesAndAnnotationTypes(this.parserClass);
		this.removeAstNopTreeNodes = removeAstNopTreeNodes;
	}
	
	public String getParsingErrors() {
		StringBuilder stringBuilder = new StringBuilder();
		
		for (String failure : matchFailureMessages) {
			if(stringBuilder.length() != 0) {
				stringBuilder.append('\n');
			}
			stringBuilder.append(failure);
		}
		
		return stringBuilder.toString();
	}

	@Override
	public void beforeParse(PreParseEvent<V> event) {
		super.beforeParse(event);
	}

	@Override
	public void beforeMatch(PreMatchEvent<V> event) {
        final Context<V> context = event.getContext();
        final int level = context.getLevel();
        String ruleLabel = context.getMatcher().getLabel();
        
        AstTreeNode<V> node = null;
    	try {
			node = createAstTreeNode(ruleLabel, "");
		} catch (ReflectiveOperationException e) {
			throw new RuntimeException(e);
		}

//    	if(!removeAstNopTreeNodes || level == 0 || !AstNopTreeNode.class.equals(node.getClass()))
    	nodes.put(level, node);
	}

	@Override
	public void matchSuccess(MatchSuccessEvent<V> event) {
        final Context<V> context = event.getContext();
        final int level = context.getLevel();
        final String inputForNode = getInputForNode(context);
        
        AstTreeNode<V> node = nodes.get(level);
        
        if(node != null) {
        	matchFailureMessages.clear(); // earlier failures are irrelevant, because the parse found a match.
        	node.setValue(inputForNode);
	        if(level == 0) {
	        	rootNode = node;
	        }
	        
	        if(!nodes.headMap(level).isEmpty()) {
		        final int previousLevel = nodes.headMap(level).lastKey();
		
		        if(!nodes.get(previousLevel).getChildren().contains(node)) {
		        	AstTreeNode<V> parent = nodes.get(previousLevel);
		        	parent.getChildren().add(node);
		        	node.setParent(parent);
		        }
	        }
        }
	}

	@Override
	public void matchFailure(MatchFailureEvent<V> event) {
	       final Context<V> context = event.getContext();
	        Position position = context.getPosition();
	        int line = position.getLine();
	        int column = position.getColumn();
	        
	        String msg = String.format("Matching failure: Path: %s, at line %d, column %d", context.toString(), line, column);
			matchFailureMessages.add(msg);
	}

	@Override
	public void afterParse(PostParseEvent<V> event) {
		if(removeAstNopTreeNodes && rootNode != null)
			removeAstNopTreeNodes(rootNode);
	}

	public static <V> void removeAstNopTreeNodes(AstTreeNode<V> rootNode) {
		removeAstNopTreeNodes(rootNode, rootNode, rootNode);
	}

	@SuppressWarnings("unchecked")
	private static <V> void removeAstNopTreeNodes(AstTreeNode<V> rootNode, AstTreeNode<V> node, AstTreeNode<V> parentNode) {
		for (AstTreeNode<V> childNode : node.getChildren().toArray(new AstTreeNode[node.getChildren().size()])) {
			if(AstNopTreeNode.class.isAssignableFrom(childNode.getClass())) {
				node.getChildren().remove(childNode);
				removeAstNopTreeNodes(rootNode, childNode, parentNode);
			} else {
				if(AstNopTreeNode.class.isAssignableFrom(node.getClass()) && parentNode != rootNode) {
					parentNode.getChildren().add(childNode);
					childNode.setParent(parentNode);
				} else {
					node.getChildren().remove(childNode);
					parentNode.getChildren().add(childNode);
				}
				removeAstNopTreeNodes(rootNode, childNode, childNode);
			}
		}
	}

	public AstTreeNode<V> getRootNode() {
		return rootNode;
	}

	public static MethodNameToTreeNodeInfoMaps findImplementationClassesAndAnnotationTypes(Class<?> parserClass) {
		MethodNameToTreeNodeInfoMaps methodNameToTreeNodeInfoMaps = new MethodNameToTreeNodeInfoMaps();
		Method[] methods = getAllDeclaredMethods(parserClass);
		for (int i = 0; i < methods.length; i++) {
			AstValue astValue = methods[i].getAnnotation(AstValue.class);
			AstCommand astCommand = methods[i].getAnnotation(AstCommand.class);

			if ((astCommand != null) || (astValue != null)) {
				Class<?> implementationClass = findImplementationClass(parserClass, methods[i].getName());
				methodNameToTreeNodeInfoMaps.putMethodNameToTreeNodeImplementation(methods[i].getName(), implementationClass);
			}
			if(astCommand != null)
				methodNameToTreeNodeInfoMaps.putMethodNameToTreeNodeAnnotationType(methods[i].getName(), astCommand);
			else if(astValue != null)
				methodNameToTreeNodeInfoMaps.putMethodNameToTreeNodeAnnotationType(methods[i].getName(), astValue);
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
	
	private static Method[] getAllDeclaredMethods(Class<?> clazz) {
		List<Method> methods = new ArrayList<>();
		
		getAllDeclaredMethods(clazz, methods);
				
		return methods.toArray(new Method[methods.size()]);
	}

	private static void getAllDeclaredMethods(Class<?> clazz, List<Method> methods) {
		Method[] declaredMethods = clazz.getDeclaredMethods();
		for (Method method : declaredMethods) {
			methods.add(method);
		}
		Class<?> superClass = clazz.getSuperclass();
		if(superClass != null && !superClass.isAssignableFrom(BaseParser.class)) {
			getAllDeclaredMethods(superClass, methods);
		}
	}

	private AstTreeNode<V> createAstTreeNode(Matcher matcher, MatcherContext<?> context) throws ReflectiveOperationException {
		Object annotation = methodNameToTreeNodeInfoMaps.getAnnotationTypeForMethodName(matcher.getLabel());
		List<?> parseNodeChildren = matcher.getChildren();
		String value = getInputForNode(context);
		AstTreeNode<V> result = null;

		if (annotation != null) {
			result = createTreeNode(matcher.getLabel(), annotation, value);
		} else {
			result = new AstNopTreeNode<>(matcher.getLabel(), value);
		}
		result = iterateOverChildren(context, parseNodeChildren, result);
		return result;
	}

	private AstTreeNode<V> createAstTreeNode(String label, String inputBuffer) throws ReflectiveOperationException {
		Object annotation = methodNameToTreeNodeInfoMaps.getAnnotationTypeForMethodName(label);
		AstTreeNode<V> result = null;

		if (annotation != null) {
			result = createTreeNode(label, annotation, inputBuffer);
		} else {
			result = new AstNopTreeNode<>(label, inputBuffer);
		}
		return result;
	}

	private AstTreeNode<V> createTreeNode(String label, Object annotation, String value) throws ReflectiveOperationException {
		AstTreeNode<V> result = new AstNopTreeNode<>(label, value);
		if (annotation instanceof AstValue) {
			result = createAstValue(label, (AstValue) annotation, value);
		} else if (annotation instanceof AstCommand) {
			result = createAstCommand(label, (AstCommand) annotation, value);
		}
		return result;
	}

	private AstTreeNode<V> createAstValue(String label, AstValue annotation, String value) {
		AstTreeNode<V> result;
		Class<?> valueType = annotation.valueType();
		Class<?> factoryClass = annotation.factoryClass();
		String factoryMethod = annotation.factoryMethod();
		result = new AstValueTreeNode<>(label, value, valueType, factoryClass, factoryMethod);
		return result;
	}

	private AstTreeNode<V> createAstCommand(String label, AstCommand astCommand, String value) throws ReflectiveOperationException {
		AstTreeNode<V> result;
		String classname = getClassname(label, astCommand, methodNameToTreeNodeInfoMaps);
		@SuppressWarnings("unchecked")
		Class<AstTreeNode<V>> commandClass = (Class<AstTreeNode<V>>) Class.forName(classname);
		Constructor<AstTreeNode<V>> constructor = commandClass.getDeclaredConstructor(String.class, String.class);
		result = constructor.newInstance(label, value);
		return result;
	}

	public static String getClassname(String method, AstCommand astCommand, MethodNameToTreeNodeInfoMaps methodNameToTreeNodeInfoMaps) {
		String classname = astCommand.classname();
		if (classname.isEmpty()) {
			String label = method.substring(0, 1).toUpperCase() + method.substring(1);
			String methodName = AST_COMMAND_TREENODE_PREFIX + label + AST_COMMAND_TREENODE_SUFFIX;
			Class<?> clazz = methodNameToTreeNodeInfoMaps.getImplementationClassForMethodName(method);
			Package astTreeNodePackage = clazz.getPackage();
			classname = astTreeNodePackage.getName() + "." + methodName;
		}
		return classname;
	}

	private AstTreeNode<V> iterateOverChildren(MatcherContext<?> context, List<?> parseNodeChildren, AstTreeNode<V> result) throws ReflectiveOperationException {
		if ((parseNodeChildren != null) && !parseNodeChildren.isEmpty()) {
			for (Object parseNode : parseNodeChildren) {
				Matcher matcher = (Matcher) parseNode;
				MatcherContext<?> matcherContext = matcher.getSubContext(context);
				AstTreeNode<V> astTreeNode = createAstTreeNode(matcher, matcherContext);
				if (result == null) {
					result = astTreeNode;
				} else {
					if (astTreeNode != null) {
						result.getChildren().add(astTreeNode);
					}
				}
			}
		}
		return result;
	}

    private String getInputForNode(final Context<?> context){
        final int start = context.getStartIndex();
        int end = context.getCurrentIndex();
        return context.getInputBuffer().extract(start, end);
    }
}
