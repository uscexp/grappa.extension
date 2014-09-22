/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.parser.peg;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.parboiled.BaseParser;
import org.parboiled.Node;
import org.parboiled.trees.TreeNode;

import com.github.uscexp.grappa.extension.codegenerator.PegParserGenerator;
import com.github.uscexp.grappa.extension.codegenerator.ReservedJavaWords;
import com.github.uscexp.grappa.extension.interpreter.ProcessStore;
import com.github.uscexp.grappa.extension.nodes.AstCommandTreeNode;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;

/**
 * Base command {@link TreeNode} for PEG generator.
 * 
 * @author haui
 */
public class AstPegBaseTreeNode<V> extends AstCommandTreeNode<V> {
	
	protected ProcessStore<String> processStore;
	protected ProcessStore<String> openProcessStore;
	protected ProcessStore<String> closeProcessStore;
	protected JCodeModel codeModel;
	protected JDefinedClass definedClass;
	private List<Field> constants = new ArrayList<>();
	private Map<String, String> methodNameMap = new HashMap<>();
	private Map<String, Boolean> existenceMap = new HashMap<>();

	public AstPegBaseTreeNode(Node<?> node, String value) {
		super(node, value);
		Field[] fields = BaseParser.class.getDeclaredFields();
		
		for (Field field : fields) {
			if(Modifier.isPublic(field.getModifiers()) && Modifier.isFinal(field.getModifiers()) && Modifier.isStatic(field.getModifiers())) {
				constants.add(field);
			}
		}
	}

	@Override
	protected void interpret(Long id) throws ReflectiveOperationException {
		processStore = ProcessStore.getInstance(id);
		openProcessStore = ProcessStore.getInstance(id + PegParserGenerator.OPEN);
		closeProcessStore = ProcessStore.getInstance(id + PegParserGenerator.CLOSE);
		codeModel = (JCodeModel) processStore.getVariable(PegParserGenerator.CODE_MODEL);
		definedClass = (JDefinedClass) processStore.getVariable(PegParserGenerator.DEFINED_CLASS);
	}


	protected String checkPostponedAction(String bodyString) {
		String openMethod;
		openMethod = "";
		if(!openProcessStore.getStack().isEmpty()) {
			openMethod = (String) openProcessStore.getStack().peek();
		}
		String peek = "";
		if(!closeProcessStore.getStack().isEmpty()) {
			peek = (String) closeProcessStore.getStack().peek();
		}
		String compare = "#" + openMethod + "#";
		
		if((openMethod.equals(AstZEROORMORETreeNode.ZERO_OR_MORE) || openMethod.equals(AstONEORMORETreeNode.ONE_OR_MORE) ||
				openMethod.equals(AstOPTIONTreeNode.OPTION)) && peek.equals(compare)) {
			openProcessStore.getStack().pop();
			closeProcessStore.getStack().pop();
			bodyString = openMethod + "(" + bodyString + ")";
		}
		return bodyString;
	}
	
	protected String getMethodName(String expressionName) {
		String methodName = methodNameMap.get(expressionName);
		
		if(methodName == null) {
			if(expressionName.equals(expressionName.toUpperCase())) {
				methodName = expressionName;
			} else {
				methodName = expressionName.substring(0, 1).toLowerCase() + expressionName.substring(1);
			}
			methodName = ReservedJavaWords.getUnreservedWord(methodName);
			methodNameMap.put(expressionName, methodName);
		}
		return methodName;
	}
	
	protected boolean checkExistence(String methodName) {
		Boolean result = existenceMap.get(methodName);
		
		if(result == null) {
			result = false;
			for (Field constant : constants) {
				if(constant.getName().equals(methodName)) {
					result = true;
					break;
				}
			}
			existenceMap.put(methodName, result);
		}
		
		return result;
	}
}
