/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.parser.peg;

import java.util.ArrayList;
import java.util.List;

import org.parboiled.Node;

/**
 * Command implementation for the <code>PegParser</code> rule: sequence.
 */
public class AstSequenceTreeNode<V> extends AstPegBaseTreeNode<V> {

	public static final String START_SEQUENCE = "#startSequence#";

	public AstSequenceTreeNode(Node<?> node, String value) {
		super(node, value);
	}

	@Override
	protected void interpret(Long id)
		throws ReflectiveOperationException {
		super.interpret(id);
		String peek = "";
		if (!openProcessStore.getStack().isEmpty()) {
			peek = (String) openProcessStore.getStack().peek();
		}
//		if (!peek.equals("firstOf")) {
			String compare = "#" + peek + "#";
			String methodBody = "";
			String bracket = null;
			List<String> values = new ArrayList<>();

			while (!closeProcessStore.getStack().isEmpty()) {
				String value = (String) closeProcessStore.getStack().pop();
				if (value.equals("(") || value.equals(")")) {
					bracket = value;
					break;
				}
				if (isStartSequence(value)) {
					break;
				}
				if (value.equals(compare)) {
					break;
				}
				if(value.startsWith("#") && value.endsWith("#")) {
					continue;
				}
				values.add(value);
			}
			if (values.size() == 1) {
				if ((bracket == null) &&
						(peek.equals(AstZEROORMORETreeNode.ZERO_OR_MORE) || peek.equals(AstONEORMORETreeNode.ONE_OR_MORE) ||
							peek.equals(AstOPTIONTreeNode.OPTION))) {
					methodBody = (String) openProcessStore.getStack().pop() + "(";
					methodBody += values.get(0) + ")";
				} else {
					methodBody = values.get(0);
				}
			} else {
				methodBody = "sequence(";
				for (String value : values) {
					if (!methodBody.endsWith("(")) {
						methodBody += ", ";
					}
					methodBody += value;
				}
				methodBody += ")";
			}
			if (bracket != null)
				closeProcessStore.getStack().push(bracket);
			closeProcessStore.getStack().push(methodBody);
//		}
	}

	public static boolean isStartSequence(String value) {
		return value.equals(START_SEQUENCE);
	}

}
