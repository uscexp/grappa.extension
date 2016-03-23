package com.github.uscexp.grappa.extension.codegenerator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Test;
import org.parboiled.BaseParser;
import org.parboiled.Parboiled;

import com.github.uscexp.grappa.extension.annotations.AstCommand;
import com.github.uscexp.grappa.extension.codegenerator.parser.GeneratorTestCalculatorParser;
import com.github.uscexp.grappa.extension.interpreter.AstInterpreter;
import com.github.uscexp.grappa.extension.interpreter.MethodNameToTreeNodeInfoMaps;

public class AstModelGeneratorTest {

	String sourceOutputPath;
	File file;

	@After
	public void teardown() {
		deleteFilesAndDirs(sourceOutputPath);
	}
	
	@Test
	public void testGenerateAstModelString() throws Exception {
		AstModelGenerator astModelGenerator = new AstModelGenerator();
		
		sourceOutputPath = "target";
		astModelGenerator.generateAstModel(GeneratorTestCalculatorParser.class.getName(), sourceOutputPath);
		
		Map<String, Long> timestamps = new HashMap<>();
		assertFilesExists(sourceOutputPath, timestamps);
		
	}

	@Test
	public void testGenerateAstModelStringFilesAlreadyExist() throws Exception {
		AstModelGenerator astModelGenerator = new AstModelGenerator();
		
		sourceOutputPath = ".";
		astModelGenerator.generateAstModel(GeneratorTestCalculatorParser.class.getName(), null);
		
		Map<String, Long> timestamps = new HashMap<>();
		assertFilesExists(sourceOutputPath, timestamps);
		astModelGenerator.generateAstModel(GeneratorTestCalculatorParser.class.getName(), null);
		
		assertFilesExists(sourceOutputPath, timestamps);
		
	}

	public void deleteFilesAndDirs(String sourceOutputPath) {
		file = file.getParentFile();
		
		File[] listFiles = file.listFiles();
		
		for (int i = 0; i < listFiles.length; i++) {
			listFiles[i].delete();
		}
		
		while (!file.getName().equals(sourceOutputPath)) {
			file.delete();
			file = file.getParentFile();
		}
	}

	public void assertFilesExists(String sourceOutputPath, Map<String, Long> timestamps) {
		String filename = GeneratorTestCalculatorParser.class.getPackage().getName().replace('.', '/');
		filename = sourceOutputPath + "/" + filename;
		BaseParser<?> parser = Parboiled.createParser(GeneratorTestCalculatorParser.class);
		MethodNameToTreeNodeInfoMaps methodNameToTreeNodeInfoMaps = AstInterpreter.findImplementationClassesAndAnnotationTypes(parser.getClass());
		
		Set<String> methodNames = methodNameToTreeNodeInfoMaps.getMethodNames();
		
		file = null;
		for (String methodName : methodNames) {
			if(methodNameToTreeNodeInfoMaps.getAnnotationTypeForMethodName(methodName) instanceof AstCommand) {
				AstCommand astCommand = (AstCommand) methodNameToTreeNodeInfoMaps.getAnnotationTypeForMethodName(methodName);
				String label = methodName.substring(0, 1).toUpperCase() + methodName.substring(1);
				String javaFilename = "Ast" + label + "TreeNode.java";
				String classname = astCommand.classname();
				
				if(classname != null && !classname.isEmpty()) {
					javaFilename = sourceOutputPath + "/" + classname.replace('.', '/') + ".java";
				} else {
					javaFilename = filename + "/" + javaFilename;
				}
				
				file = new File(javaFilename);
				
				Long timestamp = timestamps.get(javaFilename);
				
				if(timestamp == null) {
					timestamp = file.lastModified();
					timestamps.put(javaFilename, timestamp);
				} else {
					assertEquals(timestamp, new Long(file.lastModified()));
				}
				
				assertTrue(String.format("File %s does not exist.", file.getAbsoluteFile()), file.exists());
			}
		}
	}

}
