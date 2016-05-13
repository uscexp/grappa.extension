/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.interpreter.type;

import static com.github.uscexp.grappa.extension.interpreter.type.Comperator.EQUALS;
import static com.github.uscexp.grappa.extension.interpreter.type.Comperator.GREATER;
import static com.github.uscexp.grappa.extension.interpreter.type.Comperator.GREATER_EQUALS;
import static com.github.uscexp.grappa.extension.interpreter.type.Comperator.LESSER;
import static com.github.uscexp.grappa.extension.interpreter.type.Comperator.LESSER_EQUALS;
import static com.github.uscexp.grappa.extension.interpreter.type.Comperator.NOT_EQUALS;
import static com.github.uscexp.grappa.extension.interpreter.type.Operator.ADDITION;
import static com.github.uscexp.grappa.extension.interpreter.type.Operator.BITWISE_AND;
import static com.github.uscexp.grappa.extension.interpreter.type.Operator.BITWISE_OR;
import static com.github.uscexp.grappa.extension.interpreter.type.Operator.BITWISE_XOR;
import static com.github.uscexp.grappa.extension.interpreter.type.Operator.DIVISION;
import static com.github.uscexp.grappa.extension.interpreter.type.Operator.MODULO;
import static com.github.uscexp.grappa.extension.interpreter.type.Operator.MULTIPLICATION;
import static com.github.uscexp.grappa.extension.interpreter.type.Operator.SUBTRACTION;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author haui
 *
 */
public class Primitive {
	public static final short BOOLEAN = 1;
	public static final short CHARACTER = 2;
	public static final short BYTE = 3;
	public static final short SHORT = 4;
	public static final short INTEGER = 5;
	public static final short LONG = 6;
	public static final short FLOAT = 7;
	public static final short DOUBLE = 8;
	public static final short STRING = 9;
	public static final short FILE_READER = 10;
	public static final short FILE_WRITER = 11;
	private Class<?> primitiveType;
	private short typeId;
	private Object value;

	public Primitive(Class<?> primitiveType) {
		this.primitiveType = primitiveType;
		setTypeId();
		switch (getTypeId()) {
		case BOOLEAN:
			this.value = new Boolean(false);
			break;
		case CHARACTER:
			this.value = new Character(' ');
			break;
		case BYTE:
			this.value = new Byte((byte) 0);
			break;
		case SHORT:
			this.value = new Short((short) 0);
			break;
		case INTEGER:
			this.value = new Integer(0);
			break;
		case LONG:
			this.value = new Long(0L);
			break;
		case FLOAT:
			this.value = new Float(0.0F);
			break;
		case DOUBLE:
			this.value = new Double(0.0D);
			break;
		case STRING:
			this.value = new String("");
			break;
		case FILE_READER:
			this.value = null;
			break;
		case FILE_WRITER:
			this.value = null;
		}
	}

	public Primitive(Class<?> primitiveType, boolean value) {
		this.primitiveType = primitiveType;
		this.value = new Boolean(value);
		setTypeId();
	}

	public Primitive(Class<?> primitiveType, char value) {
		this.primitiveType = primitiveType;
		this.value = new Character(value);
		setTypeId();
	}

	public Primitive(Class<?> primitiveType, byte value) {
		this.primitiveType = primitiveType;
		this.value = new Byte(value);
		setTypeId();
	}

	public Primitive(Class<?> primitiveType, short value) {
		this.primitiveType = primitiveType;
		this.value = new Short(value);
		setTypeId();
	}

	public Primitive(Class<?> primitiveType, int value) {
		this.primitiveType = primitiveType;
		this.value = new Integer(value);
		setTypeId();
	}

	public Primitive(Class<?> primitiveType, long value) {
		this.primitiveType = primitiveType;
		this.value = new Long(value);
		setTypeId();
	}

	public Primitive(Class<?> primitiveType, float value) {
		this.primitiveType = primitiveType;
		this.value = new Float(value);
		setTypeId();
	}

	public Primitive(Class<?> primitiveType, double value) {
		this.primitiveType = primitiveType;
		this.value = new Double(value);
		setTypeId();
	}

	public Primitive(Class<?> type, String value) {
		if (type.isPrimitive()) {
			this.primitiveType = type;
		} else {
			this.primitiveType = getClass(type);
		}
		setTypeId();
		setValue(value);
	}

	public Primitive(Class<?> type, Object value) {
		if (type.isPrimitive()) {
			this.primitiveType = type;
		} else {
			this.primitiveType = getClass(type);
		}
		setTypeId();
		setValue(value);
	}

	public Primitive(Class<?> type, BufferedReader value) {
		if (type.isPrimitive()) {
			this.primitiveType = type;
		} else {
			this.primitiveType = getClass(type);
		}
		setTypeId();
		setValue(value);
	}

	public Primitive(Class<?> type, BufferedWriter value) {
		if (type.isPrimitive()) {
			this.primitiveType = type;
		} else {
			this.primitiveType = getClass(type);
		}
		setTypeId();
		setValue(value);
	}

	public Primitive(Object value) {
		this.primitiveType = getClass(value.getClass());
		setTypeId();
		this.value = value;
	}

	public static Class<?> getClassFromType(String type) {
		short typeIdx = getType(type);
		return getClassFromTypeIdx(typeIdx);
	}

