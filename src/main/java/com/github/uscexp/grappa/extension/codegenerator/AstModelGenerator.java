/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.grappa.extension.codegenerator;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.fge.grappa.parsers.BaseParser;
import com.github.uscexp.grappa.extension.annotations.AstCommand;
import com.github.uscexp.grappa.extension.interpreter.MethodNameToTreeNodeInfoMaps;
import com.github.uscexp.grappa.extension.nodes.AstCommandTreeNode;
import com.github.uscexp.grappa.extension.nodes.AstTreeNode;
import com.github.uscexp.grappa.extension.nodes.treeconstruction.AstTreeNodeBuilder;
import com.sun.codemodel.JBlock;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JDocComment;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JType;

/**
 * The {@link AstModelGenerator} creates the model classes from a {@link BaseParser} extended annotated parser class. It creates the
 * corresponding java source files for the {@link AstCommand} annotated rules in the parser class. The generated classes will extend <code>
 * AstCommandTreeNode</code> and the to be implemented methods <code>interpretBeforeChilds</code> and <code>interpretAfterChilds</code>.
 *
 * @author  haui
 */
public class AstModelGenerator {

	private static Logger logger = Logger.getLogger(AstModelGenerator.class.getName());

	public <V> void generateAstModel(String parserClassString, String sourceOutputPath)
		throws ReflectiveOperationException, IOException {
		@SuppressWarnings("unchecked")
		Class<? extends BaseParser<V>> parserClass = (Class<? extends BaseParser<V>>) Class.forName(parserClassString);

		generateAstModel(parserClass, sourceOutputPath);
	}

	public <V> void generateAstModel(Class<? extends BaseParser<V>> parserClass, String sourceOutputPath)
		throws ReflectiveOperationException, IOException {
		if (sourceOutputPath == null) {
			sourceOutputPath = ".";
		}

//		@SuppressWarnings("unchecked")
//		BaseParser<? extends BaseParser<?>> parser = (BaseParser<? extends BaseParser<?>>) Grappa.createParser(parserClass);

		MethodNameToTreeNodeInfoMaps methodNameToTreeNodeInfoMaps = AstTreeNodeBuilder.findImplementationClassesAndAnnotationTypes(
				parserClass);

		Set<String> methodNames = methodNameToTreeNodeInfoMaps.getMethodNames();

		for (String methodName : methodNames) {
			Object annotation = methodNameToTreeNodeInfoMaps.getAnnotationType(methodName);
			generateClass(parserClass, sourceOutputPath, methodNameToTreeNodeInfoMaps, methodName, annotation);
		}
	}

	private void generateClass(Class<? extends BaseParser<?>> parserClass, String sourceOutputPath,
			MethodNameToTreeNodeInfoMaps methodNameToTreeNodeInfoMaps, String methodName, Object annotation)
		throws IOException {
		JCodeModel codeModel = new JCodeModel();

		if (annotation instanceof AstCommand) {
			String classname = AstTreeNodeBuilder.getClassname(methodName, (AstCommand) annotation, methodNameToTreeNodeInfoMaps);
			try {
				createClassFile(parserClass, sourceOutputPath, methodName, codeModel, classname);
			} catch (JClassAlreadyExistsException e) {
				logger.log(Level.WARNING, String.format("Class %s already exists -> skip ...", classname));
			}
		}
	}

	private void createClassFile(Class<? extends BaseParser<?>> parserClass, String sourceOutputPath, String methodName,
			JCodeModel codeModel, String classname)
		throws JClassAlreadyExistsException, IOException {
		if (!classFileExists(sourceOutputPath, classname)) {
			JDefinedClass definedClass = codeModel._class(classname);
			String genericTypeName = "V";
			JClass genericType = codeModel.ref(genericTypeName);
			JClass superClass = codeModel.ref(AstCommandTreeNode.class).narrow(genericType);
			definedClass._extends(superClass);
			definedClass.generify(genericTypeName);
			JDocComment javadoc = definedClass.javadoc();
			javadoc.add(String.format("Command implementation for the <code>%s</code> rule: %s.", parserClass.getSimpleName(), methodName));
			addConstructors(codeModel, definedClass);
			addAbstractMethods(codeModel, definedClass);
			codeModel.build(new File(sourceOutputPath));
		}
	}

