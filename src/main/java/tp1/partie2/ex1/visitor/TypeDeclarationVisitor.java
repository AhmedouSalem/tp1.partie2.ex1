package tp1.partie2.ex1.visitor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.ASTVisitor;

import tp1.partie2.ex1.model.ClassInfo;

public class TypeDeclarationVisitor extends ASTVisitor {
    private final CompilationUnit cu;
    private final List<ClassInfo> classes = new ArrayList<>();
    private String currentPackage = "";

    public TypeDeclarationVisitor(CompilationUnit cu) {
        this.cu = cu;
    }

    @Override
    public boolean visit(PackageDeclaration node) {
        currentPackage = node.getName().getFullyQualifiedName();
        return super.visit(node);
    }

    @Override
    public boolean visit(TypeDeclaration node) {
        ClassInfo info = new ClassInfo(currentPackage, node.getName().getIdentifier());
        info.setSourceLines(estimateLoc(node));
        classes.add(info);
        return super.visit(node);
    }

    public List<ClassInfo> getClasses() { return classes; }

    private int estimateLoc(ASTNode node) {
        int start = cu.getLineNumber(node.getStartPosition());
        int end = cu.getLineNumber(node.getStartPosition() + node.getLength());
        return Math.max(0, end - start + 1);
    }
}