	public static Class<?> getClassFromTypeIdx(short type) {
		Class<?> clazz = null;
		switch (type) {
		case BOOLEAN:
			clazz = Boolean.TYPE;
			break;
		case CHARACTER:
			clazz = Character.TYPE;
			break;
		case BYTE:
			clazz = Byte.TYPE;
			break;
		case SHORT:
			clazz = Short.TYPE;
			break;
		case INTEGER:
			clazz = Integer.TYPE;
			break;
		case LONG:
			clazz = Long.TYPE;
			break;
		case FLOAT:
			clazz = Float.TYPE;
			break;
		case DOUBLE:
			clazz = Double.TYPE;
			break;
		case STRING:
			clazz = String.class;
			break;
		case FILE_READER:
			clazz = BufferedReader.class;
			break;
		case FILE_WRITER:
			clazz = BufferedWriter.class;
		}
		return clazz;
	}

	public static short getType(String type) {
		short typeIdx = STRING;
		if (type.equals("boolean")) {
			typeIdx = BOOLEAN;
		} else if (type.equals("char")) {
			typeIdx = CHARACTER;
		} else if (type.equals("byte")) {
			typeIdx = BYTE;
		} else if (type.equals("short")) {
			typeIdx = SHORT;
		} else if (type.equals("int")) {
			typeIdx = INTEGER;
		} else if (type.equals("long")) {
			typeIdx = LONG;
		} else if (type.equals("float")) {
			typeIdx = FLOAT;
		} else if (type.equals("double")) {
			typeIdx = DOUBLE;
		} else if (type.equals("string")) {
			typeIdx = STRING;
		} else if (type.equals("filereader")) {
			typeIdx = FILE_READER;
		} else if (type.equals("filewriter")) {
			typeIdx = FILE_WRITER;
		}
		return typeIdx;
	}

	private void setTypeId() {
		if (getPrimitiveType().equals(Boolean.TYPE)) {
			this.typeId = 1;
		} else if (getPrimitiveType().equals(Character.TYPE)) {
			this.typeId = 2;
		} else if (getPrimitiveType().equals(Byte.TYPE)) {
			this.typeId = 3;
		} else if (getPrimitiveType().equals(Short.TYPE)) {
			this.typeId = 4;
		} else if (getPrimitiveType().equals(Integer.TYPE)) {
			this.typeId = 5;
		} else if (getPrimitiveType().equals(Long.TYPE)) {
			this.typeId = 6;
		} else if (getPrimitiveType().equals(Float.TYPE)) {
			this.typeId = 7;
		} else if (getPrimitiveType().equals(Double.TYPE)) {
			this.typeId = 8;
		} else if (getPrimitiveType().equals(String.class)) {
			this.typeId = 9;
		} else if (getPrimitiveType().equals(BufferedReader.class)) {
			this.typeId = 10;
		} else if (getPrimitiveType().equals(BufferedWriter.class)) {
			this.typeId = 11;
		}
	}

	public short getTypeId() {
		return this.typeId;
	}

	public Class<?> getPrimitiveType() {
		return this.primitiveType;
	}

	public Object getValue() {
		return this.value;
	}

	public boolean getBooleanValue() {
		boolean primValue = false;
		switch (this.typeId) {
		case BOOLEAN:
			primValue = ((Boolean) getValue()).booleanValue();
			break;
		case CHARACTER:
			primValue = ((Character) getValue()).charValue() > 0;
			break;
		case BYTE:
			primValue = ((Byte) getValue()).shortValue() > 0;
			break;
		case SHORT:
			primValue = ((Short) getValue()).shortValue() > 0;
			break;
		case INTEGER:
			primValue = ((Integer) getValue()).intValue() > 0;
			break;
		case LONG:
			primValue = ((Long) getValue()).longValue() > 0L;
			break;
		case FLOAT:
			primValue = ((Float) getValue()).floatValue() > 0.0F;
			break;
		case DOUBLE:
			primValue = ((Double) getValue()).doubleValue() > 0.0D;
			break;
		case STRING:
			primValue = new Boolean((String) getValue()).booleanValue();
			break;
		default:
			primValue = false;
		}
		return primValue;
	}

	public char getCharacterValue() {
		char primValue = ' ';
		switch (this.typeId) {
		case BOOLEAN:
			primValue = getBooleanValue() ? '1' : '0';
			break;
		case CHARACTER:
			primValue = ((Character) getValue()).charValue();
			break;
		case BYTE:
			primValue = (char) ((Byte) getValue()).shortValue();
			break;
		case SHORT:
			primValue = (char) ((Short) getValue()).shortValue();
			break;
		case INTEGER:
			primValue = (char) ((Integer) getValue()).intValue();
			break;
		case LONG:
			primValue = (char) (int) ((Long) getValue()).longValue();
			break;
		case STRING:
			primValue = ((String) getValue()).charAt(0);
			break;
		case FLOAT:
			primValue = (char) ((Float) getValue()).intValue();
			break;
		case DOUBLE:
			primValue = (char) ((Double) getValue()).intValue();
			break;
		default:
			primValue = ' ';
		}
		return primValue;
	}

