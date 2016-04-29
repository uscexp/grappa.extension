/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.parser.peg;

import java.util.Stack;
import java.util.StringTokenizer;
import java.util.logging.Logger;

import com.github.fge.grappa.rules.Rule;
import com.sun.codemodel.JBlock;
import com.sun.codemodel.JMethod;

/**
 * Command implementation for the <code>PegParser</code> rule: definition.
 */
public class AstDefinitionTreeNode<V> extends AstPegBaseTreeNode<V> {
	private static Logger logger = Logger.getLogger(AstDefinitionTreeNode.class.getName());

	public AstDefinitionTreeNode(String rule, String value) {
		super(rule, value);
	}

	@Override
	protected void interpretBeforeChilds(Long id) throws ReflectiveOperationException {
		super.interpretBeforeChilds(id);
		this.processStore.tierOneUp(true);
		this.openProcessStore.tierOneUp(true);
	}

	@Override
	protected void interpretAfterChilds(Long id) throws ReflectiveOperationException {
		super.interpretAfterChilds(id);
		Stack<Object> stack = this.processStore.getTierStack();
		Stack<Object> openStack = this.openProcessStore.getTierStack();
		if ((this.value != null) && (!this.value.isEmpty())) {
			StringTokenizer st = new StringTokenizer(this.value, " \t\n\r", false);
			if (st.hasMoreTokens()) {
				String methodName = getMethodName(st.nextToken());
				if (!checkExistence(methodName)) {
					JMethod method = this.definedClass.method(1, Rule.class, methodName);
					JBlock body = method.body();
					String rule = "";
					int open = 0;
					while (!openStack.isEmpty()) {
						String function = (String) openStack.pop();
						if (!function.isEmpty()) {
							rule = rule + function + "(";
							open++;
						}
					}
					if (!stack.isEmpty()) {
						String currentRule = (String) stack.pop();
						if (((currentRule + "(").startsWith(methodName)) && (!stack.isEmpty())) {
							currentRule = (String) stack.pop();
						}
						rule = rule + currentRule;
					}
					while (!stack.isEmpty()) {
						String value = (String) stack.pop();
						if (!rule.endsWith("(")) {
							rule = rule + ", ";
						}
						rule = rule + value;
					}
					for (int i = 0; i < open; i++) {
						rule = rule + ")";
					}
					String bodySource = "return " + rule + ";";
					body.directStatement(bodySource);

					logger.info(String.format("add method: %s, with body: %s", new Object[] { methodName, bodySource }));
				}
			}
		}
		openStack.clear();
		stack.clear();
		this.processStore.tierOneDown(true);
		this.openProcessStore.tierOneDown(true);
	}
}
