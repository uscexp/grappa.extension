package com.github.uscexp.grappa.extension.interpreter.type;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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
		assertEquals(1, sut.getBooleanValue());
	}

	@Test
	public void testPrimitiveClassLong() {
		Primitive sut = new Primitive(long.class, 1L);
		assertEquals(Primitive.LONG, sut.getTypeId());
		assertEquals(1L, sut.getLongValue());
	}

	@Test
	public void testPrimitiveClassFloat() {
		Primitive sut = new Primitive(float.class, 1.1);
		assertEquals(Primitive.FLOAT, sut.getTypeId());
		assertEquals(1.1, sut.getFloatValue(), 0);
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
		assertEquals(Primitive.BOOLEAN, sut.getTypeId());
		assertEquals(true, sut.getBooleanValue());
		sut = new Primitive('c');
		assertEquals(Primitive.CHARACTER, sut.getTypeId());
		assertEquals('c', sut.getCharacterValue());
		byte b = "b".getBytes()[0];
		sut = new Primitive(b);
		assertEquals(Primitive.BYTE, sut.getTypeId());
		assertEquals(b, sut.getByteValue());
		short s = (short)1;
		sut = new Primitive(s);
		assertEquals(Primitive.SHORT, sut.getTypeId());
		assertEquals(s, sut.getShortValue());
		sut = new Primitive(1);
		assertEquals(Primitive.INTEGER, sut.getTypeId());
		assertEquals(1, sut.getIntegerValue());
		sut = new Primitive(1L);
		assertEquals(Primitive.LONG, sut.getTypeId());
		assertEquals(1L, sut.getLongValue());
		sut = new Primitive((float)1.1);
		assertEquals(Primitive.FLOAT, sut.getTypeId());
		assertEquals((float)1.1, sut.getFloatValue(), 0);
		sut = new Primitive((double)1.1);
		assertEquals(Primitive.DOUBLE, sut.getTypeId());
		assertEquals((double)1.1, sut.getDoubleValue(), 0);
		sut = new Primitive("string");
		assertEquals(Primitive.STRING, sut.getTypeId());
		assertEquals("string", sut.getValue());
		BufferedReader bufferedReader = new BufferedReader(new StringReader("test"));
		sut = new Primitive(bufferedReader);
		assertEquals(Primitive.FILE_READER, sut.getTypeId());
		assertEquals(bufferedReader, sut.getValue());
		BufferedWriter bufferedWriter = new BufferedWriter(new StringWriter());
		sut = new Primitive(bufferedWriter);
		assertEquals(Primitive.FILE_WRITER, sut.getTypeId());
		assertEquals(bufferedWriter, sut.getValue());
	}

	@Test
	public void testGetClassFromType() {
		Class<?> result = Primitive.getClassFromType("boolean");
		assertEquals(boolean.class, result);
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
		fail("Not yet implemented");
	}

	@Test
	public void testGetType() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetTypeId() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPrimitiveType() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetValue() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetBooleanValue() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCharacterValue() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetByteValue() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetShortValue() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetIntegerValue() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetLongValue() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetFloatValue() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDoubleValue() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetValueBoolean() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetValueChar() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetValueByte() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetValueShort() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetValueInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetValueLong() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetValueFloat() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetValueDouble() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetValueString() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetValueBufferedReader() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetValueBufferedWriter() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetValueObject() {
		fail("Not yet implemented");
	}

	@Test
	public void testResolveGetMethodName() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetClassClass() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreateValueObjectArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreateValueObject() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetValueArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testToString() {
		fail("Not yet implemented");
	}

	@Test
	public void testEqualsObject() {
		fail("Not yet implemented");
	}

	@Test
	public void testNotEquals() {
		fail("Not yet implemented");
	}

	@Test
	public void testBitwiseAnd() {
		fail("Not yet implemented");
	}

	@Test
	public void testBitwiseCompl() {
		fail("Not yet implemented");
	}

	@Test
	public void testBitwiseOr() {
		fail("Not yet implemented");
	}

	@Test
	public void testBitwiseXor() {
		fail("Not yet implemented");
	}

	@Test
	public void testDiv() {
		fail("Not yet implemented");
	}

	@Test
	public void testGreaterEqual() {
		fail("Not yet implemented");
	}

	@Test
	public void testGreaterThan() {
		fail("Not yet implemented");
	}

	@Test
	public void testLesserEqual() {
		fail("Not yet implemented");
	}

	@Test
	public void testLesserThan() {
		fail("Not yet implemented");
	}

	@Test
	public void testMod() {
		fail("Not yet implemented");
	}

	@Test
	public void testMult() {
		fail("Not yet implemented");
	}

	@Test
	public void testSubstract() {
		fail("Not yet implemented");
	}

	@Test
	public void testAdd() {
		fail("Not yet implemented");
	}

	@Test
	public void testIncrement() {
		fail("Not yet implemented");
	}

	@Test
	public void testDecrement() {
		fail("Not yet implemented");
	}

}
