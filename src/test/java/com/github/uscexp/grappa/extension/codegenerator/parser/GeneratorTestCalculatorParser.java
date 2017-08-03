/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.codegenerator.parser;

import com.github.fge.grappa.parsers.BaseParser;
import com.github.fge.grappa.rules.Rule;
import com.github.uscexp.grappa.extension.annotations.AstCommand;
import com.github.uscexp.grappa.extension.annotations.AstValue;

/**
 * @author haui
 *
 */
public class GeneratorTestCalculatorParser extends BaseParser<Double> {

	@AstCommand(classname = "com.github.uscexp.grappa.extension.codegenerator.parser.AstResultTreeNode")
	public Rule inputLine() {
		return sequence(expression(), EOI);
	}

	@AstCommand
	protected Rule expression() {
		return sequence(term(), zeroOrMore(firstOf("+ ", "- "), term()));
	}

	@AstCommand
	protected Rule term() {
		return sequence(factor(), zeroOrMore(firstOf("* ", "/ "), factor()));
	}

	@AstCommand
	protected Rule factor() {
		return sequence(atom(), zeroOrMore(firstOf("^ ", atom())));
	}

	@AstValue(valueType = String.class, factoryClass = String.class) // only for testing, not necessary
	protected Rule atom() {
		return firstOf(number(), squareRoot(), parens());
	}

	@AstValue(valueType = String.class) // only for testing, not necessary
	protected Rule parens() {
		return sequence('(', expression(), ')');
	}

	@AstCommand
	protected Rule squareRoot() {
		return sequence("SQRT ", parens());
	}

	@AstValue(valueType = Double.class, factoryClass = Double.class, factoryMethod = "valueOf")
	protected Rule number() {
		return sequence(

				// we use another Sequence in the "Number" Sequence so we can easily access the input text matched
				// by the three enclosed rules with "match()" or "matchOrDefault()"
				sequence(optional('-'), oneOrMore(digit()), optional('.', oneOrMore(digit()))), whiteSpace());
	}

	protected Rule whiteSpace() {
		return zeroOrMore(anyOf(" \t\f"));
	}

	// we redefine the rule creation for string literals to automatically match trailing whitespace if the string
	// literal ends with a space character, this way we don't have to insert extra whitespace() rules after each
	// character or string literal
	@Override
	protected Rule fromStringLiteral(String string) {
		return string.endsWith(" ") ? sequence(string(string.substring(0, string.length() - 1)), whiteSpace()) : string(string);
	}
}
