/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.codegenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class which contains all java language reserved words.
 * 
 * @author haui
 *
 */
public class ReservedJavaWords {

	public static List<String> reservedWords;

	static {
		reservedWords = new ArrayList<>();
		reservedWords.add("abstract");
		reservedWords.add("assert");
		reservedWords.add("boolean");
		reservedWords.add("break");
		reservedWords.add("byte");
		reservedWords.add("case");
		reservedWords.add("catch");
		reservedWords.add("char");
		reservedWords.add("class");
		reservedWords.add("const");
		reservedWords.add("continue");
		reservedWords.add("default");
		reservedWords.add("do");
		reservedWords.add("double");
		reservedWords.add("else");
		reservedWords.add("enum");
		reservedWords.add("extends");
		reservedWords.add("final");
		reservedWords.add("finally");
		reservedWords.add("float");
		reservedWords.add("for");
		reservedWords.add("goto");
		reservedWords.add("if");
		reservedWords.add("implements");
		reservedWords.add("import");
		reservedWords.add("instanceof");
		reservedWords.add("int");
		reservedWords.add("interface");
		reservedWords.add("long");
		reservedWords.add("native");
		reservedWords.add("new");
		reservedWords.add("package");
		reservedWords.add("private");
		reservedWords.add("protected");
		reservedWords.add("public");
		reservedWords.add("return");
		reservedWords.add("short");
		reservedWords.add("static");
		reservedWords.add("strictfp");
		reservedWords.add("super");
		reservedWords.add("switch");
		reservedWords.add("synchronized");
		reservedWords.add("this");
		reservedWords.add("throw");
		reservedWords.add("throws");
		reservedWords.add("transient");
		reservedWords.add("try");
		reservedWords.add("void");
		reservedWords.add("volatile");
		reservedWords.add("while");
		
		reservedWords = Collections.unmodifiableList(reservedWords);
	}
	
	private ReservedJavaWords() {
		super();
	}
	
	public static boolean isReservedWord(String word) {
		return reservedWords.contains(word);
	}
	
	public static String getUnreservedWord(String word) {
		String result = word;
		
		while (isReservedWord(result)) {
			result += result.charAt(result.length()-1);
		}
		
		return result;
	}
}
