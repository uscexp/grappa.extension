/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.exception;

/**
 * @author haui
 *
 */
public class AstInterpreterException extends Exception {

	private static final long serialVersionUID = -330014265655391953L;

	public AstInterpreterException(String message, Throwable cause) {
		super(message, cause);
	}

	public AstInterpreterException(String message) {
		super(message);
	}

}