	public byte getByteValue() {
		byte primValue = 0;
		switch (this.typeId) {
		case BOOLEAN:
			primValue = ((Boolean) getValue()).booleanValue() ? (byte) 1 : (byte) 0;
			break;
		case CHARACTER:
			primValue = ((Character) getValue()).toString().getBytes()[0];
			break;
		case BYTE:
			primValue = ((Byte) getValue()).byteValue();
			break;
		case SHORT:
			primValue = ((Short) getValue()).byteValue();
			break;
		case INTEGER:
			primValue = ((Integer) getValue()).byteValue();
			break;
		case LONG:
			primValue = ((Long) getValue()).byteValue();
			break;
		case FLOAT:
			primValue = ((Float) getValue()).byteValue();
			break;
		case DOUBLE:
			primValue = ((Double) getValue()).byteValue();
			break;
		case STRING:
			primValue = new Byte((String) getValue()).byteValue();
			break;
		default:
			primValue = 0;
		}
		return primValue;
	}

	public short getShortValue() {
		short primValue = 0;
		switch (this.typeId) {
		case BOOLEAN:
			primValue = ((Boolean) getValue()).booleanValue() ? (short) 1 : (short) 0;
			break;
		case CHARACTER:
			primValue = (short) ((Character) getValue()).charValue();
			break;
		case BYTE:
			primValue = ((Byte) getValue()).shortValue();
			break;
		case SHORT:
			primValue = ((Short) getValue()).shortValue();
			break;
		case INTEGER:
			primValue = ((Integer) getValue()).shortValue();
			break;
		case LONG:
			primValue = ((Long) getValue()).shortValue();
			break;
		case FLOAT:
			primValue = ((Float) getValue()).shortValue();
			break;
		case DOUBLE:
			primValue = ((Double) getValue()).shortValue();
			break;
		case STRING:
			primValue = new Short((String) getValue()).shortValue();
			break;
		default:
			primValue = 0;
		}
		return primValue;
	}

	public int getIntegerValue() {
		int primValue = 0;
		switch (this.typeId) {
		case BOOLEAN:
			primValue = ((Boolean) getValue()).booleanValue() ? 1 : 0;
			break;
		case CHARACTER:
			primValue = ((Character) getValue()).charValue();
			break;
		case BYTE:
			primValue = ((Byte) getValue()).intValue();
			break;
		case SHORT:
			primValue = ((Short) getValue()).intValue();
			break;
		case INTEGER:
			primValue = ((Integer) getValue()).intValue();
			break;
		case LONG:
			primValue = ((Long) getValue()).intValue();
			break;
		case FLOAT:
			primValue = ((Float) getValue()).intValue();
			break;
		case DOUBLE:
			primValue = ((Double) getValue()).intValue();
			break;
		case STRING:
			primValue = new Integer((String) getValue()).intValue();
			break;
		default:
			primValue = 0;
		}
		return primValue;
	}

	public long getLongValue() {
		long primValue = 0L;
		switch (this.typeId) {
		case BOOLEAN:
			primValue = ((Boolean) getValue()).booleanValue() ? 1 : 0;
			break;
		case CHARACTER:
			primValue = ((Character) getValue()).charValue();
			break;
		case BYTE:
			primValue = ((Byte) getValue()).longValue();
			break;
		case SHORT:
			primValue = ((Short) getValue()).longValue();
			break;
		case INTEGER:
			primValue = ((Integer) getValue()).longValue();
			break;
		case LONG:
			primValue = ((Long) getValue()).longValue();
			break;
		case FLOAT:
			primValue = ((Float) getValue()).longValue();
			break;
		case DOUBLE:
			primValue = ((Double) getValue()).longValue();
			break;
		case STRING:
			primValue = new Long((String) getValue()).longValue();
			break;
		default:
			primValue = 0L;
		}
		return primValue;
	}

	public float getFloatValue() {
		float primValue = 0.0F;
		switch (this.typeId) {
		case BOOLEAN:
			primValue = ((Boolean) getValue()).booleanValue() ? 1.0F : 0.0F;
			break;
		case CHARACTER:
			primValue = ((Character) getValue()).charValue();
			break;
		case BYTE:
			primValue = ((Byte) getValue()).floatValue();
			break;
		case SHORT:
			primValue = ((Short) getValue()).floatValue();
			break;
		case INTEGER:
			primValue = ((Integer) getValue()).floatValue();
			break;
		case LONG:
			primValue = ((Long) getValue()).floatValue();
			break;
		case FLOAT:
			primValue = ((Float) getValue()).floatValue();
			break;
		case DOUBLE:
			primValue = ((Double) getValue()).floatValue();
			break;
		case STRING:
			primValue = new Float((String) getValue()).floatValue();
			break;
		default:
			primValue = 0.0F;
		}
		return primValue;
	}

	public double getDoubleValue() {
		double primValue = 0.0D;
		switch (this.typeId) {
		case BOOLEAN:
			primValue = ((Boolean) getValue()).booleanValue() ? 1 : 0;
			break;
		case CHARACTER:
			primValue = ((Character) getValue()).charValue();
			break;
		case BYTE:
			primValue = ((Byte) getValue()).doubleValue();
			break;
		case SHORT:
			primValue = ((Short) getValue()).doubleValue();
			break;
		case INTEGER:
			primValue = ((Integer) getValue()).doubleValue();
			break;
		case LONG:
			primValue = ((Long) getValue()).doubleValue();
			break;
		case FLOAT:
			primValue = ((Float) getValue()).doubleValue();
			break;
		case DOUBLE:
			primValue = ((Double) getValue()).doubleValue();
			break;
		case STRING:
			primValue = new Double((String) getValue()).doubleValue();
			break;
		default:
			primValue = 0.0D;
		}
		return primValue;
	}

