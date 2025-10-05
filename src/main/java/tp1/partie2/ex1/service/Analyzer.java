package tp1.partie2.ex1.service;

import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.dom.CompilationUnit;
import tp1.partie2.ex1.model.*;
import tp1.partie2.ex1.parser.IParser;
import tp1.partie2.ex1.visitor.FieldDeclarationVisitor;
import tp1.partie2.ex1.visitor.MethodDeclarationVisitor;
import tp1.partie2.ex1.visitor.TypeDeclarationVisitor;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Analyzer {

    public AnalysisResult analyzeProjectSources(IParser parser, File sourceFolder) throws Exception {
        List<File> javaFiles = parser.listJavaFilesForFolder(sourceFolder);

        List<ClassInfo> allClasses = new ArrayList<>();
        Set<String> packages = new HashSet<>();
        int totalAppLOC = 0;

        for (File f : javaFiles) {
            String src = FileUtils.readFileToString(f, StandardCharsets.UTF_8);
            CompilationUnit cu = parser.parse(src.toCharArray());

            TypeDeclarationVisitor typeV = new TypeDeclarationVisitor(cu);
            cu.accept(typeV);
            List<ClassInfo> classesInFile = typeV.getClasses();
            allClasses.addAll(classesInFile);

            String pkg = cu.getPackage() == null ? "" : cu.getPackage().getName().getFullyQualifiedName();
            if (!pkg.isEmpty()) packages.add(pkg);

            FieldDeclarationVisitor fieldV = new FieldDeclarationVisitor(classesInFile);
            cu.accept(fieldV);

            MethodDeclarationVisitor methodV = new MethodDeclarationVisitor(cu, classesInFile);
            cu.accept(methodV);

            totalAppLOC += countNonEmptyLines(src);
        }

        return new AnalysisResult(allClasses, packages, totalAppLOC);
    }

    private int countNonEmptyLines(String src) {
        int count = 0;
        for (String line : src.split("\\R")) if (!line.trim().isEmpty()) count++;
        return count;
    }
}