	private boolean classFileExists(String sourceOutputPath, String classname) {
		String filename = classname.replace('.', '/');
		filename = sourceOutputPath + "/" + filename + ".java";
		File file = new File(filename);
		if (file.exists()) {
			logger.log(Level.INFO, String.format("File %s already exists -> skip ...", file.getName()));
			return true;
		}
		return false;
	}

	public void addConstructors(JCodeModel codeModel, JDefinedClass definedClass) {
		Constructor<?>[] declaredConstructors = AstCommandTreeNode.class.getDeclaredConstructors();
		MethodConstructorWrapper<Constructor<?>>[] wrappers = wrapConstructors(declaredConstructors);

		addMethods(codeModel, definedClass, wrappers);
	}

	public void addAbstractMethods(JCodeModel codeModel, JDefinedClass definedClass) {
		Method[] declaredMethods = AstTreeNode.class.getDeclaredMethods();
		MethodConstructorWrapper<Method>[] wrappers = wrapMethods(declaredMethods);

		addMethods(codeModel, definedClass, wrappers);
	}

	private MethodConstructorWrapper<Method>[] wrapMethods(Method[] methods) {
		@SuppressWarnings("unchecked")
		MethodConstructorWrapper<Method>[] result = new MethodConstructorWrapper[methods.length];

		for (int i = 0; i < methods.length; i++) {
			result[i] = new MethodConstructorWrapper<Method>(methods[i]);
		}
		return result;
	}

	private MethodConstructorWrapper<Constructor<?>>[] wrapConstructors(Constructor<?>[] constructors) {
		@SuppressWarnings("unchecked")
		MethodConstructorWrapper<Constructor<?>>[] result = new MethodConstructorWrapper[constructors.length];

		for (int i = 0; i < constructors.length; i++) {
			result[i] = new MethodConstructorWrapper<Constructor<?>>(constructors[i]);
		}
		return result;
	}

	private <T> void addMethods(JCodeModel codeModel, JDefinedClass definedClass, MethodConstructorWrapper<T>[] methods) {
		for (MethodConstructorWrapper<T> method : methods) {
			if (Modifier.isAbstract(method.getModifiers()) || method.isConstructor()) {
				int modifier = JMod.PUBLIC;
				if (Modifier.isProtected(method.getModifiers())) {
					modifier = JMod.PROTECTED;
				}
				JMethod jMethod = createMethod(codeModel, definedClass, method, modifier);

				// throws declarations
				createThrowsDeclaration(method, jMethod);

				// parameters
				StringBuilder stringBuilder = addParameterAndCreateConstructorBodyText(codeModel, method, jMethod);

				addMethodBody(codeModel, method, jMethod, stringBuilder);
			}
		}
	}

	private <T> JMethod createMethod(JCodeModel codeModel, JDefinedClass definedClass, MethodConstructorWrapper<T> method, int modifier) {
		JMethod jMethod;
		if (method.isConstructor()) {
			jMethod = definedClass.constructor(modifier);
		} else {
			JType type = codeModel._ref(method.getReturnType());
			jMethod = definedClass.method(modifier, type, method.getName());
			jMethod.annotate(Override.class);
		}
		return jMethod;
	}

	private <T> void createThrowsDeclaration(MethodConstructorWrapper<T> method, JMethod jMethod) {
		@SuppressWarnings("unchecked")
		Class<? extends Throwable>[] exceptionTypes = (Class<? extends Throwable>[]) method.getExceptionTypes();

		for (Class<? extends Throwable> exeptionType : exceptionTypes) {
			jMethod._throws(exeptionType);
		}
	}

	private <T> StringBuilder addParameterAndCreateConstructorBodyText(JCodeModel codeModel, MethodConstructorWrapper<T> method,
			JMethod jMethod) {
		Class<?>[] parameterTypes = method.getParameterTypes();
		TypeVariable<?>[] typeParameters = method.getTypeParameters();

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("super(");
		for (int i = 0; i < parameterTypes.length; i++) {
			String name = getParameterName(parameterTypes, typeParameters, i);
			String parameterizedTypeString = getParameterizedType(codeModel, method, parameterTypes, i);

			if (parameterizedTypeString != null) {
				JClass genericType = codeModel.ref(parameterizedTypeString);
				JClass pType = codeModel.ref(parameterTypes[i]).narrow(genericType);
				jMethod.param(pType, name);
			} else {
				jMethod.param(parameterTypes[i], name);
			}
			if (i > 0) {
				stringBuilder.append(", ");
			}
			stringBuilder.append(name);
		}
		stringBuilder.append(");");
		return stringBuilder;
	}

