/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.parser.peg;

import java.util.StringTokenizer;
import java.util.logging.Logger;

import org.parboiled.Node;
import org.parboiled.Rule;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;

/**
 * Command implementation for the <code>PegParser</code> rule: definition.
 */
public class AstDefinitionTreeNode<V> extends AstPegBaseTreeNode<V> {

	private static Logger logger = Logger.getLogger(AstDefinitionTreeNode.class.getName());

	public AstDefinitionTreeNode(Node<?> node, String value) {
		super(node, value);
	}

	@Override
	protected void interpret(Long id)
		throws ReflectiveOperationException {
		super.interpret(id);

		if ((value != null) && !value.isEmpty()) {
			StringTokenizer st = new StringTokenizer(value, " \t\n\r", false);
			if (st.hasMoreTokens()) {
				String methodName = getMethodName(st.nextToken());
				
				if(checkExistence(methodName)) {
					openProcessStore.getStack().clear();
					closeProcessStore.getStack().clear();
				} else {

					JMethod method = definedClass.method(JMod.PUBLIC, Rule.class, methodName);
					JBlock body = method.body();
					String rule = "";
					int open = 0;
					while (!openProcessStore.getStack().isEmpty()) {
						rule += (String) openProcessStore.getStack().pop() + "(";
						++open;
					}
					
					if(!closeProcessStore.getStack().isEmpty()) {
						String currentRule = (String) closeProcessStore.getStack().pop();
						if(!AstSequenceTreeNode.isStartSequence(currentRule) && !(currentRule.startsWith("#") && currentRule.endsWith("#"))) {
							if ((currentRule + "(").startsWith(methodName)) {
								currentRule = (String) closeProcessStore.getStack().pop();
								rule += currentRule;
							}
						}
					}
					
					while (!closeProcessStore.getStack().isEmpty()) {
						String value = (String) closeProcessStore.getStack().pop();
						if(AstSequenceTreeNode.isStartSequence(value)) {
							break;
						}
						if(!rule.endsWith("(")) {
							rule += ", ";
						}
						rule += value;
					}
					
					for (int i = 0; i < open; i++) {
						rule += ")";
					}
					
					String bodySource = "return " + rule + ";";
					body.directStatement(bodySource);
	
					logger.info(String.format("add method: %s, with body: %s", methodName, bodySource));
				}
			}
		}
	}

}
