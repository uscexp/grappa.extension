/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.parser.subclass;

import org.parboiled.Rule;

import com.github.uscexp.grappa.extension.parser.CalculatorParser;

/**
 * @author haui
 *
 */
public class ExtendedCalculatorParser extends CalculatorParser {

	public Rule extendeInputLine() {
		return sequence(inputLine(), true);
	}
}
