/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.testparser.subclass;

import org.parboiled.Rule;

import com.github.uscexp.grappa.extension.testparser.CalculatorParser;

/**
 * @author haui
 *
 */
public class ExtendedCalculatorParser extends CalculatorParser {

	public Rule extendeInputLine() {
		return sequence(inputLine(), true);
	}
}
