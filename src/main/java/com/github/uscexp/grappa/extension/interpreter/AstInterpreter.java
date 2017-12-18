/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.interpreter;

import java.io.OutputStream;

import com.github.fge.grappa.parsers.BaseParser;
import com.github.uscexp.grappa.extension.annotations.AstCommand;
import com.github.uscexp.grappa.extension.annotations.AstValue;
import com.github.uscexp.grappa.extension.exception.AstInterpreterException;
import com.github.uscexp.grappa.extension.nodes.AstTreeNode;
import com.github.uscexp.grappa.extension.util.AstTreeUtil;

/**
 * The {@link AstInterpreter} interpretes a PEG defined language, based on {@link BaseParser}. It takes an AST (Abstract Syntax Tree)
 * based on the parsing and the annotated parser methods, annotated with {@link AstValue} or {@link AstCommand}. {@link
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

	private AstTreeNode<V> root;
	private OutputStream astTreeOutputStream = null;
	

	public AstInterpreter() {
		this(null);
	}

	public AstInterpreter(OutputStream astTreeOutputStream) {
		super();
		this.astTreeOutputStream = astTreeOutputStream;
	}

	public void interpretBackwardOrder(Class<? extends BaseParser<V>> parserClass, AstTreeNode<V> node, Long id)
		throws AstInterpreterException {
		interpret(parserClass, node, id, false);
	}

	public void interpretForewardOrder(Class<? extends BaseParser<V>> parserClass, AstTreeNode<V> node, Long id)
		throws AstInterpreterException {
		interpret(parserClass, node, id, true);
	}

	private void interpret(Class<? extends BaseParser<V>> parserClass, AstTreeNode<V> node, Long id, boolean forewardOrder)
		throws AstInterpreterException {
		try {
			ProcessStore.getInstance(id);

			root = node;
			
			if(root == null) {
				throw new AstInterpreterException(String.format("Can't build AstTreeNodes for parser class %s",
						parserClass.getName()));
			}
			
			if(astTreeOutputStream != null) {
				AstTreeUtil.printAstTree(root, astTreeOutputStream);
			}

			root.interpretIt(id, forewardOrder);
		} catch (Exception e) {
			throw new AstInterpreterException(String.format("Can't build/interpret AstTreeNodes for parser class %s",
					parserClass.getName()), e);
		}
	}

	public void cleanUp(Long id) {
		ProcessStore.removeInstance(id);
	}

}
