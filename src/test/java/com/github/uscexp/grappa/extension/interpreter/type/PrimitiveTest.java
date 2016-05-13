package com.github.uscexp.grappa.extension.interpreter.type;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.StringReader;
import java.io.StringWriter;

import org.junit.Test;

public class PrimitiveTest {

	@Test
	public void testPrimitiveClass() {
		Primitive sut = new Primitive(boolean.class);
		assertEquals(Primitive.BOOLEAN, sut.getTypeId());
		sut = new Primitive(char.class);
		assertEquals(Primitive.CHARACTER, sut.getTypeId());
		sut = new Primitive(byte.class);
		assertEquals(Primitive.BYTE, sut.getTypeId());
		sut = new Primitive(short.class);
		assertEquals(Primitive.SHORT, sut.getTypeId());
		sut = new Primitive(int.class);
		assertEquals(Primitive.INTEGER, sut.getTypeId());
		sut = new Primitive(long.class);
		assertEquals(Primitive.LONG, sut.getTypeId());
		sut = new Primitive(float.class);
		assertEquals(Primitive.FLOAT, sut.getTypeId());
		sut = new Primitive(double.class);
		assertEquals(Primitive.DOUBLE, sut.getTypeId());
		sut = new Primitive(String.class);
		assertEquals(Primitive.STRING, sut.getTypeId());
		sut = new Primitive(BufferedReader.class);
		assertEquals(Primitive.FILE_READER, sut.getTypeId());
		sut = new Primitive(BufferedWriter.class);
		assertEquals(Primitive.FILE_WRITER, sut.getTypeId());
	}

	@Test
	public void testPrimitiveClassBoolean() {
		Primitive sut = new Primitive(boolean.class, true);
		assertEquals(Primitive.BOOLEAN, sut.getTypeId());
		assertEquals(true, sut.getBooleanValue());
	}

	@Test
	public void testPrimitiveClassChar() {
		Primitive sut = new Primitive(char.class, 'c');
		assertEquals(Primitive.CHARACTER, sut.getTypeId());
		assertEquals('c', sut.getCharacterValue());
	}

	@Test
	public void testPrimitiveClassByte() {
		byte b = "b".getBytes()[0];
		Primitive sut = new Primitive(byte.class, b);
		assertEquals(Primitive.BYTE, sut.getTypeId());
		assertEquals(b, sut.getByteValue());
	}

	@Test
	public void testPrimitiveClassShort() {
		short s = (short)1;
		Primitive sut = new Primitive(short.class, s);
		assertEquals(Primitive.SHORT, sut.getTypeId());
		assertEquals(s, sut.getShortValue());
	}

	@Test
	public void testPrimitiveClassInt() {
		Primitive sut = new Primitive(int.class, 1);
		assertEquals(Primitive.INTEGER, sut.getTypeId());
		assertEquals(1, sut.getIntegerValue());
	}

	@Test
	public void testPrimitiveClassLong() {
		Primitive sut = new Primitive(long.class, 1L);
		assertEquals(Primitive.LONG, sut.getTypeId());
		assertEquals(1L, sut.getLongValue());
	}

	@Test
	public void testPrimitiveClassFloat() {
		Primitive sut = new Primitive(float.class, (float)1.1);
		assertEquals(Primitive.FLOAT, sut.getTypeId());
		assertEquals(1.1F, sut.getFloatValue(), 0);
	}

	@Test
	public void testPrimitiveClassDouble() {
		Primitive sut = new Primitive(double.class, 1.1);
		assertEquals(Primitive.DOUBLE, sut.getTypeId());
		assertEquals(1.1, sut.getDoubleValue(), 0);
	}

	@Test
	public void testPrimitiveClassString() {
		String value = "string";
		Primitive sut = new Primitive(String.class, value);
		assertEquals(Primitive.STRING, sut.getTypeId());
		assertEquals(value, sut.getValue());
	}

	@Test
	public void testPrimitiveClassObject() {
		Integer integer = new Integer(1);
		Primitive sut = new Primitive(Integer.class, (Object)integer);
		assertEquals(Primitive.INTEGER, sut.getTypeId());
		assertEquals(integer.intValue(), sut.getIntegerValue());
	}

	@Test
	public void testPrimitiveClassBufferedReader() {
		BufferedReader bufferedReader = new BufferedReader(new StringReader("test"));
		Primitive sut = new Primitive(BufferedReader.class, bufferedReader);
		assertEquals(Primitive.FILE_READER, sut.getTypeId());
		assertEquals(bufferedReader, sut.getValue());
	}

	@Test
	public void testPrimitiveClassBufferedWriter() {
		BufferedWriter bufferedWriter = new BufferedWriter(new StringWriter());
		Primitive sut = new Primitive(BufferedWriter.class, bufferedWriter);
		assertEquals(Primitive.FILE_WRITER, sut.getTypeId());
		assertEquals(bufferedWriter, sut.getValue());
	}

	@Test
	public void testPrimitiveObject() {
		Primitive sut = new Primitive(true);
		assertEquals(true, sut.getBooleanValue());
		sut = new Primitive('c');
		assertEquals('c', sut.getCharacterValue());
		byte b = "b".getBytes()[0];
		sut = new Primitive(b);
		assertEquals(b, sut.getByteValue());
		short s = (short)1;
		sut = new Primitive(s);
		assertEquals(s, sut.getShortValue());
		sut = new Primitive(1);
		assertEquals(1, sut.getIntegerValue());
		sut = new Primitive(1L);
		assertEquals(1L, sut.getLongValue());
		sut = new Primitive((float)1.1);
		assertEquals((float)1.1, sut.getFloatValue(), 0);
		sut = new Primitive((double)1.1);
		assertEquals((double)1.1, sut.getDoubleValue(), 0);
		sut = new Primitive("string");
		assertEquals("string", sut.getValue());
		BufferedReader bufferedReader = new BufferedReader(new StringReader("test"));
		sut = new Primitive(bufferedReader);
		assertEquals(bufferedReader, sut.getValue());
		BufferedWriter bufferedWriter = new BufferedWriter(new StringWriter());
		sut = new Primitive(bufferedWriter);
		assertEquals(bufferedWriter, sut.getValue());
	}

	@Test
	public void testGetClassFromType() {
		Class<?> result = Primitive.getClassFromType("boolean");
		assertEquals(boolean.class, result);
		result = Primitive.getClassFromType("char");
		assertEquals(char.class, result);
		result = Primitive.getClassFromType("byte");
		assertEquals(byte.class, result);
		result = Primitive.getClassFromType("short");
		assertEquals(short.class, result);
		result = Primitive.getClassFromType("int");
		assertEquals(int.class, result);
		result = Primitive.getClassFromType("long");
		assertEquals(long.class, result);
		result = Primitive.getClassFromType("float");
		assertEquals(float.class, result);
		result = Primitive.getClassFromType("double");
		assertEquals(double.class, result);
		result = Primitive.getClassFromType("string");
		assertEquals(String.class, result);
		result = Primitive.getClassFromType("filereader");
		assertEquals(BufferedReader.class, result);
		result = Primitive.getClassFromType("filewriter");
		assertEquals(BufferedWriter.class, result);
	}

