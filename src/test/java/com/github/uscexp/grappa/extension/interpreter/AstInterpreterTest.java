/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.interpreter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.Test;

import com.github.fge.grappa.Grappa;
import com.github.fge.grappa.parsers.BaseParser;
import com.github.fge.grappa.run.ListeningParseRunner;
import com.github.fge.grappa.run.ParsingResult;
import com.github.uscexp.grappa.extension.exception.AstInterpreterException;
import com.github.uscexp.grappa.extension.nodes.AstTreeNode;
import com.github.uscexp.grappa.extension.nodes.treeconstruction.AstTreeNodeBuilder;
import com.github.uscexp.grappa.extension.parser.AstTreeParserResult;
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
		
		AstTreeParserResult<Double> astTreeParserResult = executeParser(CalculatorParser.class, calculatorParser, input, false);

		AstInterpreter<Double> interpreter = new AstInterpreter<>();
		Long id = new Date().getTime();
		interpreter.interpretBackwardOrder(CalculatorParser.class, astTreeParserResult, id);
		Object result = ProcessStore.getInstance(id).getStack().peek();
		
		assertEquals(new Double(10), result);
		interpreter.cleanUp(id);
		System.out.println("-------------------");
	}

	@Test
	public void testHappyCaseRemoveAstNopTreeNodes() throws Exception {
		String input = "2 * 2 + 2 * 3";
		CalculatorParser calculatorParser = Grappa.createParser(CalculatorParser.class);
		
		AstTreeParserResult<Double> astTreeParserResult = executeParser(CalculatorParser.class, calculatorParser, input, true);

		AstInterpreter<Double> interpreter = new AstInterpreter<>();
		Long id = new Date().getTime();
		interpreter.interpretBackwardOrder(calculatorParser.getClass(), astTreeParserResult, id);
		Object result = ProcessStore.getInstance(id).getStack().peek();
		
		assertEquals(new Double(10), result);
		
		AstTreeUtil.printAstTree(astTreeParserResult.getRootNode(), System.out);
		interpreter.cleanUp(id);
		System.out.println("-------------------");
	}

	@Test
	public void testSqrt() throws Exception {
		String input = "SQRT(4)";
		CalculatorParser calculatorParser = Grappa.createParser(CalculatorParser.class);
		
		AstTreeParserResult<Double> astTreeParserResult = executeParser(CalculatorParser.class, calculatorParser, input, false);
		
		AstInterpreter<Double> interpreter = new AstInterpreter<>();
		Long id = new Date().getTime();
		interpreter.interpretBackwardOrder(calculatorParser.getClass(), astTreeParserResult, id);
		Object result = ProcessStore.getInstance(id).getStack().peek();
		
		assertEquals(new Double(2), result);
		interpreter.cleanUp(id);
		System.out.println("-------------------");
	}

	@Test(expected = AstInterpreterException.class)
	public void testWrongSyntax() throws Exception {
		String input = ")2(2+2*3";
		CalculatorParser calculatorParser = Grappa.createParser(CalculatorParser.class);
		
		AstTreeParserResult<Double> astTreeParserResult = executeParser(CalculatorParser.class, calculatorParser, input, false);
		System.out.println("-------------------");
		
		AstInterpreter<Double> interpreter = new AstInterpreter<>();
		Long id = new Date().getTime();
		interpreter.interpretBackwardOrder(calculatorParser.getClass(), astTreeParserResult, id);
		interpreter.cleanUp(id);
	}

	@Test
	public void testExtendedParserHappyCase() throws Exception {
		String input = "2 * 2 + 2 * 3";
		ExtendedCalculatorParser calculatorParser = Grappa.createParser(ExtendedCalculatorParser.class);
		
		AstTreeParserResult<Double> astTreeParserResult = executeParser(ExtendedCalculatorParser.class, calculatorParser, input, false);
		
		AstInterpreter<Double> interpreter = new AstInterpreter<>();
		Long id = new Date().getTime();
		interpreter.interpretBackwardOrder(ExtendedCalculatorParser.class, astTreeParserResult, id);
		Object result = ProcessStore.getInstance(id).getStack().peek();
		
		assertEquals(new Double(10), result);
		interpreter.cleanUp(id);
		System.out.println("-------------------");
	}

	private AstTreeParserResult<Double> executeParser(Class<? extends BaseParser<?>> parserClass, CalculatorParser calculatorParser, String input, boolean removeAstNopTreeNodes) {
		final AstTreeNodeBuilder<Double> treeNodeBuilder = new AstTreeNodeBuilder<Double>(parserClass, removeAstNopTreeNodes);
		ListeningParseRunner<Double> parseRunner = new ListeningParseRunner<>(calculatorParser.inputLine());
		
		parseRunner.registerListener(treeNodeBuilder);

		ParsingResult<Double> parsingResult = parseRunner.run(input);
		
		assertNotNull(parsingResult);
		
		AstTreeNode<Double> root = treeNodeBuilder.getRootNode();
		
		if(root != null) {
			System.out.println("Root node text: " + root.getValue());
		} else {
			System.err.println(treeNodeBuilder.getParsingErrors());
		}
		
		AstTreeParserResult<Double> astTreeParserResult = new AstTreeParserResult<>(parsingResult, treeNodeBuilder.getRootNode());
		return astTreeParserResult;
	}
}
