/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * annotation for parser rules. announce that the interpreter should store the
 * parse value to the stack.
 * 
 * @author haui
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AstValue {

	public Class<?> valueType() default Object.class;

	public Class<?> factoryClass() default Object.class;

	public String factoryMethod() default "";
}