	@Test
	public void testGetClassFromTypeIdx() {
		Class<?> result = Primitive.getClassFromTypeIdx(Primitive.BOOLEAN);
		assertEquals(boolean.class, result);
		result = Primitive.getClassFromTypeIdx(Primitive.CHARACTER);
		assertEquals(char.class, result);
		result = Primitive.getClassFromTypeIdx(Primitive.BYTE);
		assertEquals(byte.class, result);
		result = Primitive.getClassFromTypeIdx(Primitive.SHORT);
		assertEquals(short.class, result);
		result = Primitive.getClassFromTypeIdx(Primitive.INTEGER);
		assertEquals(int.class, result);
		result = Primitive.getClassFromTypeIdx(Primitive.LONG);
		assertEquals(long.class, result);
		result = Primitive.getClassFromTypeIdx(Primitive.FLOAT);
		assertEquals(float.class, result);
		result = Primitive.getClassFromTypeIdx(Primitive.DOUBLE);
		assertEquals(double.class, result);
		result = Primitive.getClassFromTypeIdx(Primitive.STRING);
		assertEquals(String.class, result);
		result = Primitive.getClassFromTypeIdx(Primitive.FILE_READER);
		assertEquals(BufferedReader.class, result);
		result = Primitive.getClassFromTypeIdx(Primitive.FILE_WRITER);
		assertEquals(BufferedWriter.class, result);
	}

	@Test
	public void testGetType() {
		short result = Primitive.getType("boolean");
		assertEquals(Primitive.BOOLEAN, result);
		result = Primitive.getType("char");
		assertEquals(Primitive.CHARACTER, result);
		result = Primitive.getType("byte");
		assertEquals(Primitive.BYTE, result);
		result = Primitive.getType("short");
		assertEquals(Primitive.SHORT, result);
		result = Primitive.getType("int");
		assertEquals(Primitive.INTEGER, result);
		result = Primitive.getType("long");
		assertEquals(Primitive.LONG, result);
		result = Primitive.getType("float");
		assertEquals(Primitive.FLOAT, result);
		result = Primitive.getType("double");
		assertEquals(Primitive.DOUBLE, result);
		result = Primitive.getType("string");
		assertEquals(Primitive.STRING, result);
		result = Primitive.getType("filereader");
		assertEquals(Primitive.FILE_READER, result);
		result = Primitive.getType("filewriter");
		assertEquals(Primitive.FILE_WRITER, result);
	}

	@Test
	public void testGetTypeId() {
		Primitive sut = new Primitive(true);
		assertEquals(Primitive.BOOLEAN, sut.getTypeId());
		sut = new Primitive('c');
		assertEquals(Primitive.CHARACTER, sut.getTypeId());
		byte b = "b".getBytes()[0];
		sut = new Primitive(b);
		assertEquals(Primitive.BYTE, sut.getTypeId());
		short s = (short)1;
		sut = new Primitive(s);
		assertEquals(Primitive.SHORT, sut.getTypeId());
		sut = new Primitive(1);
		assertEquals(Primitive.INTEGER, sut.getTypeId());
		sut = new Primitive(1L);
		assertEquals(Primitive.LONG, sut.getTypeId());
		sut = new Primitive((float)1.1);
		assertEquals(Primitive.FLOAT, sut.getTypeId());
		sut = new Primitive((double)1.1);
		assertEquals(Primitive.DOUBLE, sut.getTypeId());
		sut = new Primitive("string");
		assertEquals(Primitive.STRING, sut.getTypeId());
		BufferedReader bufferedReader = new BufferedReader(new StringReader("test"));
		sut = new Primitive(bufferedReader);
		assertEquals(Primitive.FILE_READER, sut.getTypeId());
		BufferedWriter bufferedWriter = new BufferedWriter(new StringWriter());
		sut = new Primitive(bufferedWriter);
		assertEquals(Primitive.FILE_WRITER, sut.getTypeId());
	}

	@Test
	public void testGetPrimitiveType() {
		Primitive sut = new Primitive(true);
		assertEquals(boolean.class, sut.getPrimitiveType());
		sut = new Primitive('c');
		assertEquals(char.class, sut.getPrimitiveType());
		byte b = "b".getBytes()[0];
		sut = new Primitive(b);
		assertEquals(byte.class, sut.getPrimitiveType());
		short s = (short)1;
		sut = new Primitive(s);
		assertEquals(short.class, sut.getPrimitiveType());
		sut = new Primitive(1);
		assertEquals(int.class, sut.getPrimitiveType());
		sut = new Primitive(1L);
		assertEquals(long.class, sut.getPrimitiveType());
		sut = new Primitive((float)1.1);
		assertEquals(float.class, sut.getPrimitiveType());
		sut = new Primitive((double)1.1);
		assertEquals(double.class, sut.getPrimitiveType());
		sut = new Primitive("string");
		assertEquals(String.class, sut.getPrimitiveType());
		BufferedReader bufferedReader = new BufferedReader(new StringReader("test"));
		sut = new Primitive(bufferedReader);
		assertEquals(BufferedReader.class, sut.getPrimitiveType());
		BufferedWriter bufferedWriter = new BufferedWriter(new StringWriter());
		sut = new Primitive(bufferedWriter);
		assertEquals(BufferedWriter.class, sut.getPrimitiveType());
	}

	@Test
	public void testGetValue() {
		Primitive sut = new Primitive(true);
		assertEquals(new Boolean(true), sut.getValue());
		sut = new Primitive('c');
		assertEquals(new Character('c'), sut.getValue());
		byte b = "b".getBytes()[0];
		sut = new Primitive(b);
		assertEquals(new Byte(b), sut.getValue());
		short s = (short)1;
		sut = new Primitive(s);
		assertEquals(new Short(s), sut.getValue());
		sut = new Primitive(1);
		assertEquals(new Integer(1), sut.getValue());
		sut = new Primitive(1L);
		assertEquals(new Long(1L), sut.getValue());
		sut = new Primitive((float)1.1);
		assertEquals(new Float((float)1.1), (Float)sut.getValue(), 0);
		sut = new Primitive((double)1.1);
		assertEquals(new Double((double)1.1), (Double)sut.getValue(), 0);
		sut = new Primitive("string");
		assertEquals("string", sut.getValue());
		BufferedReader bufferedReader = new BufferedReader(new StringReader("test"));
		sut = new Primitive(bufferedReader);
		assertEquals(bufferedReader, sut.getValue());
		BufferedWriter bufferedWriter = new BufferedWriter(new StringWriter());
		sut = new Primitive(bufferedWriter);
		assertEquals(bufferedWriter, sut.getValue());
	}

	@Test
	public void testGetBooleanValue() {
		Primitive sut = new Primitive(boolean.class, true);
		assertEquals(Primitive.BOOLEAN, sut.getTypeId());
		assertEquals(true, sut.getBooleanValue());
	}

	@Test
	public void testGetCharacterValue() {
		char c = 'c';
		Primitive sut = new Primitive(char.class, c);
		assertEquals(Primitive.CHARACTER, sut.getTypeId());
		assertEquals(c, sut.getCharacterValue());
	}

	@Test
	public void testGetByteValue() {
		byte b = new Byte("1").byteValue();
		Primitive sut = new Primitive(byte.class, b);
		assertEquals(Primitive.BYTE, sut.getTypeId());
		assertEquals(b, sut.getByteValue());
	}

	@Test
	public void testGetShortValue() {
		short s = 1;
		Primitive sut = new Primitive(short.class, s);
		assertEquals(Primitive.SHORT, sut.getTypeId());
		assertEquals(s, sut.getShortValue());
	}

	@Test
	public void testGetIntegerValue() {
		int i = 1;
		Primitive sut = new Primitive(int.class, i);
		assertEquals(Primitive.INTEGER, sut.getTypeId());
		assertEquals(i, sut.getIntegerValue());
	}