	public void setValue(boolean value) {
		switch (this.typeId) {
		case BOOLEAN:
			this.value = Boolean.valueOf(value);
			break;
		case CHARACTER:
			this.value = new Character((value ? '1' : '0'));
			break;
		case BYTE:
			this.value = new Byte((byte) (value ? 1 : 0));
			break;
		case SHORT:
			this.value = new Short((short) (value ? 1 : 0));
			break;
		case INTEGER:
			this.value = new Integer(value ? 1 : 0);
			break;
		case LONG:
			this.value = new Long(value ? 1 : 0);
			break;
		case FLOAT:
			this.value = new Float(value ? 1.0F : 0.0F);
			break;
		case DOUBLE:
			this.value = new Double(value ? 1.0D : 0.0D);
			break;
		case STRING:
			this.value = String.valueOf(value);
			break;
		default:
			this.value = Boolean.valueOf(false);
		}
	}

	public void setValue(char value) {
		switch (this.typeId) {
		case BOOLEAN:
			this.value = (value == 0 ? Boolean.valueOf(false) : Boolean.valueOf(true));
			break;
		case CHARACTER:
			this.value = new Character(value);
			break;
		case BYTE:
			this.value = new Byte(String.valueOf(value).getBytes()[0]);
			break;
		case SHORT:
			this.value = new Short((short) value);
			break;
		case INTEGER:
			this.value = new Integer(value);
			break;
		case LONG:
			this.value = new Long(value);
			break;
		case FLOAT:
			this.value = new Float(value);
			break;
		case DOUBLE:
			this.value = new Double(value);
			break;
		case STRING:
			this.value = String.valueOf(value);
			break;
		default:
			this.value = new Character(' ');
		}
	}

	public void setValue(byte value) {
		switch (this.typeId) {
		case BOOLEAN:
			this.value = (value == 0 ? Boolean.valueOf(false) : Boolean.valueOf(true));
			break;
		case CHARACTER:
			this.value = new Character((char) value);
			break;
		case BYTE:
			this.value = new Byte(value);
			break;
		case SHORT:
			this.value = new Short((short) value);
			break;
		case INTEGER:
			this.value = new Integer(value);
			break;
		case LONG:
			this.value = new Long(value);
			break;
		case FLOAT:
			this.value = new Float(value);
			break;
		case DOUBLE:
			this.value = new Double(value);
			break;
		case STRING:
			this.value = String.valueOf(value);
			break;
		default:
			this.value = new Byte((byte) 0);
		}
	}

	public void setValue(short value) {
		switch (this.typeId) {
		case BOOLEAN:
			this.value = (value == 0 ? Boolean.valueOf(false) : Boolean.valueOf(true));
			break;
		case CHARACTER:
			this.value = new Character((char) value);
			break;
		case BYTE:
			this.value = new Byte((byte) value);
			break;
		case SHORT:
			this.value = new Short(value);
			break;
		case INTEGER:
			this.value = new Integer(value);
			break;
		case LONG:
			this.value = new Long(value);
			break;
		case FLOAT:
			this.value = new Float(value);
			break;
		case DOUBLE:
			this.value = new Double(value);
			break;
		case STRING:
			this.value = String.valueOf(value);
			break;
		default:
			this.value = new Short((short) 0);
		}
	}

	public void setValue(int value) {
		switch (this.typeId) {
		case BOOLEAN:
			this.value = (value == 0 ? Boolean.valueOf(false) : Boolean.valueOf(true));
			break;
		case CHARACTER:
			this.value = new Character((char) value);
			break;
		case BYTE:
			this.value = new Byte((byte) value);
			break;
		case SHORT:
			this.value = new Short((short) value);
			break;
		case INTEGER:
			this.value = new Integer(value);
			break;
		case LONG:
			this.value = new Long(value);
			break;
		case FLOAT:
			this.value = new Float(value);
			break;
		case DOUBLE:
			this.value = new Double(value);
			break;
		case STRING:
			this.value = String.valueOf(value);
			break;
		default:
			this.value = new Integer(0);
		}
	}

	public void setValue(long value) {
		switch (this.typeId) {
		case BOOLEAN:
			this.value = (value == 0L ? Boolean.valueOf(false) : Boolean.valueOf(true));
			break;
		case CHARACTER:
			this.value = new Character((char) (int) value);
			break;
		case BYTE:
			this.value = new Byte((byte) (int) value);
			break;
		case SHORT:
			this.value = new Short((short) (int) value);
			break;
		case INTEGER:
			this.value = new Integer((int) value);
			break;
		case LONG:
			this.value = new Long(value);
			break;
		case FLOAT:
			this.value = new Float((float) value);
			break;
		case DOUBLE:
			this.value = new Double(value);
			break;
		case STRING:
			this.value = String.valueOf(value);
			break;
		default:
			this.value = new Long(0L);
		}
	}

	public void setValue(float value) {
		switch (this.typeId) {
		case BOOLEAN:
			this.value = (value == 0.0D ? Boolean.valueOf(false) : Boolean.valueOf(true));
			break;
		case CHARACTER:
			this.value = new Character((char) (int) value);
			break;
		case BYTE:
			this.value = new Byte((byte) (int) value);
			break;
		case SHORT:
			this.value = new Short((short) (int) value);
			break;
		case INTEGER:
			this.value = new Integer((int) value);
			break;
		case LONG:
			this.value = new Float(value).longValue();
			break;
		case FLOAT:
			this.value = new Float(value);
			break;
		case DOUBLE:
			this.value = new Double(value);
			break;
		case STRING:
			this.value = String.valueOf(value);
			break;
		default:
			this.value = new Float(0.0F);
		}
	}

