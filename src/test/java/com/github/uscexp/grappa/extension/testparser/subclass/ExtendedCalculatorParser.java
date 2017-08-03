/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.testparser.subclass;

import com.github.fge.grappa.rules.Rule;
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