	@Test
	public void testGetLongValue() {
		long l = 1;
		Primitive sut = new Primitive(long.class, l);
		assertEquals(Primitive.LONG, sut.getTypeId());
		assertEquals(l, sut.getLongValue());
	}

	@Test
	public void testGetFloatValue() {
		Primitive sut = new Primitive(float.class, (float)1.1);
		assertEquals(Primitive.FLOAT, sut.getTypeId());
		assertEquals(1.1F, sut.getFloatValue(), 0);
	}

	@Test
	public void testGetDoubleValue() {
		Primitive sut = new Primitive(double.class, 1.1);
		assertEquals(Primitive.DOUBLE, sut.getTypeId());
		assertEquals(1.1, sut.getDoubleValue(), 0);
	}

	@Test
	public void testSetValueBoolean() {
		Primitive sut = new Primitive(boolean.class);
		assertEquals(Primitive.BOOLEAN, sut.getTypeId());
		sut.setValue(true);
		assertEquals(true, sut.getBooleanValue());
		sut.setValue('1');
		assertEquals(true, sut.getBooleanValue());
		sut.setValue((byte)1);
		assertEquals(true, sut.getBooleanValue());
		sut.setValue((short)1);
		assertEquals(true, sut.getBooleanValue());
		sut.setValue(1);
		assertEquals(true, sut.getBooleanValue());
		sut.setValue(1L);
		assertEquals(true, sut.getBooleanValue());
		sut.setValue(1F);
		assertEquals(true, sut.getBooleanValue());
		sut.setValue(1D);
		assertEquals(true, sut.getBooleanValue());
	}

	@Test
	public void testSetValueChar() {
		Primitive sut = new Primitive(char.class);
		assertEquals(Primitive.CHARACTER, sut.getTypeId());
		sut.setValue(true);
		assertEquals('1', sut.getCharacterValue());
		char c = 'c';
		sut.setValue(c);
		assertEquals(c, sut.getCharacterValue());
		sut.setValue((byte)65);
		assertEquals('A', sut.getCharacterValue());
		sut.setValue((short)65);
		assertEquals('A', sut.getCharacterValue());
		sut.setValue(65);
		assertEquals('A', sut.getCharacterValue());
		sut.setValue(65L);
		assertEquals('A', sut.getCharacterValue());
		sut.setValue(65F);
		assertEquals('A', sut.getCharacterValue());
		sut.setValue(65D);
		assertEquals('A', sut.getCharacterValue());
		sut.setValue("A");
		assertEquals('A', sut.getCharacterValue());
	}

	@Test
	public void testSetValueByte() {
		Primitive sut = new Primitive(byte.class);
		assertEquals(Primitive.BYTE, sut.getTypeId());
		sut.setValue(true);
		assertEquals((byte)1, sut.getByteValue());
		sut.setValue((byte)1);
		assertEquals((byte)1, sut.getByteValue());
		sut.setValue((short)1);
		assertEquals((byte)1, sut.getByteValue());
		sut.setValue(1);
		assertEquals((byte)1, sut.getByteValue());
		sut.setValue(1L);
		assertEquals((byte)1, sut.getByteValue());
		sut.setValue(1F);
		assertEquals((byte)1, sut.getByteValue());
		sut.setValue(1D);
		assertEquals((byte)1, sut.getByteValue());
		sut.setValue("1");
		assertEquals((byte)1, sut.getByteValue());
	}

	@Test
	public void testSetValueShort() {
		Primitive sut = new Primitive(short.class);
		assertEquals(Primitive.SHORT, sut.getTypeId());
		sut.setValue(true);
		assertEquals((short)1, sut.getShortValue());
		sut.setValue((byte)1);
		assertEquals((short)1, sut.getShortValue());
		sut.setValue((short)1);
		assertEquals((short)1, sut.getShortValue());
		sut.setValue(1);
		assertEquals((short)1, sut.getShortValue());
		sut.setValue(1L);
		assertEquals((short)1, sut.getShortValue());
		sut.setValue(1F);
		assertEquals((short)1, sut.getShortValue());
		sut.setValue(1D);
		assertEquals((short)1, sut.getShortValue());
		sut.setValue("1");
		assertEquals((short)1, sut.getShortValue());
	}

	@Test
	public void testSetValueInt() {
		Primitive sut = new Primitive(int.class);
		assertEquals(Primitive.INTEGER, sut.getTypeId());
		sut.setValue(true);
		assertEquals((short)1, sut.getIntegerValue());
		sut.setValue((byte)1);
		assertEquals(1, sut.getIntegerValue());
		sut.setValue((short)1);
		assertEquals(1, sut.getIntegerValue());
		sut.setValue(1);
		assertEquals(1, sut.getIntegerValue());
		sut.setValue(1L);
		assertEquals(1, sut.getIntegerValue());
		sut.setValue(1F);
		assertEquals(1, sut.getIntegerValue());
		sut.setValue(1D);
		assertEquals(1, sut.getIntegerValue());
		sut.setValue("1");
		assertEquals(1, sut.getIntegerValue());
	}

	@Test
	public void testSetValueLong() {
		Primitive sut = new Primitive(long.class);
		assertEquals(Primitive.LONG, sut.getTypeId());
		sut.setValue(true);
		assertEquals((short)1, sut.getLongValue());
		sut.setValue((byte)1);
		assertEquals(1L, sut.getLongValue());
		sut.setValue((short)1);
		assertEquals(1L, sut.getLongValue());
		sut.setValue(1);
		assertEquals(1, sut.getLongValue());
		sut.setValue(1L);
		assertEquals(1L, sut.getLongValue());
		sut.setValue(1F);
		assertEquals(1L, sut.getLongValue());
		sut.setValue(1D);
		assertEquals(1L, sut.getLongValue());
		sut.setValue("1");
		assertEquals(1L, sut.getLongValue());
	}

	@Test
	public void testSetValueFloat() {
		Primitive sut = new Primitive(float.class);
		assertEquals(Primitive.FLOAT, sut.getTypeId());
		sut.setValue(true);
		assertEquals((short)1, sut.getFloatValue(), 0);
		sut.setValue((byte)1);
		assertEquals(1F, sut.getFloatValue(), 0);
		sut.setValue((short)1);
		assertEquals(1F, sut.getFloatValue(), 0);
		sut.setValue(1);
		assertEquals(1F, sut.getFloatValue(), 0);
		sut.setValue(1L);
		assertEquals(1F, sut.getFloatValue(), 0);
		sut.setValue(1.1F);
		assertEquals(1.1F, sut.getFloatValue(), 0);
		sut.setValue(1.1D);
		assertEquals(1.1F, sut.getFloatValue(), 0);
		sut.setValue("1.1");
		assertEquals(1.1F, sut.getFloatValue(), 0);
	}

	@Test
	public void testSetValueDouble() {
		Primitive sut = new Primitive(double.class);
		assertEquals(Primitive.DOUBLE, sut.getTypeId());
		sut.setValue(true);
		assertEquals((short)1, sut.getDoubleValue(), 0);
		sut.setValue((byte)1);
		assertEquals(1D, sut.getDoubleValue(), 0);
		sut.setValue((short)1);
		assertEquals(1D, sut.getDoubleValue(), 0);
		sut.setValue(1);
		assertEquals(1D, sut.getDoubleValue(), 0);
		sut.setValue(1L);
		assertEquals(1D, sut.getDoubleValue(), 0);
		sut.setValue(1.1F);
		assertEquals(1.1D, sut.getDoubleValue(), 0.01);
		sut.setValue(1.1D);
		assertEquals(1.1D, sut.getDoubleValue(), 0);
		sut.setValue("1.1");
		assertEquals(1.1D, sut.getDoubleValue(), 0);
	}