	public void setValue(double value) {
		switch (this.typeId) {
		case BOOLEAN:
			this.value = (value == 0.0D ? Boolean.valueOf(false) : Boolean.valueOf(true));
			break;
		case CHARACTER:
			this.value = new Character((char) (int) value);
			break;
		case BYTE:
			this.value = new Byte((byte) (int) value);
			break;
		case SHORT:
			this.value = new Short((short) (int) value);
			break;
		case INTEGER:
			this.value = new Integer((int) value);
			break;
		case LONG:
			this.value = new Double(value).longValue();
			break;
		case FLOAT:
			this.value = new Float((float) value);
			break;
		case DOUBLE:
			this.value = new Double(value);
			break;
		case STRING:
			this.value = String.valueOf(value);
			break;
		default:
			this.value = new Double(0.0D);
		}
	}

	public void setValue(String value) {
		switch (this.typeId) {
		case BOOLEAN:
			this.value = new Boolean(value);
			break;
		case CHARACTER:
			this.value = new Character(value.charAt(0));
			break;
		case BYTE:
			this.value = (byte)Integer.parseInt(value);
			break;
		case SHORT:
			this.value = new Short(value);
			break;
		case INTEGER:
			this.value = new Integer(value);
			break;
		case LONG:
			this.value = new Long(value);
			break;
		case FLOAT:
			this.value = new Float(value);
			break;
		case DOUBLE:
			this.value = new Double(value);
			break;
		case STRING:
			this.value = String.valueOf(value);
			break;
		case FILE_READER:
			try {
				this.value = new BufferedReader(new FileReader(value));
			} catch (FileNotFoundException ex) {
				throw new RuntimeException(ex);
			}
		case FILE_WRITER:
			try {
				this.value = new BufferedWriter(new FileWriter(value));
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}
		default:
			this.value = value;
		}
	}

	public void setValue(BufferedReader value) {
		if (this.typeId == 10) {
			this.value = value;
		} else {
			throw new RuntimeException(String.format("%s can't be assigned to %s of type %s",
					value == null ? "null" : value.getClass().getSimpleName(), getClass().getSimpleName(), getPrimitiveType().getSimpleName()));
		}
	}

	public void setValue(BufferedWriter value) {
		if (this.typeId == 11) {
			this.value = value;
		} else {
			throw new RuntimeException(String.format("%s can't be assigned to %s of type %s",
					value == null ? "null" : value.getClass().getSimpleName(), getClass().getSimpleName(), getPrimitiveType().getSimpleName()));
		}
	}

