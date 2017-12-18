/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.interpreter.type;

import static com.github.uscexp.grappa.extension.interpreter.type.Comparator.EQUALS;
import static com.github.uscexp.grappa.extension.interpreter.type.Comparator.GREATER;
import static com.github.uscexp.grappa.extension.interpreter.type.Comparator.GREATER_EQUALS;
import static com.github.uscexp.grappa.extension.interpreter.type.Comparator.LESSER;
import static com.github.uscexp.grappa.extension.interpreter.type.Comparator.LESSER_EQUALS;
import static com.github.uscexp.grappa.extension.interpreter.type.Comparator.NOT_EQUALS;
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
		setObjectValue(value);
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

	public static Class<?> getPrimitiveTypeFromType(String type) {
		short typeIdx = getType(type);
		return getPrimitiveTypeFromTypeIdx(typeIdx);
	}

	public static Class<?> getPrimitiveTypeFromTypeIdx(short type) {
		Class<?> clazz = null;
		switch (type) {
		case BOOLEAN:
			clazz = Boolean.class;
			break;
		case CHARACTER:
			clazz = Character.class;
			break;
		case BYTE:
			clazz = Byte.class;
			break;
		case SHORT:
			clazz = Short.class;
			break;
		case INTEGER:
			clazz = Integer.class;
			break;
		case LONG:
			clazz = Long.class;
			break;
		case FLOAT:
			clazz = Float.class;
			break;
		case DOUBLE:
			clazz = Double.class;
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
		if (getPrimitiveType().equals(Boolean.TYPE) || getPrimitiveType().equals(Boolean.class)) {
			this.typeId = BOOLEAN;
		} else if (getPrimitiveType().equals(Character.TYPE) || getPrimitiveType().equals(Character.class)) {
			this.typeId = CHARACTER;
		} else if (getPrimitiveType().equals(Byte.TYPE) || getPrimitiveType().equals(Byte.class)) {
			this.typeId = BYTE;
		} else if (getPrimitiveType().equals(Short.TYPE) || getPrimitiveType().equals(Short.class)) {
			this.typeId = SHORT;
		} else if (getPrimitiveType().equals(Integer.TYPE) || getPrimitiveType().equals(Integer.class)) {
			this.typeId = INTEGER;
		} else if (getPrimitiveType().equals(Long.TYPE) || getPrimitiveType().equals(Long.class)) {
			this.typeId = LONG;
		} else if (getPrimitiveType().equals(Float.TYPE) || getPrimitiveType().equals(Float.class)) {
			this.typeId = FLOAT;
		} else if (getPrimitiveType().equals(Double.TYPE) || getPrimitiveType().equals(Double.class)) {
			this.typeId = DOUBLE;
		} else if (getPrimitiveType().equals(String.class)) {
			this.typeId = STRING;
		} else if (getPrimitiveType().equals(BufferedReader.class)) {
			this.typeId = FILE_READER;
		} else if (getPrimitiveType().equals(BufferedWriter.class)) {
			this.typeId = FILE_WRITER;
		}
	}

	public short getTypeId() {
		return this.typeId;
	}

	public Class<?> getPrimitiveType() {
		return this.primitiveType;
	}

	public Object getValue() {
		Object primValue = value;
		switch (this.typeId) {
		case BOOLEAN:
			primValue = getBooleanValue();
			break;
		case CHARACTER:
			primValue = getCharacterValue();
			break;
		case BYTE:
			primValue = getByteValue();
			break;
		case SHORT:
			primValue = getShortValue();
			break;
		case INTEGER:
			primValue = getIntegerValue();
			break;
		case LONG:
			primValue = getLongValue();
			break;
		case FLOAT:
			primValue = getFloatValue();
			break;
		case DOUBLE:
			primValue = getDoubleValue();
			break;
		case STRING:
			primValue = value instanceof String ? value : String.valueOf(value);
			break;
		default:
			primValue = value;
		}
		return primValue;
	}

	public boolean getBooleanValue() {
		boolean primValue = false;
		switch (this.typeId) {
		case BOOLEAN:
			primValue = ((Boolean) value).booleanValue();
			break;
		case CHARACTER:
			primValue = ((Character) value).charValue() > 0;
			break;
		case BYTE:
			primValue = ((Byte) value).shortValue() > 0;
			break;
		case SHORT:
			primValue = ((Short) value).shortValue() > 0;
			break;
		case INTEGER:
			primValue = ((Integer) value).intValue() > 0;
			break;
		case LONG:
			primValue = ((Long) value).longValue() > 0L;
			break;
		case FLOAT:
			primValue = ((Float) value).floatValue() > 0.0F;
			break;
		case DOUBLE:
			primValue = ((Double) value).doubleValue() > 0.0D;
			break;
		case STRING:
			primValue = new Boolean((String) value).booleanValue();
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
			primValue = ((Character) value).charValue();
			break;
		case BYTE:
			primValue = (char) ((Byte) value).shortValue();
			break;
		case SHORT:
			primValue = (char) ((Short) value).shortValue();
			break;
		case INTEGER:
			primValue = (char) ((Integer) value).intValue();
			break;
		case LONG:
			primValue = (char) (int) ((Long) value).longValue();
			break;
		case STRING:
			primValue = ((String) value).charAt(0);
			break;
		case FLOAT:
			primValue = (char) ((Float) value).intValue();
			break;
		case DOUBLE:
			primValue = (char) ((Double) value).intValue();
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
			primValue = ((Boolean) value).booleanValue() ? (byte) 1 : (byte) 0;
			break;
		case CHARACTER:
			primValue = ((Character) value).toString().getBytes()[0];
			break;
		case BYTE:
			primValue = ((Byte) value).byteValue();
			break;
		case SHORT:
			primValue = ((Short) value).byteValue();
			break;
		case INTEGER:
			primValue = ((Integer) value).byteValue();
			break;
		case LONG:
			primValue = ((Long) value).byteValue();
			break;
		case FLOAT:
			primValue = ((Float) value).byteValue();
			break;
		case DOUBLE:
			primValue = ((Double) value).byteValue();
			break;
		case STRING:
			primValue = new Byte((String) value).byteValue();
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
			primValue = ((Boolean) value).booleanValue() ? (short) 1 : (short) 0;
			break;
		case CHARACTER:
			primValue = (short) ((Character) value).charValue();
			break;
		case BYTE:
			primValue = ((Byte) value).shortValue();
			break;
		case SHORT:
			primValue = ((Short) value).shortValue();
			break;
		case INTEGER:
			primValue = ((Integer) value).shortValue();
			break;
		case LONG:
			primValue = ((Long) value).shortValue();
			break;
		case FLOAT:
			primValue = ((Float) value).shortValue();
			break;
		case DOUBLE:
			primValue = ((Double) value).shortValue();
			break;
		case STRING:
			primValue = new Short((String) value).shortValue();
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
			primValue = ((Boolean) value).booleanValue() ? 1 : 0;
			break;
		case CHARACTER:
			primValue = ((Character) value).charValue();
			break;
		case BYTE:
			primValue = ((Byte) value).intValue();
			break;
		case SHORT:
			primValue = ((Short) value).intValue();
			break;
		case INTEGER:
			primValue = ((Integer) value).intValue();
			break;
		case LONG:
			primValue = ((Long) value).intValue();
			break;
		case FLOAT:
			primValue = ((Float) value).intValue();
			break;
		case DOUBLE:
			primValue = ((Double) value).intValue();
			break;
		case STRING:
			primValue = new Integer((String) value).intValue();
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
			primValue = ((Boolean) value).booleanValue() ? 1 : 0;
			break;
		case CHARACTER:
			primValue = ((Character) value).charValue();
			break;
		case BYTE:
			primValue = ((Byte) value).longValue();
			break;
		case SHORT:
			primValue = ((Short) value).longValue();
			break;
		case INTEGER:
			primValue = ((Integer) value).longValue();
			break;
		case LONG:
			primValue = ((Long) value).longValue();
			break;
		case FLOAT:
			primValue = ((Float) value).longValue();
			break;
		case DOUBLE:
			primValue = ((Double) value).longValue();
			break;
		case STRING:
			primValue = new Long((String) value).longValue();
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
			primValue = ((Boolean) value).booleanValue() ? 1.0F : 0.0F;
			break;
		case CHARACTER:
			primValue = ((Character) value).charValue();
			break;
		case BYTE:
			primValue = ((Byte) value).floatValue();
			break;
		case SHORT:
			primValue = ((Short) value).floatValue();
			break;
		case INTEGER:
			primValue = ((Integer) value).floatValue();
			break;
		case LONG:
			primValue = ((Long) value).floatValue();
			break;
		case FLOAT:
			primValue = ((Float) value).floatValue();
			break;
		case DOUBLE:
			primValue = ((Double) value).floatValue();
			break;
		case STRING:
			primValue = new Float((String) value).floatValue();
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
			primValue = ((Boolean) value).booleanValue() ? 1 : 0;
			break;
		case CHARACTER:
			primValue = ((Character) value).charValue();
			break;
		case BYTE:
			primValue = ((Byte) value).doubleValue();
			break;
		case SHORT:
			primValue = ((Short) value).doubleValue();
			break;
		case INTEGER:
			primValue = ((Integer) value).doubleValue();
			break;
		case LONG:
			primValue = ((Long) value).doubleValue();
			break;
		case FLOAT:
			primValue = ((Float) value).doubleValue();
			break;
		case DOUBLE:
			primValue = ((Double) value).doubleValue();
			break;
		case STRING:
			primValue = new Double((String) value).doubleValue();
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
			this.value = new Float(value);
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
			this.value = (byte) Integer.parseInt(value);
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
			this.value = value;
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

	public void setObjectValue(Object value) {
		Primitive primitive = new Primitive(value);
		switch (primitive.getTypeId()) {
		case BOOLEAN:
			setValue(primitive.getBooleanValue());
			break;
		case CHARACTER:
			setValue(primitive.getCharacterValue());
			break;
		case BYTE:
			setValue(primitive.getByteValue());
			break;
		case SHORT:
			setValue(primitive.getShortValue());
			break;
		case INTEGER:
			setValue(primitive.getIntegerValue());
			break;
		case LONG:
			setValue(primitive.getLongValue());
			break;
		case FLOAT:
			setValue(primitive.getFloatValue());
			break;
		case DOUBLE:
			setValue(primitive.getDoubleValue());
			break;

		case STRING:
			setValue((String) primitive.getValue());
			break;

		case FILE_READER:
			setValue((BufferedReader) primitive.getValue());
			break;

		case FILE_WRITER:
			setValue((BufferedWriter) primitive.getValue());
			break;

		default:
			throw new RuntimeException(String.format("%s can't be assigned to %s of type %s",
					value == null ? "null" : value.getClass().getSimpleName(), getClass().getSimpleName(), getPrimitiveType().getSimpleName()));
		}
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

	public boolean compare(Comparator comperator, Object obj) {
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
			result = comperator.apply(String.valueOf(value), String.valueOf(check.getValue()));
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
		return (Primitive) obj;
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
			primitive = new Primitive(operator.apply((String) value, String.valueOf(check.getValue())));
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

	public void positive() {
		switch (this.typeId) {
		case CHARACTER:
			setValue(+((Character) this.value).charValue());
			break;
		case BYTE:
			setValue(+((Byte) this.value).byteValue());
			break;
		case SHORT:
			setValue(+((Short) this.value).shortValue());
			break;
		case INTEGER:
			setValue(+((Integer) this.value).intValue());
			break;
		case LONG:
			setValue(+((Long) this.value).longValue());
			break;
		case FLOAT:
			setValue(+((Float) this.value).floatValue());
			break;
		case DOUBLE:
			setValue(+((Double) this.value).doubleValue());
			break;
		default:
			throw new RuntimeException(String.format("%s for %s is not allowed!", "-", getPrimitiveType()));
		}
	}

	public void negative() {
		switch (this.typeId) {
		case CHARACTER:
			setValue(-((Character) this.value).charValue());
			break;
		case BYTE:
			setValue(-((Byte) this.value).byteValue());
			break;
		case SHORT:
			setValue(-((Short) this.value).shortValue());
			break;
		case INTEGER:
			setValue(-((Integer) this.value).intValue());
			break;
		case LONG:
			setValue(-((Long) this.value).longValue());
			break;
		case FLOAT:
			setValue(-((Float) this.value).floatValue());
			break;
		case DOUBLE:
			setValue(-((Double) this.value).doubleValue());
			break;
		default:
			throw new RuntimeException(String.format("%s for %s is not allowed!", "-", getPrimitiveType()));
		}
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