	@Test
	public void testSetValueString() {
		Primitive sut = new Primitive(String.class);
		assertEquals(Primitive.STRING, sut.getTypeId());
		sut.setValue(true);
		assertEquals("true", sut.getValue());
		String expected = "1";
		sut.setValue((byte)1);
		assertEquals(expected, sut.getValue());
		sut.setValue((short)1);
		assertEquals(expected, sut.getValue());
		sut.setValue(1);
		assertEquals(expected, sut.getValue());
		sut.setValue(1L);
		assertEquals(expected, sut.getValue());
		sut.setValue(1F);
		assertEquals("1.0", sut.getValue());
		sut.setValue(1D);
		assertEquals("1.0", sut.getValue());
		sut.setValue("string");
		assertEquals("string", sut.getValue());
	}

	@Test
	public void testSetValueBufferedReader() {
		BufferedReader bufferedReader = new BufferedReader(new StringReader("test"));
		Primitive sut = new Primitive(BufferedReader.class);
		assertEquals(Primitive.FILE_READER, sut.getTypeId());
		sut.setValue(bufferedReader);
		assertEquals(bufferedReader, sut.getValue());
	}

	@Test
	public void testSetValueBufferedWriter() {
		BufferedWriter bufferedWriter = new BufferedWriter(new StringWriter());
		Primitive sut = new Primitive(BufferedWriter.class);
		assertEquals(Primitive.FILE_WRITER, sut.getTypeId());
		sut.setValue(bufferedWriter);
		assertEquals(bufferedWriter, sut.getValue());
	}

	@Test
	public void testSetValueObject() {
		// boolean
		Primitive sut = new Primitive(boolean.class);
		sut.setValue(new Boolean(true));
		assertEquals(true, sut.getBooleanValue());
		sut = new Primitive(boolean.class);
		sut.setValue(new Character('t'));
		assertEquals(true, sut.getBooleanValue());
		sut = new Primitive(boolean.class);
		sut.setValue(new Short((short)1));
		assertEquals(true, sut.getBooleanValue());
		sut = new Primitive(boolean.class);
		sut.setValue(new Integer(1));
		assertEquals(true, sut.getBooleanValue());
		sut = new Primitive(boolean.class);
		sut.setValue(new Long(1L));
		assertEquals(true, sut.getBooleanValue());
		sut = new Primitive(boolean.class);
		sut.setValue(new Float(1f));
		assertEquals(true, sut.getBooleanValue());
		sut = new Primitive(boolean.class);
		sut.setValue(new Double(1d));
		assertEquals(true, sut.getBooleanValue());
		sut = new Primitive(boolean.class);
		sut.setValue("true");
		assertEquals(true, sut.getBooleanValue());
		
		// char
		sut = new Primitive(char.class);
		sut.setValue(new Boolean(true));
		assertEquals('1', sut.getCharacterValue());
		sut = new Primitive(char.class);
		sut.setValue(new Character('c'));
		assertEquals('c', sut.getCharacterValue());
		sut = new Primitive(char.class);
		sut.setValue(new Short((short)65));
		assertEquals('A', sut.getCharacterValue());
		sut = new Primitive(char.class);
		sut.setValue(new Integer(65));
		assertEquals('A', sut.getCharacterValue());
		sut = new Primitive(char.class);
		sut.setValue(new Long(65L));
		assertEquals('A', sut.getCharacterValue());
		sut = new Primitive(char.class);
		sut.setValue(new Float(65.1F));
		assertEquals('A', sut.getCharacterValue());
		sut = new Primitive(char.class);
		sut.setValue(new Double(65.1D));
		assertEquals('A', sut.getCharacterValue());
		sut = new Primitive(char.class);
		sut.setValue("c");
		assertEquals('c', sut.getCharacterValue());
		
		// byte
		byte b = 1;
		sut = new Primitive(byte.class);
		sut.setValue(new Boolean(true));
		assertEquals(b, sut.getByteValue());
		b = "b".getBytes()[0];
		sut = new Primitive(byte.class);
		sut.setValue(new Character('b'));
		assertEquals(b, sut.getByteValue());
		b = 1;
		sut = new Primitive(byte.class);
		sut.setValue(new Short((short)1));
		assertEquals(b, sut.getByteValue());
		sut = new Primitive(byte.class);
		sut.setValue(new Integer(1));
		assertEquals(b, sut.getByteValue());
		sut = new Primitive(byte.class);
		sut.setValue(new Long(1L));
		assertEquals(b, sut.getByteValue());
		sut = new Primitive(byte.class);
		sut.setValue(new Float(1.1F));
		assertEquals(b, sut.getByteValue());
		sut = new Primitive(byte.class);
		sut.setValue(new Double(1.1D));
		assertEquals(b, sut.getByteValue());
		b = 12;
		sut = new Primitive(byte.class);
		sut.setValue("12");
		assertEquals(b, sut.getByteValue());
		
		// short
		short s = 1;
		sut = new Primitive(short.class);
		sut.setValue(new Boolean(true));
		assertEquals(s, sut.getShortValue());
		s = 65;
		sut = new Primitive(short.class);
		sut.setValue(new Character('A'));
		assertEquals(s, sut.getShortValue());
		s = 1;
		sut = new Primitive(short.class);
		sut.setValue(new Short((short)1));
		assertEquals(s, sut.getShortValue());
		sut = new Primitive(short.class);
		sut.setValue(new Integer(1));
		assertEquals(s, sut.getShortValue());
		sut = new Primitive(short.class);
		sut.setValue(new Long(1L));
		assertEquals(s, sut.getShortValue());
		sut = new Primitive(short.class);
		sut.setValue(new Float(1.1F));
		assertEquals(s, sut.getShortValue());
		sut = new Primitive(short.class);
		sut.setValue(new Double(1.1D));
		assertEquals(s, sut.getShortValue());
		sut = new Primitive(short.class);
		sut.setValue("1");
		assertEquals(s, sut.getShortValue());

		// int
		int i = 1;
		sut = new Primitive(int.class);
		sut.setValue(new Boolean(true));
		assertEquals(i, sut.getIntegerValue());
		i = 65;
		sut = new Primitive(int.class);
		sut.setValue(new Character('A'));
		assertEquals(i, sut.getIntegerValue());
		i = 1;
		sut = new Primitive(int.class);
		sut.setValue(new Short((short)1));
		assertEquals(i, sut.getIntegerValue());
		sut = new Primitive(int.class);
		sut.setValue(new Integer(1));
		assertEquals(i, sut.getIntegerValue());
		sut = new Primitive(int.class);
		sut.setValue(new Long(1L));
		assertEquals(i, sut.getIntegerValue());
		sut = new Primitive(int.class);
		sut.setValue(new Float(1.1F));
		assertEquals(i, sut.getIntegerValue());
		sut = new Primitive(int.class);
		sut.setValue(new Double(1.1D));
		assertEquals(i, sut.getIntegerValue());
		sut = new Primitive(int.class);
		sut.setValue("1");
		assertEquals(i, sut.getIntegerValue());

		// long
		long l = 1;
		sut = new Primitive(long.class);
		sut.setValue(new Boolean(true));
		assertEquals(l, sut.getIntegerValue());
		l = 65;
		sut = new Primitive(long.class);
		sut.setValue(new Character('A'));
		assertEquals(l, sut.getIntegerValue());
		l = 1;
		sut = new Primitive(long.class);
		sut.setValue(new Short((short)1));
		assertEquals(l, sut.getIntegerValue());
		sut = new Primitive(long.class);
		sut.setValue(new Integer(1));
		assertEquals(l, sut.getIntegerValue());
		sut = new Primitive(long.class);
		sut.setValue(new Long(1L));
		assertEquals(l, sut.getIntegerValue());
		sut = new Primitive(long.class);
		sut.setValue(new Float(1.1F));
		assertEquals(l, sut.getIntegerValue());
		sut = new Primitive(long.class);
		sut.setValue(new Double(1.1D));
		assertEquals(l, sut.getIntegerValue());
		sut = new Primitive(long.class);
		sut.setValue("1");
		assertEquals(l, sut.getIntegerValue());

		// float
		float f = 1f;
		sut = new Primitive(float.class);
		sut.setValue(new Boolean(true));
		assertEquals(f, sut.getFloatValue(), 0);
		f = 65f;
		sut = new Primitive(float.class);
		sut.setValue(new Character('A'));
		assertEquals(f, sut.getFloatValue(), 0);
		f = 1f;
		sut = new Primitive(float.class);
		sut.setValue(new Short((short)1));
		assertEquals(f, sut.getFloatValue(), 0);
		sut = new Primitive(float.class);
		sut.setValue(new Integer(1));
		assertEquals(f, sut.getFloatValue(), 0);
		sut = new Primitive(float.class);
		sut.setValue(new Long(1L));
		assertEquals(f, sut.getFloatValue(), 0);
		f = 1.1f;
		sut = new Primitive(float.class);
		sut.setValue(new Float(1.1F));
		assertEquals(f, sut.getFloatValue(), 0);
		sut = new Primitive(float.class);
		sut.setValue(new Double(1.1D));
		assertEquals(f, sut.getFloatValue(), 0);
		sut = new Primitive(float.class);
		sut.setValue("1.1");
		assertEquals(f, sut.getFloatValue(), 0);

		// double
		double d = 1d;
		sut = new Primitive(float.class);
		sut.setValue(new Boolean(true));
		assertEquals(d, sut.getDoubleValue(), 0);
		d = 65d;
		sut = new Primitive(float.class);
		sut.setValue(new Character('A'));
		assertEquals(d, sut.getDoubleValue(), 0);
		d = 1d;
		sut = new Primitive(float.class);
		sut.setValue(new Short((short)1));
		assertEquals(d, sut.getDoubleValue(), 0);
		sut = new Primitive(float.class);
		sut.setValue(new Integer(1));
		assertEquals(d, sut.getDoubleValue(), 0);
		sut = new Primitive(float.class);
		sut.setValue(new Long(1L));
		assertEquals(d, sut.getDoubleValue(), 0);
		d = 1.1d;
		sut = new Primitive(float.class);
		sut.setValue(new Float(1.1F));
		assertEquals(d, sut.getDoubleValue(), 0.01);
		sut = new Primitive(float.class);
		sut.setValue(new Double(1.1D));
		assertEquals(d, sut.getDoubleValue(), 0.01);
		sut = new Primitive(float.class);
		sut.setValue("1.1");
		assertEquals(d, sut.getDoubleValue(), 0.01);

		// string
		String str = "true";
		sut = new Primitive(String.class);
		sut.setValue(new Boolean(true));
		assertEquals(str, sut.getValue());
		str = "c";
		sut = new Primitive(String.class);
		sut.setValue(new Character('c'));
		assertEquals(str, sut.getValue());
		str = "1";
		sut = new Primitive(String.class);
		sut.setValue(new Short((short)1));
		assertEquals(str, sut.getValue());
		sut = new Primitive(String.class);
		sut.setValue(new Integer(1));
		assertEquals(str, sut.getValue());
		sut = new Primitive(String.class);
		sut.setValue(new Long(1L));
		assertEquals(str, sut.getValue());
		str = "1.1";
		sut = new Primitive(String.class);
		sut.setValue(new Float(1.1F));
		assertEquals(str, sut.getValue());
		sut = new Primitive(String.class);
		sut.setValue(new Double(1.1D));
		assertEquals(str, sut.getValue());
		sut = new Primitive(String.class);
		str = "text";
		sut.setValue("text");
		assertEquals(str, sut.getValue());

		BufferedReader bufferedReader = new BufferedReader(new StringReader("test"));
		sut = new Primitive(BufferedReader.class);
		sut.setValue(bufferedReader);
		assertEquals(bufferedReader, sut.getValue());
		
		BufferedWriter bufferedWriter = new BufferedWriter(new StringWriter());
		sut = new Primitive(BufferedWriter.class);
		sut.setValue(bufferedWriter);
		assertEquals(bufferedWriter, sut.getValue());
	}