	public void setValue(Object value) {
		Primitive primitive = new Primitive(value);
		switch (primitive.getTypeId()) {
		case BOOLEAN:
			switch (getTypeId()) {
			case BOOLEAN:
				value = new Boolean(primitive.getBooleanValue());
				break;
			case CHARACTER:
				value = new Character(primitive.getCharacterValue());
				break;
			case BYTE:
				value = new Byte(primitive.getByteValue());
				break;
			case SHORT:
				value = new Short(primitive.getShortValue());
				break;
			case INTEGER:
				value = new Integer(primitive.getIntegerValue());
				break;
			case LONG:
				value = new Long(primitive.getLongValue());
				break;
			case FLOAT:
				value = new Float(primitive.getFloatValue());
				break;
			case DOUBLE:
				value = new Double(primitive.getDoubleValue());
				break;
			case STRING:
				value = String.valueOf(value);
				break;
			case FILE_READER:
				value = ((BufferedReader) value);
				break;
			case FILE_WRITER:
				value = ((BufferedWriter) value);
			}
			break;
		case CHARACTER:
			switch (getTypeId()) {
			case BOOLEAN:
				value = new Boolean(primitive.getBooleanValue());
				break;
			case CHARACTER:
				value = new Character(primitive.getCharacterValue());
				break;
			case BYTE:
				value = new Byte(primitive.getByteValue());
				break;
			case SHORT:
				value = new Short(primitive.getShortValue());
				break;
			case INTEGER:
				value = new Integer(primitive.getIntegerValue());
				break;
			case LONG:
				value = new Long(primitive.getLongValue());
				break;
			case FLOAT:
				value = new Float(primitive.getFloatValue());
				break;
			case DOUBLE:
				value = new Double(primitive.getDoubleValue());
				break;
			case STRING:
				value = String.valueOf(value);
				break;
			case FILE_READER:
				value = ((BufferedReader) value);
				break;
			case FILE_WRITER:
				value = ((BufferedWriter) value);
			}
			break;
		case BYTE:
			switch (getTypeId()) {
			case BOOLEAN:
				value = new Boolean(primitive.getBooleanValue());
				break;
			case CHARACTER:
				value = new Character(primitive.getCharacterValue());
				break;
			case BYTE:
				value = new Byte(primitive.getByteValue());
				break;
			case SHORT:
				value = new Short(primitive.getShortValue());
				break;
			case INTEGER:
				value = new Integer(primitive.getIntegerValue());
				break;
			case LONG:
				value = new Long(primitive.getLongValue());
				break;
			case FLOAT:
				value = new Float(primitive.getFloatValue());
				break;
			case DOUBLE:
				value = new Double(primitive.getDoubleValue());
				break;
			case STRING:
				value = String.valueOf(value);
				break;
			case FILE_READER:
				value = ((BufferedReader) value);
				break;
			case FILE_WRITER:
				value = ((BufferedWriter) value);
			}
			break;
		case SHORT:
			switch (getTypeId()) {
			case BOOLEAN:
				value = new Boolean(primitive.getBooleanValue());
				break;
			case CHARACTER:
				value = new Character(primitive.getCharacterValue());
				break;
			case BYTE:
				value = new Byte(primitive.getByteValue());
				break;
			case SHORT:
				value = primitive.getValue();
				break;
			case INTEGER:
				value = new Integer(primitive.getIntegerValue());
				break;
			case LONG:
				value = new Long(primitive.getLongValue());
				break;
			case FLOAT:
				value = new Float(primitive.getFloatValue());
				break;
			case DOUBLE:
				value = new Double(primitive.getDoubleValue());
				break;
			case STRING:
				value = String.valueOf(value);
				break;
			case FILE_READER:
				value = ((BufferedReader) value);
				break;
			case FILE_WRITER:
				value = ((BufferedWriter) value);
			}
			break;
		case INTEGER:
			switch (getTypeId()) {
			case BOOLEAN:
				value = new Boolean(primitive.getBooleanValue());
				break;
			case CHARACTER:
				value = new Character(primitive.getCharacterValue());
				break;
			case BYTE:
				value = new Byte(primitive.getByteValue());
				break;
			case SHORT:
				value = new Short(primitive.getShortValue());
				break;
			case INTEGER:
				value = primitive.getValue();
				break;
			case LONG:
				value = new Long(primitive.getLongValue());
				break;
			case FLOAT:
				value = new Float(primitive.getFloatValue());
				break;
			case DOUBLE:
				value = new Double(primitive.getDoubleValue());
				break;
			case STRING:
				value = String.valueOf(value);
				break;
			case FILE_READER:
				value = ((BufferedReader) value);
				break;
			case FILE_WRITER:
				value = ((BufferedWriter) value);
			}
			break;
		case LONG:
			switch (getTypeId()) {
			case BOOLEAN:
				value = new Boolean(primitive.getBooleanValue());
				break;
			case CHARACTER:
				value = new Character(primitive.getCharacterValue());
				break;
			case BYTE:
				value = new Byte(primitive.getByteValue());
				break;
			case SHORT:
				value = new Short(primitive.getShortValue());
				break;
			case INTEGER:
				value = new Integer(primitive.getIntegerValue());
				break;
			case LONG:
				value = primitive.getValue();
				break;
			case FLOAT:
				value = new Float(primitive.getFloatValue());
				break;
			case DOUBLE:
				value = new Double(primitive.getDoubleValue());
				break;
			case STRING:
				value = String.valueOf(value);
				break;
			case FILE_READER:
				value = ((BufferedReader) value);
				break;
			case FILE_WRITER:
				value = ((BufferedWriter) value);
			}
			break;
		case FLOAT:
			switch (getTypeId()) {
			case BOOLEAN:
				value = new Boolean(primitive.getBooleanValue());
				break;
			case CHARACTER:
				value = new Character(primitive.getCharacterValue());
				break;
			case BYTE:
				value = new Byte(primitive.getByteValue());
				break;
			case SHORT:
				value = new Short(primitive.getShortValue());
				break;
			case INTEGER:
				value = new Integer(primitive.getIntegerValue());
				break;
			case LONG:
				value = new Long(primitive.getLongValue());
				break;
			case FLOAT:
				value = new Float(primitive.getFloatValue());
				break;
			case DOUBLE:
				value = new Double(primitive.getDoubleValue());
				break;
			case STRING:
				value = String.valueOf(value);
				break;
			case FILE_READER:
				value = ((BufferedReader) value);
				break;
			case FILE_WRITER:
				value = ((BufferedWriter) value);
			}
			break;
		case DOUBLE:
			switch (getTypeId()) {
			case BOOLEAN:
				value = new Boolean(primitive.getBooleanValue());
				break;
			case CHARACTER:
				value = new Character(primitive.getCharacterValue());
				break;
			case BYTE:
				value = new Byte(primitive.getByteValue());
				break;
			case SHORT:
				value = new Short(primitive.getShortValue());
				break;
			case INTEGER:
				value = new Integer(primitive.getIntegerValue());
				break;
			case LONG:
				value = new Long(primitive.getLongValue());
				break;
			case FLOAT:
				value = new Float(primitive.getFloatValue());
				break;
			case DOUBLE:
				value = new Double(primitive.getDoubleValue());
				break;
			case STRING:
				value = String.valueOf(value);
				break;
			case FILE_READER:
				value = ((BufferedReader) value);
				break;
			case FILE_WRITER:
				value = ((BufferedWriter) value);
			}
			break;
			
			default:
				throw new RuntimeException(String.format("%s can't be assigned to %s of type %s",
						value == null ? "null" : value.getClass().getSimpleName(), getClass().getSimpleName(), getPrimitiveType().getSimpleName()));
		}
		this.value = value;
	}

