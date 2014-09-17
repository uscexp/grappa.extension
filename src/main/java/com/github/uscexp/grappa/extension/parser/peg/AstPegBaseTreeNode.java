/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.parser.peg;

import org.parboiled.Node;
import org.parboiled.trees.TreeNode;

import com.github.uscexp.grappa.extension.codegenerator.PegParserGenerator;
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

	public AstPegBaseTreeNode(Node<?> node, String value) {
		super(node, value);
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
}