	@Test
	public void testResolveGetMethodName() {
		Primitive sut = new Primitive(boolean.class);
		assertEquals("getBooleanValue", sut.resolveGetMethodName());
		sut = new Primitive(char.class);
		assertEquals("getCharacterValue", sut.resolveGetMethodName());
		sut = new Primitive(byte.class);
		assertEquals("getByteValue", sut.resolveGetMethodName());
		sut = new Primitive(short.class);
		assertEquals("getShortValue", sut.resolveGetMethodName());
		sut = new Primitive(int.class);
		assertEquals("getIntegerValue", sut.resolveGetMethodName());
		sut = new Primitive(long.class);
		assertEquals("getLongValue", sut.resolveGetMethodName());
		sut = new Primitive(float.class);
		assertEquals("getFloatValue", sut.resolveGetMethodName());
		sut = new Primitive(double.class);
		assertEquals("getDoubleValue", sut.resolveGetMethodName());
		sut = new Primitive(String.class);
		assertEquals("getValue", sut.resolveGetMethodName());
		sut = new Primitive(BufferedReader.class);
		assertEquals("getValue", sut.resolveGetMethodName());
		sut = new Primitive(BufferedWriter.class);
		assertEquals("getValue", sut.resolveGetMethodName());
	}

	@Test
	public void testGetClassClass() {
		assertEquals(byte.class, Primitive.getClass(Byte.class));
		assertEquals(char.class, Primitive.getClass(Character.class));
		assertEquals(short.class, Primitive.getClass(Short.class));
		assertEquals(int.class, Primitive.getClass(Integer.class));
		assertEquals(long.class, Primitive.getClass(Long.class));
		assertEquals(float.class, Primitive.getClass(Float.class));
		assertEquals(double.class, Primitive.getClass(Double.class));
		assertEquals(String.class, Primitive.getClass(String.class));
		assertEquals(BufferedReader.class, Primitive.getClass(BufferedReader.class));
		assertEquals(BufferedWriter.class, Primitive.getClass(BufferedWriter.class));
	}