	public String resolveGetMethodName() {
		String method = null;
		switch (this.typeId) {
		case BOOLEAN:
			method = "getBooleanValue";
			break;
		case CHARACTER:
			method = "getCharacterValue";
			break;
		case BYTE:
			method = "getByteValue";
			break;
		case SHORT:
			method = "getShortValue";
			break;
		case INTEGER:
			method = "getIntegerValue";
			break;
		case LONG:
			method = "getLongValue";
			break;
		case FLOAT:
			method = "getFloatValue";
			break;
		case DOUBLE:
			method = "getDoubleValue";
			break;
		case STRING:
			method = "getValue";
			break;
		case FILE_READER:
			method = "getValue";
			break;
		case FILE_WRITER:
			method = "getValue";
			break;
		default:
			method = null;
		}
		return method;
	}

	public static Class<?> getClass(Class<?> type) {
		Class<?> clazz = null;
		if (type != null) {
			if (type.equals(Boolean.class)) {
				clazz = Boolean.TYPE;
			} else if (type.equals(Character.class)) {
				clazz = Character.TYPE;
			} else if (type.equals(Byte.class)) {
				clazz = Byte.TYPE;
			} else if (type.equals(Short.class)) {
				clazz = Short.TYPE;
			} else if (type.equals(Integer.class)) {
				clazz = Integer.TYPE;
			} else if (type.equals(Long.class)) {
				clazz = Long.TYPE;
			} else if (type.equals(Float.class)) {
				clazz = Float.TYPE;
			} else if (type.equals(Double.class)) {
				clazz = Double.TYPE;
			} else if (type.equals(String.class)) {
				clazz = String.class;
			} else if (type.equals(BufferedReader.class)) {
				clazz = BufferedReader.class;
			} else if (type.equals(BufferedWriter.class)) {
				clazz = BufferedWriter.class;
			} else if (type.isArray()) {
				clazz = Primitive[].class;
			}
		}
		return clazz;
	}

	public static Primitive[] createValue(Object[] objects) {
		if (objects == null) {
			return null;
		}
		Primitive[] primitives = new Primitive[objects.length];
		for (int i = 0; i < primitives.length; i++) {
			primitives[i] = new Primitive(objects[i]);
		}
		return primitives;
	}

	public static Primitive createValue(Object object) {
		if (object == null) {
			return null;
		}
		Primitive primitive = new Primitive(object);

		return primitive;
	}

	public static Object[] getValueArray(Primitive[] primitives) {
		int count = primitives.length;
		if ((primitives == null) || (count == 0)) {
			return null;
		}
		Class<?> clazz = primitives[0].getPrimitiveType();
		Object[] objects = null;
		if (clazz != null) {
			if (clazz.equals(Boolean.TYPE)) {
				objects = new Boolean[count];
			} else if (clazz.equals(Character.TYPE)) {
				objects = new Character[count];
			} else if (clazz.equals(Byte.TYPE)) {
				objects = new Byte[count];
			} else if (clazz.equals(Short.TYPE)) {
				objects = new Short[count];
			} else if (clazz.equals(Integer.TYPE)) {
				objects = new Integer[count];
			} else if (clazz.equals(Long.TYPE)) {
				objects = new Long[count];
			} else if (clazz.equals(Float.TYPE)) {
				objects = new Float[count];
			} else if (clazz.equals(Double.TYPE)) {
				objects = new Double[count];
			} else if (clazz.equals(String.class)) {
				objects = new String[count];
			} else if (clazz.equals(BufferedReader.class)) {
				objects = new BufferedReader[count];
			} else if (clazz.equals(BufferedWriter.class)) {
				objects = new BufferedWriter[count];
			}
		}
		for (int i = 0; i < count; i++) {
			objects[i] = primitives[i].getValue();
		}
		return objects;
	}

	@Override
	public String toString() {
		return this.value.toString();
	}

	@Override
	public int hashCode() {
		return this.value.hashCode();
	}

	public Primitive equalsObject(Object obj) {
		return new Primitive(Boolean.TYPE, equals(obj));
	}
	
	@Override
	public boolean equals(Object obj) {
		return compare(EQUALS, obj);
	}
	
	public boolean compare(Comperator comperator, Object obj) {
		Primitive check = getPrimitive(obj);
		boolean result = false;
		switch (getTypeId()) {
		case BOOLEAN:
			result = comperator.apply(getBooleanValue(), check.getBooleanValue());
			break;
		case CHARACTER:
			result = comperator.apply(getCharacterValue(), check.getCharacterValue());
			break;
		case BYTE:
			result = comperator.apply(getByteValue(), check.getByteValue());
			break;
		case SHORT:
			result = comperator.apply(getShortValue(), check.getShortValue());
			break;
		case INTEGER:
			result = comperator.apply(getIntegerValue(), check.getIntegerValue());
			break;
		case LONG:
			result = comperator.apply(getLongValue(), check.getLongValue());
			break;
		case FLOAT:
			result = comperator.apply(getFloatValue(), check.getFloatValue());
			break;
		case DOUBLE:
			result = comperator.apply(getDoubleValue(), check.getDoubleValue());
			break;
		case STRING:
			result = comperator.apply(toString(), (String)check.getValue());
			break;
		default:
			throw new RuntimeException(String.format("%s for %s is not allowed!", comperator.toString(), getPrimitiveType()));
		}
		return result;
	}
	
	public Primitive notEquals(Object obj) {
		return new Primitive(Boolean.TYPE, compare(NOT_EQUALS, obj));
	}

	public Primitive bitwiseAnd(Object obj) {
		return calculate(BITWISE_AND, obj);
	}

