/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.interpreter;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;

import com.github.fge.grappa.Grappa;
import com.github.fge.grappa.exceptions.InvalidGrammarException;
import com.github.uscexp.grappa.extension.nodes.AstTreeNode;
import com.github.uscexp.grappa.extension.parser.Parser;
import com.github.uscexp.grappa.extension.testparser.CalculatorParser;
import com.github.uscexp.grappa.extension.testparser.subclass.ExtendedCalculatorParser;
import com.github.uscexp.grappa.extension.util.AstTreeUtil;

/**
 * @author haui
 *
 */
public class AstInterpreterTest {

	@Test
	public void testHappyCase() throws Exception {
		String input = "2 * 2 + 2 * 3";
		CalculatorParser calculatorParser = Grappa.createParser(CalculatorParser.class);
		
		AstTreeNode<Double> rootNode = Parser.parseInput(CalculatorParser.class, calculatorParser.inputLine(), input);
		
		AstInterpreter<Double> interpreter = new AstInterpreter<>();
		Long id = new Date().getTime();
		interpreter.interpretBackwardOrder(CalculatorParser.class, rootNode, id);
		Object result = ProcessStore.getInstance(id).getStack().peek();
		
		assertEquals(new Double(10), result);
		interpreter.cleanUp(id);
		System.out.println("-------------------");
	}

	@Test
	public void testHappyCaseRemoveAstNopTreeNodes() throws Exception {
		String input = "2 * 2 + 2 * 3";
		CalculatorParser calculatorParser = Grappa.createParser(CalculatorParser.class);
		
		AstTreeNode<Double> rootNode = Parser.parseInput(CalculatorParser.class, calculatorParser.inputLine(), input, true);
		
		AstInterpreter<Double> interpreter = new AstInterpreter<>();
		Long id = new Date().getTime();
		interpreter.interpretBackwardOrder(calculatorParser.getClass(), rootNode, id);
		Object result = ProcessStore.getInstance(id).getStack().peek();
		
		assertEquals(new Double(10), result);
		
		AstTreeUtil.printAstTree(rootNode, System.out);
		interpreter.cleanUp(id);
		System.out.println("-------------------");
	}

	@Test
	public void testSqrt() throws Exception {
		String input = "SQRT(4)";
		CalculatorParser calculatorParser = Grappa.createParser(CalculatorParser.class);
		
		AstTreeNode<Double> rootNode = Parser.parseInput(CalculatorParser.class, calculatorParser.inputLine(), input);
		
		AstInterpreter<Double> interpreter = new AstInterpreter<>();
		Long id = new Date().getTime();
		interpreter.interpretBackwardOrder(calculatorParser.getClass(), rootNode, id);
		Object result = ProcessStore.getInstance(id).getStack().peek();
		
		assertEquals(new Double(2), result);
		interpreter.cleanUp(id);
		System.out.println("-------------------");
	}

	@Test(expected = InvalidGrammarException.class)
	public void testWrongSyntax() throws Exception {
		String input = ")2(2+2*3";
		CalculatorParser calculatorParser = Grappa.createParser(CalculatorParser.class);
		
		AstTreeNode<Double> rootNode = Parser.parseInput(CalculatorParser.class, calculatorParser.inputLine(), input);
		System.out.println("-------------------");
		
		AstInterpreter<Double> interpreter = new AstInterpreter<>();
		Long id = new Date().getTime();
		interpreter.interpretBackwardOrder(calculatorParser.getClass(), rootNode, id);
		interpreter.cleanUp(id);
	}

	@Test
	public void testExtendedParserHappyCase() throws Exception {
		String input = "2 * 2 + 2 * 3";
		ExtendedCalculatorParser calculatorParser = Grappa.createParser(ExtendedCalculatorParser.class);
		
		AstTreeNode<Double> rootNode = Parser.parseInput(CalculatorParser.class, calculatorParser.inputLine(), input);
		
		AstInterpreter<Double> interpreter = new AstInterpreter<>();
		Long id = new Date().getTime();
		interpreter.interpretBackwardOrder(ExtendedCalculatorParser.class, rootNode, id);
		Object result = ProcessStore.getInstance(id).getStack().peek();
		
		assertEquals(new Double(10), result);
		interpreter.cleanUp(id);
		System.out.println("-------------------");
	}
}