	@Test
	public void testCreateValueObjectArray() {
		Boolean[] bools = {true, true, false};
		Primitive[] sut = Primitive.createValue(bools);
		assertNotNull(sut);
		Object[] valueArray = Primitive.getValueArray(sut);
		assertArrayEquals(bools, valueArray);

		byte b = 1, b1 = 2, b2 = 3;
		Byte[] bytes = {b, b1, b2};
		sut = Primitive.createValue(bytes);
		assertNotNull(sut);
		valueArray = Primitive.getValueArray(sut);
		assertArrayEquals(bytes, valueArray);

		Character[] chars = {'a', 'b', 'c'};
		sut = Primitive.createValue(chars);
		assertNotNull(sut);
		valueArray = Primitive.getValueArray(sut);
		assertArrayEquals(chars, valueArray);

		Short[] shorts = {1, 2, 3};
		sut = Primitive.createValue(shorts);
		assertNotNull(sut);
		valueArray = Primitive.getValueArray(sut);
		assertArrayEquals(shorts, valueArray);

		Integer[] ints = {1, 2, 3};
		sut = Primitive.createValue(ints);
		assertNotNull(sut);
		valueArray = Primitive.getValueArray(sut);
		assertArrayEquals(ints, valueArray);

		Long[] longs = {1L, 2L, 3L};
		sut = Primitive.createValue(longs);
		assertNotNull(sut);
		valueArray = Primitive.getValueArray(sut);
		assertArrayEquals(longs, valueArray);

		Float[] floats = {1F, 2F, 3F};
		sut = Primitive.createValue(floats);
		assertNotNull(sut);
		valueArray = Primitive.getValueArray(sut);
		assertArrayEquals(floats, valueArray);

		Double[] doubles = {1D, 2D, 3D};
		sut = Primitive.createValue(doubles);
		assertNotNull(sut);
		valueArray = Primitive.getValueArray(sut);
		assertArrayEquals(doubles, valueArray);

		String[] strings = {"A", "B", "C"};
		sut = Primitive.createValue(strings);
		assertNotNull(sut);
		valueArray = Primitive.getValueArray(sut);
		assertArrayEquals(strings, valueArray);

		BufferedReader[] readers = {new BufferedReader(new StringReader("test")),
				new BufferedReader(new StringReader("test1")), new BufferedReader(new StringReader("test2"))};
		sut = Primitive.createValue(readers);
		assertNotNull(sut);
		valueArray = Primitive.getValueArray(sut);
		assertArrayEquals(readers, valueArray);

		BufferedWriter[] writers = {new BufferedWriter(new StringWriter()),
				new BufferedWriter(new StringWriter()), new BufferedWriter(new StringWriter())};
		sut = Primitive.createValue(writers);
		assertNotNull(sut);
		valueArray = Primitive.getValueArray(sut);
		assertArrayEquals(writers, valueArray);
	}

	@Test
	public void testCreateValueObject() {
		Primitive sut = Primitive.createValue(true);
		assertEquals(new Boolean(true), sut.getValue());
		sut = Primitive.createValue('c');
		assertEquals(new Character('c'), sut.getValue());
		byte b = "b".getBytes()[0];
		sut = Primitive.createValue(b);
		assertEquals(new Byte(b), sut.getValue());
		short s = (short)1;
		sut = Primitive.createValue(s);
		assertEquals(new Short(s), sut.getValue());
		sut = Primitive.createValue(1);
		assertEquals(new Integer(1), sut.getValue());
		sut = Primitive.createValue(1L);
		assertEquals(new Long(1L), sut.getValue());
		sut = Primitive.createValue((float)1.1);
		assertEquals(new Float((float)1.1), (Float)sut.getValue(), 0);
		sut = Primitive.createValue((double)1.1);
		assertEquals(new Double((double)1.1), (Double)sut.getValue(), 0);
		sut = Primitive.createValue("string");
		assertEquals("string", sut.getValue());
		BufferedReader bufferedReader = new BufferedReader(new StringReader("test"));
		sut = Primitive.createValue(bufferedReader);
		assertEquals(bufferedReader, sut.getValue());
		BufferedWriter bufferedWriter = new BufferedWriter(new StringWriter());
		sut = Primitive.createValue(bufferedWriter);
		assertEquals(bufferedWriter, sut.getValue());
	}

	@Test
	public void testToString() {
		Primitive sut = Primitive.createValue(true);
		assertEquals(String.valueOf(true), sut.toString());
	}

	@Test
	public void testHashCode() {
		Primitive sut = Primitive.createValue(true);
		assertEquals(Boolean.hashCode(true), sut.hashCode());
	}

	@Test
	public void testEqualsObject() {
		Primitive sut = Primitive.createValue(true);
		assertTrue(sut.equalsObject(Primitive.createValue(true)).getBooleanValue());
		sut = Primitive.createValue('1');
		assertTrue(sut.equalsObject(Primitive.createValue('1')).getBooleanValue());
		sut = Primitive.createValue((short)1);
		assertTrue(sut.equalsObject(Primitive.createValue((short)1)).getBooleanValue());
		sut = Primitive.createValue(1);
		assertTrue(sut.equalsObject(Primitive.createValue(1)).getBooleanValue());
		sut = Primitive.createValue(1L);
		assertTrue(sut.equalsObject(Primitive.createValue(1L)).getBooleanValue());
		sut = Primitive.createValue(1.1F);
		assertTrue(sut.equalsObject(Primitive.createValue(1.1F)).getBooleanValue());
		sut = Primitive.createValue(1.1D);
		assertTrue(sut.equalsObject(Primitive.createValue(1.1D)).getBooleanValue());
		sut = Primitive.createValue("test");
		assertTrue(sut.equalsObject(Primitive.createValue("test")).getBooleanValue());
	}

	@Test
	public void testNotEquals() {
		Primitive sut = Primitive.createValue(true);
		assertTrue(sut.notEquals(Primitive.createValue(false)).getBooleanValue());
	}

	@Test
	public void testBitwiseAnd() {
		Primitive sut = Primitive.createValue(true);
		assertEquals(Primitive.createValue(false), sut.bitwiseAnd(Primitive.createValue(false)));
		sut = Primitive.createValue('a');
		assertEquals(Primitive.createValue(96), sut.bitwiseAnd(Primitive.createValue('b')));
		sut = Primitive.createValue((byte)0);
		assertEquals(Primitive.createValue(0), sut.bitwiseAnd(Primitive.createValue((byte)1)));
		sut = Primitive.createValue((short)2);
		assertEquals(Primitive.createValue((short)2), sut.bitwiseAnd(Primitive.createValue((short)3)));
		sut = Primitive.createValue(2);
		assertEquals(Primitive.createValue(2), sut.bitwiseAnd(Primitive.createValue(3)));
		sut = Primitive.createValue(2L);
		assertEquals(Primitive.createValue(2L), sut.bitwiseAnd(Primitive.createValue(3L)));
		sut = Primitive.createValue(2F);
		assertEquals(Primitive.createValue(2L), sut.bitwiseAnd(Primitive.createValue(3F)));
		sut = Primitive.createValue(2D);
		assertEquals(Primitive.createValue(2L), sut.bitwiseAnd(Primitive.createValue(3D)));
	}

	@Test
	public void testBitwiseComplement() {
		Primitive sut = Primitive.createValue(true);
		assertEquals(Primitive.createValue(-2), sut.bitwiseComplement());
		sut = Primitive.createValue('a');
		assertEquals(Primitive.createValue(-98), sut.bitwiseComplement());
		sut = Primitive.createValue((byte)1);
		assertEquals(Primitive.createValue(-2), sut.bitwiseComplement());
		sut = Primitive.createValue((short)2);
		assertEquals(Primitive.createValue(-3), sut.bitwiseComplement());
		sut = Primitive.createValue(2);
		assertEquals(Primitive.createValue(-3), sut.bitwiseComplement());
		sut = Primitive.createValue(2L);
		assertEquals(Primitive.createValue(-3L), sut.bitwiseComplement());
		sut = Primitive.createValue(2F);
		assertEquals(Primitive.createValue(-3L), sut.bitwiseComplement());
		sut = Primitive.createValue(2D);
		assertEquals(Primitive.createValue(-3L), sut.bitwiseComplement());
	}