	private <T> void addMethodBody(JCodeModel codeModel, MethodConstructorWrapper<T> method, JMethod jMethod, StringBuilder stringBuilder) {
		JBlock body = jMethod.body();
		if (method.isConstructor()) {
			body.directStatement(stringBuilder.toString());
		} else {
			codeModel.ref(RuntimeException.class);
			body.directStatement("throw new RuntimeException(\"not yet implemented\");");
		}
	}

	public <T> String getParameterizedType(JCodeModel codeModel, MethodConstructorWrapper<T> method, Class<?>[] parameterTypes, int i) {
		String result = null;
		Type[] genericParameterTypes = method.getGenericParameterTypes();
		if ((genericParameterTypes != null) && (genericParameterTypes.length == parameterTypes.length) &&
				(genericParameterTypes[i] instanceof ParameterizedType)) {
			ParameterizedType parameterizedType = (ParameterizedType) genericParameterTypes[i];
			result = parameterizedType.getActualTypeArguments()[0].toString();
		}
		return result;
	}

	public String getParameterName(Class<?>[] parameterTypes, TypeVariable<?>[] typeParameters, int i) {
		String name = "arg" + i;
		if ((typeParameters != null) && (typeParameters.length == parameterTypes.length)) {
			name = typeParameters[i].getName();
		}
		return name;
	}

	public static void main(String[] args) {
		try {
			AstModelGenerator astModelGenerator = new AstModelGenerator();

			String sourceOutputPath = args[1];
			astModelGenerator.generateAstModel(args[0], sourceOutputPath);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Unexpected error occured", e);

			logger.log(Level.SEVERE,
				"Syntax: java -cp <dependencies> com.github.uscexp.grappa.extension.codegenerator.AstModelGenerator pasrserClass sourceOutputPath");
		}
	}

	public class MethodConstructorWrapper<T> {

		private final T method;

		public MethodConstructorWrapper(final T method) {
			this.method = method;
		}

		public boolean isConstructor() {
			return method instanceof Constructor<?>;
		}

		public String getName() {
			if (method instanceof Method)
				return ((Method) method).getName();
			else
				return ((Constructor<?>) method).getName();
		}

		public int getModifiers() {
			if (method instanceof Method)
				return ((Method) method).getModifiers();
			else
				return ((Constructor<?>) method).getModifiers();
		}

		public TypeVariable<?>[] getTypeParameters() {
			if (method instanceof Method)
				return ((Method) method).getTypeParameters();
			else
				return ((Constructor<?>) method).getTypeParameters();
		}

		public Class<?> getReturnType() {
			if (method instanceof Method)
				return ((Method) method).getReturnType();
			else
				return null;
		}

		public Class<?>[] getParameterTypes() {
			if (method instanceof Method)
				return ((Method) method).getParameterTypes();
			else
				return ((Constructor<?>) method).getParameterTypes();
		}

		public Type[] getGenericParameterTypes() {
			if (method instanceof Method)
				return ((Method) method).getGenericParameterTypes();
			else
				return ((Constructor<?>) method).getGenericParameterTypes();
		}

		public Class<?>[] getExceptionTypes() {
			if (method instanceof Method)
				return ((Method) method).getExceptionTypes();
			else
				return ((Constructor<?>) method).getExceptionTypes();
		}

		public boolean equals(Object obj) {
			if (method instanceof Method)
				return ((Method) method).equals(obj);
			else
				return ((Constructor<?>) method).equals(obj);
		}

		public int hashCode() {
			if (method instanceof Method)
				return ((Method) method).hashCode();
			else
				return ((Constructor<?>) method).hashCode();
		}

		public String toString() {
			if (method instanceof Method)
				return ((Method) method).toString();
			else
				return ((Constructor<?>) method).toString();
		}

	}

}
