/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.interpreter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;
import org.parboiled.Node;
import org.parboiled.Parboiled;
import org.parboiled.errors.ErrorUtils;
import org.parboiled.parserunners.RecoveringParseRunner;
import org.parboiled.support.ParseTreeUtils;
import org.parboiled.support.ParsingResult;

import com.github.uscexp.grappa.extension.exception.AstInterpreterException;
import com.github.uscexp.grappa.extension.parser.CalculatorParser;
import com.github.uscexp.grappa.extension.parser.subclass.ExtendedCalculatorParser;

/**
 * @author haui
 *
 */
public class AstInterpreterTest {

	@Test
	public void testHappyCase() throws Exception {
		String input = "2 * 2 + 2 * 3";
		CalculatorParser calculatorParser = Parboiled.createParser(CalculatorParser.class);
		
		RecoveringParseRunner<CalculatorParser> recoveringParseRunner = new RecoveringParseRunner<>(calculatorParser.inputLine());
		
		ParsingResult<CalculatorParser> parsingResult = recoveringParseRunner.run(input);
		
		assertFalse(parsingResult.hasErrors());
		
		Node<CalculatorParser> root = parsingResult.parseTreeRoot;
		
		System.out.println("Root node text: " + ParseTreeUtils.getNodeText(root, parsingResult.inputBuffer));
		
		AstInterpreter<Double> interpreter = new AstInterpreter<>();
		Long id = new Date().getTime();
		interpreter.execute(calculatorParser.getClass(), parsingResult, id);
		Object result = ProcessStore.getInstance(id).getStack().peek();
		
		assertEquals(new Double(10), result);
		interpreter.cleanUp(id);
		System.out.println("-------------------");
	}

	@Test
	public void testSqrt() throws Exception {
		String input = "SQRT(4)";
		CalculatorParser calculatorParser = Parboiled.createParser(CalculatorParser.class);
		
		RecoveringParseRunner<CalculatorParser> recoveringParseRunner = new RecoveringParseRunner<>(calculatorParser.inputLine());
		
		ParsingResult<CalculatorParser> parsingResult = recoveringParseRunner.run(input);
		
		assertFalse(parsingResult.hasErrors());
		
		Node<CalculatorParser> root = parsingResult.parseTreeRoot;
		
		System.out.println("Root node text: " + ParseTreeUtils.getNodeText(root, parsingResult.inputBuffer));
		
		AstInterpreter<Double> interpreter = new AstInterpreter<>();
		Long id = new Date().getTime();
		interpreter.execute(calculatorParser.getClass(), parsingResult, id);
		Object result = ProcessStore.getInstance(id).getStack().peek();
		
		assertEquals(new Double(2), result);
		interpreter.cleanUp(id);
		System.out.println("-------------------");
	}

	@Test(expected = AstInterpreterException.class)
	public void testWrongSyntax() throws Exception {
		String input = ")2(2+2*3";
		CalculatorParser calculatorParser = Parboiled.createParser(CalculatorParser.class);
		
		RecoveringParseRunner<CalculatorParser> recoveringParseRunner = new RecoveringParseRunner<>(calculatorParser.inputLine());
		
		ParsingResult<CalculatorParser> parsingResult = recoveringParseRunner.run(input);
		
		assertTrue(parsingResult.hasErrors());
		if(parsingResult.hasErrors()) {
			String string = String.format("Calculator parse error(s): %s", ErrorUtils.printParseErrors(parsingResult));
			System.out.println(string);
		}
		
		Node<CalculatorParser> root = parsingResult.parseTreeRoot;
		
		System.out.println("Root node text: " + ParseTreeUtils.getNodeText(root, parsingResult.inputBuffer));
		System.out.println("-------------------");
		
		AstInterpreter<Double> interpreter = new AstInterpreter<>();
		Long id = new Date().getTime();
		interpreter.execute(calculatorParser.getClass(), parsingResult, id);
		interpreter.cleanUp(id);
	}

	@Test
	public void testExtendedParserHappyCase() throws Exception {
		String input = "2 * 2 + 2 * 3";
		ExtendedCalculatorParser calculatorParser = Parboiled.createParser(ExtendedCalculatorParser.class);
		
		RecoveringParseRunner<ExtendedCalculatorParser> recoveringParseRunner = new RecoveringParseRunner<>(calculatorParser.extendeInputLine());
		
		ParsingResult<ExtendedCalculatorParser> parsingResult = recoveringParseRunner.run(input);
		
		assertFalse(parsingResult.hasErrors());
		
		Node<ExtendedCalculatorParser> root = parsingResult.parseTreeRoot;
		
		System.out.println("Root node text: " + ParseTreeUtils.getNodeText(root, parsingResult.inputBuffer));
		
		AstInterpreter<Double> interpreter = new AstInterpreter<>();
		Long id = new Date().getTime();
		interpreter.execute(calculatorParser.getClass(), parsingResult, id);
		Object result = ProcessStore.getInstance(id).getStack().peek();
		
		assertEquals(new Double(10), result);
		interpreter.cleanUp(id);
		System.out.println("-------------------");
	}
}