	@Test
	public void testBitwiseOr() {
		Primitive sut = Primitive.createValue(true);
		assertEquals(Primitive.createValue(true), sut.bitwiseOr(Primitive.createValue(false)));
		sut = Primitive.createValue('a');
		assertEquals(Primitive.createValue(99), sut.bitwiseOr(Primitive.createValue('b')));
		sut = Primitive.createValue((byte)0);
		assertEquals(Primitive.createValue(1), sut.bitwiseOr(Primitive.createValue((byte)1)));
		sut = Primitive.createValue((short)2);
		assertEquals(Primitive.createValue((short)3), sut.bitwiseOr(Primitive.createValue((short)3)));
		sut = Primitive.createValue(2);
		assertEquals(Primitive.createValue(3), sut.bitwiseOr(Primitive.createValue(3)));
		sut = Primitive.createValue(2L);
		assertEquals(Primitive.createValue(3L), sut.bitwiseOr(Primitive.createValue(3L)));
		sut = Primitive.createValue(2F);
		assertEquals(Primitive.createValue(3L), sut.bitwiseOr(Primitive.createValue(3F)));
		sut = Primitive.createValue(2D);
		assertEquals(Primitive.createValue(3L), sut.bitwiseOr(Primitive.createValue(3D)));
	}

	@Test
	public void testBitwiseXor() {
		Primitive sut = Primitive.createValue(true);
		assertEquals(Primitive.createValue(true), sut.bitwiseXor(Primitive.createValue(false)));
		sut = Primitive.createValue('a');
		assertEquals(Primitive.createValue(3), sut.bitwiseXor(Primitive.createValue('b')));
		sut = Primitive.createValue((byte)0);
		assertEquals(Primitive.createValue(1), sut.bitwiseXor(Primitive.createValue((byte)1)));
		sut = Primitive.createValue((short)2);
		assertEquals(Primitive.createValue((short)1), sut.bitwiseXor(Primitive.createValue((short)3)));
		sut = Primitive.createValue(2);
		assertEquals(Primitive.createValue(1), sut.bitwiseXor(Primitive.createValue(3)));
		sut = Primitive.createValue(2L);
		assertEquals(Primitive.createValue(1L), sut.bitwiseXor(Primitive.createValue(3L)));
		sut = Primitive.createValue(2F);
		assertEquals(Primitive.createValue(1L), sut.bitwiseXor(Primitive.createValue(3F)));
		sut = Primitive.createValue(2D);
		assertEquals(Primitive.createValue(1L), sut.bitwiseXor(Primitive.createValue(3D)));
	}

	@Test
	public void testDiv() {
		Primitive sut = Primitive.createValue('a');
		assertEquals(Primitive.createValue(0), sut.divide(Primitive.createValue('b')));
		sut = Primitive.createValue((byte)1);
		assertEquals(Primitive.createValue(1), sut.divide(Primitive.createValue((byte)1)));
		sut = Primitive.createValue((short)4);
		assertEquals(Primitive.createValue(2), sut.divide(Primitive.createValue((short)2)));
		sut = Primitive.createValue(4);
		assertEquals(Primitive.createValue(2), sut.divide(Primitive.createValue(2)));
		sut = Primitive.createValue(4L);
		assertEquals(Primitive.createValue(2L), sut.divide(Primitive.createValue(2L)));
		sut = Primitive.createValue(4F);
		assertEquals(Primitive.createValue(2F), sut.divide(Primitive.createValue(2F)));
		sut = Primitive.createValue(4D);
		assertEquals(Primitive.createValue(2D), sut.divide(Primitive.createValue(2D)));
	}

	@Test
	public void testGreaterEqual() {
		Primitive sut = Primitive.createValue('1');
		assertTrue(sut.greaterEqual(Primitive.createValue('1')).getBooleanValue());
		assertTrue(sut.greaterEqual(Primitive.createValue('0')).getBooleanValue());
		sut = Primitive.createValue((short)1);
		assertTrue(sut.greaterEqual(Primitive.createValue((short)1)).getBooleanValue());
		assertTrue(sut.greaterEqual(Primitive.createValue((short)0)).getBooleanValue());
		sut = Primitive.createValue(1);
		assertTrue(sut.greaterEqual(Primitive.createValue(1)).getBooleanValue());
		assertTrue(sut.greaterEqual(Primitive.createValue(0)).getBooleanValue());
		sut = Primitive.createValue(1L);
		assertTrue(sut.greaterEqual(Primitive.createValue(1L)).getBooleanValue());
		assertTrue(sut.greaterEqual(Primitive.createValue(0L)).getBooleanValue());
		sut = Primitive.createValue(1.1F);
		assertTrue(sut.greaterEqual(Primitive.createValue(1.1F)).getBooleanValue());
		assertTrue(sut.greaterEqual(Primitive.createValue(0.1F)).getBooleanValue());
		sut = Primitive.createValue(1.1D);
		assertTrue(sut.greaterEqual(Primitive.createValue(1.1D)).getBooleanValue());
		assertTrue(sut.greaterEqual(Primitive.createValue(0.1D)).getBooleanValue());
		sut = Primitive.createValue("test");
		assertTrue(sut.greaterEqual(Primitive.createValue("test")).getBooleanValue());
		assertTrue(sut.greaterEqual(Primitive.createValue("rest")).getBooleanValue());
	}

	@Test
	public void testGreaterThan() {
		Primitive sut = Primitive.createValue('1');
		assertTrue(sut.greaterThan(Primitive.createValue('0')).getBooleanValue());
		sut = Primitive.createValue((short)1);
		assertTrue(sut.greaterThan(Primitive.createValue((short)0)).getBooleanValue());
		sut = Primitive.createValue(1);
		assertTrue(sut.greaterThan(Primitive.createValue(0)).getBooleanValue());
		sut = Primitive.createValue(1L);
		assertTrue(sut.greaterThan(Primitive.createValue(0L)).getBooleanValue());
		sut = Primitive.createValue(1.1F);
		assertTrue(sut.greaterThan(Primitive.createValue(0.1F)).getBooleanValue());
		sut = Primitive.createValue(1.1D);
		assertTrue(sut.greaterThan(Primitive.createValue(0.1D)).getBooleanValue());
		sut = Primitive.createValue("test");
		assertTrue(sut.greaterThan(Primitive.createValue("rest")).getBooleanValue());
	}

	@Test
	public void testLesserEqual() {
		Primitive sut = Primitive.createValue('1');
		assertTrue(sut.lesserEqual(Primitive.createValue('1')).getBooleanValue());
		assertTrue(sut.lesserEqual(Primitive.createValue('2')).getBooleanValue());
		sut = Primitive.createValue((short)1);
		assertTrue(sut.lesserEqual(Primitive.createValue((short)1)).getBooleanValue());
		assertTrue(sut.lesserEqual(Primitive.createValue((short)2)).getBooleanValue());
		sut = Primitive.createValue(1);
		assertTrue(sut.lesserEqual(Primitive.createValue(1)).getBooleanValue());
		assertTrue(sut.lesserEqual(Primitive.createValue(2)).getBooleanValue());
		sut = Primitive.createValue(1L);
		assertTrue(sut.lesserEqual(Primitive.createValue(1L)).getBooleanValue());
		assertTrue(sut.lesserEqual(Primitive.createValue(2L)).getBooleanValue());
		sut = Primitive.createValue(1.1F);
		assertTrue(sut.lesserEqual(Primitive.createValue(1.1F)).getBooleanValue());
		assertTrue(sut.lesserEqual(Primitive.createValue(2.1F)).getBooleanValue());
		sut = Primitive.createValue(1.1D);
		assertTrue(sut.lesserEqual(Primitive.createValue(1.1D)).getBooleanValue());
		assertTrue(sut.lesserEqual(Primitive.createValue(2.1D)).getBooleanValue());
		sut = Primitive.createValue("test");
		assertTrue(sut.lesserEqual(Primitive.createValue("test")).getBooleanValue());
		assertTrue(sut.lesserEqual(Primitive.createValue("west")).getBooleanValue());
	}