	private Primitive getPrimitive(Object obj) {
		if (!(obj instanceof Primitive)) {
			obj = new Primitive(obj);
		}
		return (Primitive)obj;
	}

	public Primitive bitwiseComplement() {
		Primitive primitive = null;
		switch (this.typeId) {
		case BOOLEAN:
			primitive = new Primitive(~getByteValue());
			break;
		case CHARACTER:
			primitive = new Primitive(~getCharacterValue());
			break;
		case BYTE:
			primitive = new Primitive(~getByteValue());
			break;
		case SHORT:
			primitive = new Primitive(~getShortValue());
			break;
		case INTEGER:
			primitive = new Primitive(~getIntegerValue());
			break;
		case LONG:
			primitive = new Primitive(~getLongValue());
			break;
		case FLOAT:
			primitive = new Primitive(~getLongValue());
			break;
		case DOUBLE:
			primitive = new Primitive(~getLongValue());
			break;
		default:
			throw new RuntimeException(String.format("%s for %s is not allowed!", "~", getPrimitiveType()));
		}
		return primitive;
	}

	public Primitive calculate(Operator operator, Object obj) {
		Primitive check = getPrimitive(obj);
		Primitive primitive = null;
		switch (this.typeId) {
		case BOOLEAN:
			primitive = new Primitive(operator.apply(getBooleanValue(), check.getBooleanValue()));
			break;
		case CHARACTER:
			primitive = new Primitive(operator.apply(getCharacterValue(), check.getCharacterValue()));
			break;
		case BYTE:
			primitive = new Primitive(operator.apply(getByteValue(), check.getByteValue()));
			break;
		case SHORT:
			primitive = new Primitive(operator.apply(getShortValue(), check.getShortValue()));
			break;
		case INTEGER:
			primitive = new Primitive(operator.apply(getIntegerValue(), check.getIntegerValue()));
			break;
		case LONG:
			primitive = new Primitive(operator.apply(getLongValue(), check.getLongValue()));
			break;
		case FLOAT:
			primitive = new Primitive(operator.apply(getFloatValue(), check.getFloatValue()));
			break;
		case DOUBLE:
			primitive = new Primitive(operator.apply(getDoubleValue(), check.getDoubleValue()));
			break;
		case STRING:
			primitive = new Primitive(operator.apply((String)getValue(), (String)check.getValue()));
			break;
		default:
			throw new RuntimeException(String.format("%s for %s is not allowed!", operator.toString(), getPrimitiveType()));
		}
		return primitive;
	}

	public Primitive bitwiseOr(Object obj) {
		return calculate(BITWISE_OR, obj);
	}

	public Primitive bitwiseXor(Object obj) {
		return calculate(BITWISE_XOR, obj);
	}

	public Primitive divide(Object obj) {
		return calculate(DIVISION, obj);
	}

	public Primitive greaterEqual(Object obj) {
		return new Primitive(Boolean.TYPE, compare(GREATER_EQUALS, obj));
	}

	public Primitive greaterThan(Object obj) {
		return new Primitive(Boolean.TYPE, compare(GREATER, obj));
	}

	public Primitive lesserEqual(Object obj) {
		return new Primitive(Boolean.TYPE, compare(LESSER_EQUALS, obj));
	}

	public Primitive lesserThan(Object obj) {
		return new Primitive(Boolean.TYPE, compare(LESSER, obj));
	}

	public Primitive mod(Object obj) {
		return calculate(MODULO, obj);
	}

	public Primitive multiplicate(Object obj) {
		return calculate(MULTIPLICATION, obj);
	}

	public Primitive substract(Object obj) {
		return calculate(SUBTRACTION, obj);
	}

	public Primitive add(Object obj) {
		return calculate(ADDITION, obj);
	}

	public void increment() {
		switch (this.typeId) {
		case CHARACTER:
			setValue(((Character) this.value).charValue() + 1);
			break;
		case BYTE:
			setValue(((Byte) this.value).byteValue() + 1);
			break;
		case SHORT:
			setValue(((Short) this.value).shortValue() + 1);
			break;
		case INTEGER:
			setValue(((Integer) this.value).intValue() + 1);
			break;
		case LONG:
			setValue(((Long) this.value).longValue() + 1L);
			break;
		case FLOAT:
			setValue(((Float) this.value).floatValue() + 1.0F);
			break;
		case DOUBLE:
			setValue(((Double) this.value).doubleValue() + 1.0D);
			break;
		default:
			throw new RuntimeException(String.format("%s for %s is not allowed!", "++", getPrimitiveType()));
		}
	}

	public void decrement() {
		switch (this.typeId) {
		case CHARACTER:
			setValue(((Character) this.value).charValue() - 1);
			break;
		case BYTE:
			setValue(((Byte) this.value).byteValue() - 1);
			break;
		case SHORT:
			setValue(((Short) this.value).shortValue() - 1);
			break;
		case INTEGER:
			setValue(((Integer) this.value).intValue() - 1);
			break;
		case LONG:
			setValue(((Long) this.value).longValue() - 1L);
			break;
		case FLOAT:
			setValue(((Float) this.value).floatValue() - 1.0F);
			break;
		case DOUBLE:
			setValue(((Double) this.value).doubleValue() - 1.0D);
			break;
		default:
			throw new RuntimeException(String.format("%s for %s is not allowed!", "--", getPrimitiveType()));
		}
	}
}
