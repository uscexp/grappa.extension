/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.interpreter.type;

/**
 * @author haui
 *
 */
public enum Operator {
	BITWISE_XOR("^") {
		@Override
		public Object apply(boolean x1, boolean x2) {
			return x1 ^ x2;
		}

		@Override
		public Object apply(char x1, char x2) {
			return x1 ^ x2;
		}

		@Override
		public Object apply(byte x1, byte x2) {
			return x1 ^ x2;
		}

		@Override
		public Object apply(short x1, short x2) {
			return x1 ^ x2;
		}

		@Override
		public Object apply(int x1, int x2) {
			return x1 ^ x2;
		}

		@Override
		public Object apply(long x1, long x2) {
			return x1 ^ x2;
		}

		@Override
		public Object apply(float x1, float x2) {
			return ((long)x1 ^ (long)x2);
		}
		
		@Override
		public Object apply(double x1, double x2) {
			return ((long)x1 ^ (long)x2);
		}

		@Override
		public String apply(String x1, String x2) {
			return (String)handleError(this, x1.getClass().getSimpleName());
		}
	},
	BITWISE_OR("|") {
		@Override
		public Object apply(boolean x1, boolean x2) {
			return x1 | x2;
		}

		@Override
		public Object apply(char x1, char x2) {
			return x1 | x2;
		}

		@Override
		public Object apply(byte x1, byte x2) {
			return x1 | x2;
		}

		@Override
		public Object apply(short x1, short x2) {
			return x1 | x2;
		}

		@Override
		public Object apply(int x1, int x2) {
			return x1 | x2;
		}

		@Override
		public Object apply(long x1, long x2) {
			return x1 | x2;
		}

		@Override
		public Object apply(float x1, float x2) {
			return ((long)x1 | (long)x2);
		}
		
		@Override
		public Object apply(double x1, double x2) {
			return ((long)x1 | (long)x2);
		}

		@Override
		public String apply(String x1, String x2) {
			return (String)handleError(this, x1.getClass().getSimpleName());
		}
	},
	BITWISE_AND("&") {
		@Override
		public Object apply(boolean x1, boolean x2) {
			return x1 & x2;
		}

		@Override
		public Object apply(char x1, char x2) {
			return x1 & x2;
		}

		@Override
		public Object apply(byte x1, byte x2) {
			return x1 & x2;
		}

		@Override
		public Object apply(short x1, short x2) {
			return x1 & x2;
		}

		@Override
		public Object apply(int x1, int x2) {
			return x1 & x2;
		}

		@Override
		public Object apply(long x1, long x2) {
			return x1 & x2;
		}

		@Override
		public Object apply(float x1, float x2) {
			return ((long)x1 & (long)x2);
		}
		
		@Override
		public Object apply(double x1, double x2) {
			return ((long)x1 & (long)x2);
		}

		@Override
		public String apply(String x1, String x2) {
			return (String)handleError(this, x1.getClass().getSimpleName());
		}
	},
	MODULO("%") {
		@Override
		public Object apply(boolean x1, boolean x2) {
			return handleError(this, boolean.class.getSimpleName());
		}

		@Override
		public Object apply(char x1, char x2) {
			return x1 % x2;
		}

		@Override
		public Object apply(byte x1, byte x2) {
			return x1 % x2;
		}

		@Override
		public Object apply(short x1, short x2) {
			return x1 % x2;
		}

		@Override
		public Object apply(int x1, int x2) {
			return x1 % x2;
		}

		@Override
		public Object apply(long x1, long x2) {
			return x1 % x2;
		}

		@Override
		public Object apply(float x1, float x2) {
			return x1 % x2;
		}
		
		@Override
		public Object apply(double x1, double x2) {
			return x1 % x2;
		}

		@Override
		public String apply(String x1, String x2) {
			return (String)handleError(this, x1.getClass().getSimpleName());
		}
	},
	MULTIPLICATION("*") {
		@Override
		public Object apply(boolean x1, boolean x2) {
			return handleError(this, boolean.class.getSimpleName());
		}

		@Override
		public Object apply(char x1, char x2) {
			return x1 * x2;
		}

		@Override
		public Object apply(byte x1, byte x2) {
			return x1 * x2;
		}

		@Override
		public Object apply(short x1, short x2) {
			return x1 * x2;
		}

		@Override
		public Object apply(int x1, int x2) {
			return x1 * x2;
		}

		@Override
		public Object apply(long x1, long x2) {
			return x1 * x2;
		}

		@Override
		public Object apply(float x1, float x2) {
			return x1 * x2;
		}
		
		@Override
		public Object apply(double x1, double x2) {
			return x1 * x2;
		}

		@Override
		public String apply(String x1, String x2) {
			return (String)handleError(this, x1.getClass().getSimpleName());
		}
	},
	DIVISION("/") {
		@Override
		public Object apply(boolean x1, boolean x2) {
			return handleError(this, boolean.class.getSimpleName());
		}

		@Override
		public Object apply(char x1, char x2) {
			return x1 / x2;
		}

		@Override
		public Object apply(byte x1, byte x2) {
			return x1 / x2;
		}

		@Override
		public Object apply(short x1, short x2) {
			return x1 / x2;
		}

		@Override
		public Object apply(int x1, int x2) {
			return x1 / x2;
		}

		@Override
		public Object apply(long x1, long x2) {
			return x1 / x2;
		}

		@Override
		public Object apply(float x1, float x2) {
			return x1 / x2;
		}
		
		@Override
		public Object apply(double x1, double x2) {
			return x1 / x2;
		}

		@Override
		public String apply(String x1, String x2) {
			return (String)handleError(this, x1.getClass().getSimpleName());
		}
	},
	ADDITION("+") {
		@Override
		public Object apply(boolean x1, boolean x2) {
			return handleError(this, boolean.class.getSimpleName());
		}

		@Override
		public Object apply(char x1, char x2) {
			return ((short)x1 + (short)x2);
		}

		@Override
		public Object apply(byte x1, byte x2) {
			return ((short)x1 + (short)x2);
		}

		@Override
		public Object apply(short x1, short x2) {
			return x1 + x2;
		}

		@Override
		public Object apply(int x1, int x2) {
			return x1 + x2;
		}

		@Override
		public Object apply(long x1, long x2) {
			return x1 + x2;
		}

		@Override
		public Object apply(float x1, float x2) {
			return x1 + x2;
		}
		
		@Override
		public Object apply(double x1, double x2) {
			return x1 + x2;
		}

		@Override
		public String apply(String x1, String x2) {
			return x1 + x2;
		}
	},
	SUBTRACTION("-") {
		@Override
		public Object apply(boolean x1, boolean x2) {
			return handleError(this, boolean.class.getSimpleName());
		}

		@Override
		public Object apply(char x1, char x2) {
			return ((short)x1 - (short)x2);
		}

		@Override
		public Object apply(byte x1, byte x2) {
			return ((short)x1 - (short)x2);
		}

		@Override
		public Object apply(short x1, short x2) {
			return x1 - x2;
		}

		@Override
		public Object apply(int x1, int x2) {
			return x1 - x2;
		}

		@Override
		public Object apply(long x1, long x2) {
			return x1 - x2;
		}

		@Override
		public Object apply(float x1, float x2) {
			return x1 - x2;
		}
		
		@Override
		public Object apply(double x1, double x2) {
			return x1 - x2;
		}

		@Override
		public String apply(String x1, String x2) {
			return (String)handleError(this, x1.getClass().getSimpleName());
		}
	};

	private final String text;

	private Operator(String text) {
		this.text = text;
	}

	public abstract Object apply(boolean x1, boolean x2);
	public abstract Object apply(char x1, char x2);
	public abstract Object apply(byte x1, byte x2);
	public abstract Object apply(short x1, short x2);
	public abstract Object apply(int x1, int x2);
	public abstract Object apply(long x1, long x2);
	public abstract Object apply(float x1, float x2);
	public abstract Object apply(double x1, double x2);
	public abstract String apply(String x1, String x2);

	@Override
	public String toString() {
		return text;
	}

	private static Object handleError(Operator operator, String className) {
		throw new RuntimeException(String.format("%s for %s is not allowed!", operator.toString(), className));
	}
}