	@Test
	public void testLesserThan() {
		Primitive sut = Primitive.createValue('1');
		assertTrue(sut.lesserThan(Primitive.createValue('2')).getBooleanValue());
		sut = Primitive.createValue((short)1);
		assertTrue(sut.lesserThan(Primitive.createValue((short)2)).getBooleanValue());
		sut = Primitive.createValue(1);
		assertTrue(sut.lesserThan(Primitive.createValue(2)).getBooleanValue());
		sut = Primitive.createValue(1L);
		assertTrue(sut.lesserThan(Primitive.createValue(2L)).getBooleanValue());
		sut = Primitive.createValue(1.1F);
		assertTrue(sut.lesserThan(Primitive.createValue(2.1F)).getBooleanValue());
		sut = Primitive.createValue(1.1D);
		assertTrue(sut.lesserThan(Primitive.createValue(2.1D)).getBooleanValue());
		sut = Primitive.createValue("test");
		assertTrue(sut.lesserThan(Primitive.createValue("west")).getBooleanValue());
	}

	@Test
	public void testMod() {
		Primitive sut = Primitive.createValue('a');
		assertEquals(Primitive.createValue(0), sut.mod(Primitive.createValue('a')));
		sut = Primitive.createValue((byte)4);
		assertEquals(Primitive.createValue(0), sut.mod(Primitive.createValue((byte)2)));
		sut = Primitive.createValue((short)4);
		assertEquals(Primitive.createValue(0), sut.mod(Primitive.createValue((short)2)));
		sut = Primitive.createValue(4);
		assertEquals(Primitive.createValue(0), sut.mod(Primitive.createValue(2)));
		sut = Primitive.createValue(4L);
		assertEquals(Primitive.createValue(0L), sut.mod(Primitive.createValue(2L)));
		sut = Primitive.createValue(4F);
		assertEquals(Primitive.createValue(0F), sut.mod(Primitive.createValue(2F)));
		sut = Primitive.createValue(4D);
		assertEquals(Primitive.createValue(0D), sut.mod(Primitive.createValue(2D)));
	}

	@Test
	public void testMult() {
		Primitive sut = Primitive.createValue('a');
		assertEquals(Primitive.createValue(9506), sut.multiplicate(Primitive.createValue('b')));
		sut = Primitive.createValue((byte)2);
		assertEquals(Primitive.createValue(4), sut.multiplicate(Primitive.createValue((byte)2)));
		sut = Primitive.createValue((short)4);
		assertEquals(Primitive.createValue(8), sut.multiplicate(Primitive.createValue((short)2)));
		sut = Primitive.createValue(4);
		assertEquals(Primitive.createValue(8), sut.multiplicate(Primitive.createValue(2)));
		sut = Primitive.createValue(4L);
		assertEquals(Primitive.createValue(8L), sut.multiplicate(Primitive.createValue(2L)));
		sut = Primitive.createValue(4F);
		assertEquals(Primitive.createValue(8F), sut.multiplicate(Primitive.createValue(2F)));
		sut = Primitive.createValue(4D);
		assertEquals(Primitive.createValue(8D), sut.multiplicate(Primitive.createValue(2D)));
	}

	@Test
	public void testSubstract() {
		Primitive sut = Primitive.createValue('a');
		assertEquals(Primitive.createValue(-1), sut.substract(Primitive.createValue('b')));
		sut = Primitive.createValue((byte)4);
		assertEquals(Primitive.createValue(2), sut.substract(Primitive.createValue((byte)2)));
		sut = Primitive.createValue((short)4);
		assertEquals(Primitive.createValue(2), sut.substract(Primitive.createValue((short)2)));
		sut = Primitive.createValue(4);
		assertEquals(Primitive.createValue(2), sut.substract(Primitive.createValue(2)));
		sut = Primitive.createValue(4L);
		assertEquals(Primitive.createValue(2L), sut.substract(Primitive.createValue(2L)));
		sut = Primitive.createValue(4F);
		assertEquals(Primitive.createValue(2F), sut.substract(Primitive.createValue(2F)));
		sut = Primitive.createValue(4D);
		assertEquals(Primitive.createValue(2D), sut.substract(Primitive.createValue(2D)));
	}

	@Test
	public void testAdd() {
		Primitive sut = Primitive.createValue('a');
		assertEquals(Primitive.createValue(195), sut.add(Primitive.createValue('b')));
		sut = Primitive.createValue((byte)4);
		assertEquals(Primitive.createValue(6), sut.add(Primitive.createValue((byte)2)));
		sut = Primitive.createValue((short)4);
		assertEquals(Primitive.createValue(6), sut.add(Primitive.createValue((short)2)));
		sut = Primitive.createValue(4);
		assertEquals(Primitive.createValue(6), sut.add(Primitive.createValue(2)));
		sut = Primitive.createValue(4L);
		assertEquals(Primitive.createValue(6L), sut.add(Primitive.createValue(2L)));
		sut = Primitive.createValue(4F);
		assertEquals(Primitive.createValue(6F), sut.add(Primitive.createValue(2F)));
		sut = Primitive.createValue(4D);
		assertEquals(Primitive.createValue(6D), sut.add(Primitive.createValue(2D)));
	}

	@Test
	public void testIncrement() {
		Primitive sut = Primitive.createValue('a');
		sut.increment();
		assertEquals(Primitive.createValue('b'), sut);
		sut = Primitive.createValue((byte)1);
		sut.increment();
		assertEquals(Primitive.createValue(2), sut);
		sut = Primitive.createValue((short)2);
		sut.increment();
		assertEquals(Primitive.createValue(3), sut);
		sut = Primitive.createValue(2);
		sut.increment();
		assertEquals(Primitive.createValue(3), sut);
		sut = Primitive.createValue(2L);
		sut.increment();
		assertEquals(Primitive.createValue(3L), sut);
		sut = Primitive.createValue(2F);
		sut.increment();
		assertEquals(Primitive.createValue(3L), sut);
		sut = Primitive.createValue(2D);
		sut.increment();
		assertEquals(Primitive.createValue(3L), sut);
	}

	@Test
	public void testDecrement() {
		Primitive sut = Primitive.createValue('b');
		sut.decrement();
		assertEquals(Primitive.createValue('a'), sut);
		sut = Primitive.createValue((byte)2);
		sut.decrement();
		assertEquals(Primitive.createValue(1), sut);
		sut = Primitive.createValue((short)2);
		sut.decrement();
		assertEquals(Primitive.createValue(1), sut);
		sut = Primitive.createValue(2);
		sut.decrement();
		assertEquals(Primitive.createValue(1), sut);
		sut = Primitive.createValue(2L);
		sut.decrement();
		assertEquals(Primitive.createValue(1L), sut);
		sut = Primitive.createValue(2F);
		sut.decrement();
		assertEquals(Primitive.createValue(1L), sut);
		sut = Primitive.createValue(2D);
		sut.decrement();
		assertEquals(Primitive.createValue(1L), sut);
	}

}
