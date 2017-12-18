/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.util;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

import com.github.uscexp.grappa.extension.nodes.AstTreeNode;

/**
 * @author haui
 *
 */
public class AstTreeUtil {

	private static final String INTEND = "  ";

	private AstTreeUtil() {
	}

	public static void printAstTree(AstTreeNode<?> rootTreeNode, OutputStream outputStream) {
		PrintStream printStream = null;
		
		if(outputStream instanceof PrintStream) {
			printStream = (PrintStream)outputStream;
		} else {
			printStream = new PrintStream(outputStream);
		}

		printAstTree(rootTreeNode, printStream, 0);
	}

	private static void printAstTree(AstTreeNode<?> rootTreeNode, PrintStream printStream, int callDepth) {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < callDepth; i++) {
			stringBuilder.append(INTEND);
		}
		stringBuilder.append(rootTreeNode.toString());

		printStream.println(stringBuilder.toString());

		List<?> children = rootTreeNode.getChildren();

		for (Object child : children) {
			AstTreeNode<?> astTreeNode = (AstTreeNode<?>) child;

			printAstTree(astTreeNode, printStream, callDepth + 1);
		}
	}
}
