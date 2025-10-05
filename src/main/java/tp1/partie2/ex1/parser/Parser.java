package tp1.partie2.ex1.parser;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import tp1.partie2.ex1.visitor.TypeDeclarationVisitor;

public class Parser implements IParser{
	
	/** Start Copyright@author prof-UM **/
	
	@Override
	public List<File> listJavaFilesForFolder(File folder) {
		List<File> javaFiles = new ArrayList<File>();
		File[] files = folder.listFiles();
		
		if(files == null) return javaFiles;
		
		for(File file : files) {
			if(file.isDirectory()) {
				javaFiles.addAll(listJavaFilesForFolder(file));
			} else if(file.getName().endsWith(".java")) {
				javaFiles.add(file);
			}
		}
		
		return javaFiles;
	}

	@Override
	public CompilationUnit parse(char[] classSource) {
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setResolveBindings(true);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setBindingsRecovery(true);
		@SuppressWarnings("rawtypes")
		Map options = JavaCore.getOptions();
		parser.setCompilerOptions(options);
		parser.setUnitName("");
        parser.setSource(classSource);
        return (CompilationUnit) parser.createAST(null);
	}
	/** End Copyright@author prof-UM **/
}
