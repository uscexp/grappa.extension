package com.github.uscexp.grappa.extension.interpreter.type;

public enum Comperator {
	GREATER(">") {
		@Override
		public boolean apply(boolean x1, boolean x2) {
			return (boolean)handleError(this, boolean.class.getSimpleName());
		}

		@Override
		public boolean apply(char x1, char x2) {
			return x1 > x2;
		}

		@Override
		public boolean apply(byte x1, byte x2) {
			return x1 > x2;
		}

		@Override
		public boolean apply(short x1, short x2) {
			return x1 > x2;
		}

		@Override
		public boolean apply(int x1, int x2) {
			return x1 > x2;
		}

		@Override
		public boolean apply(long x1, long x2) {
			return x1 > x2;
		}

		@Override
		public boolean apply(float x1, float x2) {
			return x1 > x2;
		}

		@Override
		public boolean apply(double x1, double x2) {
			return x1 > x2;
		}

		@Override
		public boolean apply(String x1, String x2) {
			return x1.compareTo(x2) > 0;
		}
	},
	GREATER_EQUALS(">=") {
		@Override
		public boolean apply(boolean x1, boolean x2) {
			return (boolean)handleError(this, boolean.class.getSimpleName());
		}

		@Override
		public boolean apply(char x1, char x2) {
			return x1 >= x2;
		}

		@Override
		public boolean apply(byte x1, byte x2) {
			return x1 >= x2;
		}

		@Override
		public boolean apply(short x1, short x2) {
			return x1 >= x2;
		}

		@Override
		public boolean apply(int x1, int x2) {
			return x1 >= x2;
		}

		@Override
		public boolean apply(long x1, long x2) {
			return x1 >= x2;
		}

		@Override
		public boolean apply(float x1, float x2) {
			return x1 >= x2;
		}

		@Override
		public boolean apply(double x1, double x2) {
			return x1 >= x2;
		}

		@Override
		public boolean apply(String x1, String x2) {
			return x1.compareTo(x2) >= 0;
		}
	},
	LESSER("<") {
		@Override
		public boolean apply(boolean x1, boolean x2) {
			return (boolean)handleError(this, boolean.class.getSimpleName());
		}

		@Override
		public boolean apply(char x1, char x2) {
			return x1 < x2;
		}

		@Override
		public boolean apply(byte x1, byte x2) {
			return x1 < x2;
		}

		@Override
		public boolean apply(short x1, short x2) {
			return x1 < x2;
		}

		@Override
		public boolean apply(int x1, int x2) {
			return x1 < x2;
		}

		@Override
		public boolean apply(long x1, long x2) {
			return x1 < x2;
		}

		@Override
		public boolean apply(float x1, float x2) {
			return x1 < x2;
		}

		@Override
		public boolean apply(double x1, double x2) {
			return x1 < x2;
		}

		@Override
		public boolean apply(String x1, String x2) {
			return x1.compareTo(x2) < 0;
		}
	},
	LESSER_EQUALS("<=") {
		@Override
		public boolean apply(boolean x1, boolean x2) {
			return (boolean)handleError(this, boolean.class.getSimpleName());
		}

		@Override
		public boolean apply(char x1, char x2) {
			return x1 <= x2;
		}

		@Override
		public boolean apply(byte x1, byte x2) {
			return x1 <= x2;
		}

		@Override
		public boolean apply(short x1, short x2) {
			return x1 <= x2;
		}

		@Override
		public boolean apply(int x1, int x2) {
			return x1 <= x2;
		}

		@Override
		public boolean apply(long x1, long x2) {
			return x1 <= x2;
		}

		@Override
		public boolean apply(float x1, float x2) {
			return x1 <= x2;
		}

		@Override
		public boolean apply(double x1, double x2) {
			return x1 <= x2;
		}

		@Override
		public boolean apply(String x1, String x2) {
			return x1.compareTo(x2) <= 0;
		}
	},
	EQUALS("==") {
		@Override
		public boolean apply(boolean x1, boolean x2) {
			return x1 == x2;
		}

		@Override
		public boolean apply(char x1, char x2) {
			return x1 == x2;
		}

		@Override
		public boolean apply(byte x1, byte x2) {
			return x1 == x2;
		}

		@Override
		public boolean apply(short x1, short x2) {
			return x1 == x2;
		}

		@Override
		public boolean apply(int x1, int x2) {
			return x1 == x2;
		}

		@Override
		public boolean apply(long x1, long x2) {
			return x1 == x2;
		}

		@Override
		public boolean apply(float x1, float x2) {
			return x1 == x2;
		}

		@Override
		public boolean apply(double x1, double x2) {
			return x1 == x2;
		}

		@Override
		public boolean apply(String x1, String x2) {
			return x1.equals(x2);
		}
	},
	NOT_EQUALS("!=") {
		@Override
		public boolean apply(boolean x1, boolean x2) {
			return x1 != x2;
		}

		@Override
		public boolean apply(char x1, char x2) {
			return x1 != x2;
		}

		@Override
		public boolean apply(byte x1, byte x2) {
			return x1 != x2;
		}

		@Override
		public boolean apply(short x1, short x2) {
			return x1 != x2;
		}

		@Override
		public boolean apply(int x1, int x2) {
			return x1 != x2;
		}

		@Override
		public boolean apply(long x1, long x2) {
			return x1 != x2;
		}

		@Override
		public boolean apply(float x1, float x2) {
			return x1 != x2;
		}

		@Override
		public boolean apply(double x1, double x2) {
			return x1 != x2;
		}

		@Override
		public boolean apply(String x1, String x2) {
			return !x1.equals(x2);
		}
	};
	
	private final String text;

	private Comperator(String text) {
		this.text = text;
	}

	public abstract boolean apply(boolean x1, boolean x2);
	public abstract boolean apply(char x1, char x2);
	public abstract boolean apply(byte x1, byte x2);
	public abstract boolean apply(short x1, short x2);
	public abstract boolean apply(int x1, int x2);
	public abstract boolean apply(long x1, long x2);
	public abstract boolean apply(float x1, float x2);
	public abstract boolean apply(double x1, double x2);
	public abstract boolean apply(String x1, String x2);

	@Override
	public String toString() {
		return text;
	}
	
	public static Comperator valueByString(String comparatorLiteral) {
		for (Comperator c : values()) {
			if(c.text.equals(comparatorLiteral)) {
				return c;
			}
		}
		return null;
	}

	private static Object handleError(Comperator comperator, String className) {
		throw new RuntimeException(String.format("%s for %s is not allowed!", comperator.toString(), className));
	}
}
