/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * annotation for parser rules. announce that the interpreter should
 * store the parse value to the stack.
 * 
 * @author haui
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AstValue {
	/**
	 * type of the value.
	 */
	public Class<?> valueType() default Object.class;
	/**
	 * factory class which holds the factory method.
	 */
	public Class<?> factoryClass() default Object.class;
	/**
	 * factory method name to create the type value from a string.
	 */
	public String factoryMethod() default "";
}
