/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.parser.peg;

import org.parboiled.BaseParser;
import org.parboiled.Rule;
import org.parboiled.annotations.BuildParseTree;

import com.github.uscexp.grappa.extension.annotations.AstCommand;

/**
 * @author  haui
 */
@BuildParseTree
public class PegParser extends BaseParser<String> {

	@AstCommand
	public Rule grammar() {
		return sequence(S(), oneOrMore(definition()), EOI);
	}

	@AstCommand
	public Rule definition() {
		return sequence(name(), arrow(), expression(), S());
	}

	public Rule expression() {
		return sequence(sequence(), zerooOrMore());
	}

	@AstCommand
	public Rule sequence() {
		return oneOrMore(prefix());
	}

	@AstCommand
	public Rule zerooOrMore() {
		return zeroOrMore(sequence(OR(), sequence()));
	}

	public Rule prefix() {
		return sequence(optional(firstOf(LOOKAHEAD(), NOT())), suffix());
	}

	public Rule suffix() {
		return sequence(primary(), optional(firstOf(OPTION(), ONEORMORE(), ZEROORMORE())), S());
	}

	public Rule primary() {
		return firstOf(sequence(name(), testNot(arrow())), groupExpr(), literal(), classs(), ANYY());
	}

	@AstCommand
	public Rule name() {
		return sequence(identifier(), S());
	}

	public Rule identifier() {
		return sequence(identStart(), zeroOrMore(identCont()));
	}

	public Rule identCont() {
		return firstOf(identStart(), charRange('0', '9'));
	}

	public Rule identStart() {
		return firstOf(alpha(), ch('_'));
	}

	public Rule groupExpr() {
		return sequence(OPEN(), expression(), CLOSE(), S());
	}

	@AstCommand
	public Rule literal() {
		return firstOf(sequence(quote(), zeroOrMore(sequence(testNot(quote()), character())), quote(), S()),
				sequence(doubleQuote(), zeroOrMore(sequence(testNot(doubleQuote()), character())), doubleQuote(), S()));
	}

	@AstCommand
	public Rule classs() {
		return sequence(SQUAREOPEN(), zeroOrMore(sequence(testNot(SQUARECLOSE()), charRange())), SQUARECLOSE(), S());
	}

	@AstCommand
	public Rule charRange() {
		return firstOf(sequence(character(), ch('-'), character()), character());
	}

	@AstCommand
	public Rule character() {
		return
			firstOf(sequence(backSlash(),
					firstOf(quote(), doubleQuote(), backQuote(), backSlash(), anyOf("nrt"),
						sequence(charRange('0', '2'), charRange('0', '7'), charRange('0', '7')),
						sequence(charRange('0', '7'), optional(charRange('0', '7'))))), sequence(testNot(backSlash()), ANY));
	}

	public Rule backSlash() {
		return ch('\\');
	}

	public Rule quote() {
		return ch('\'');
	}

	public Rule doubleQuote() {
		return ch('\"');
	}

	public Rule backQuote() {
		return ch('`');
	}

	// Terminals

	public Rule arrow() {
		return sequence(string("<-"), S());
	}

	@AstCommand
	public Rule OR() {
		return sequence(ch('/'), S());
	}

	@AstCommand
	public Rule LOOKAHEAD() {
		return sequence(ch('&'), S());
	}

	@AstCommand
	public Rule NOT() {
		return sequence(ch('!'), S());
	}

	@AstCommand
	public Rule OPTION() {
		return sequence(ch('?'), S());
	}

	@AstCommand
	public Rule ZEROORMORE() {
		return sequence(ch('*'), S());
	}

	@AstCommand
	public Rule ONEORMORE() {
		return sequence(ch('+'), S());
	}

	@AstCommand
	public Rule OPEN() {
		return sequence(ch('('), S());
	}

	@AstCommand
	public Rule CLOSE() {
		return sequence(ch(')'), S());
	}

	@AstCommand
	public Rule SQUAREOPEN() {
		return ch('[');
	}

	@AstCommand
	public Rule SQUARECLOSE() {
		return ch(']');
	}

	@AstCommand
	public Rule ANYY() {
		return sequence(ch('.'), S());
	}

	// blanks

	public Rule EOL() {
		return firstOf(string("\r\n"), ch('\n'), ch('\r'));
	}

	public Rule comment() {
		return sequence(string("#"), zeroOrMore(sequence(testNot(EOL()), ANY)), firstOf(EOL(), EOI));
	}

	public Rule S() {
		return zeroOrMore(firstOf(ch(' '), ch('\t'), EOL(), comment()));
	}
}
