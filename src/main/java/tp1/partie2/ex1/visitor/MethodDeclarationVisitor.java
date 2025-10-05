package tp1.partie2.ex1.visitor;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import tp1.partie2.ex1.model.ClassInfo;
import tp1.partie2.ex1.model.MethodInfo;

/** Récupère méthodes (nom, nb params, LOC) classe par classe. */
public class MethodDeclarationVisitor extends ASTVisitor {
    private final CompilationUnit cu;
    private final List<ClassInfo> classes;
    private int classIndex = -1;

    public MethodDeclarationVisitor(CompilationUnit cu, List<ClassInfo> classes) {
        this.cu = cu;
        this.classes = classes;
    }

    @Override
    public boolean visit(TypeDeclaration node) {
        classIndex++;
        return super.visit(node);
    }

    @Override
    public boolean visit(MethodDeclaration node) {
        if (classIndex >= 0 && classIndex < classes.size()) {
            int start = cu.getLineNumber(node.getStartPosition());
            int end = cu.getLineNumber(node.getStartPosition() + node.getLength());
            int loc = Math.max(0, end - start + 1);
            MethodInfo mi = new MethodInfo(
                node.getName().getIdentifier(),
                node.parameters().size(),
                loc
            );
            classes.get(classIndex).getMethods().add(mi);
        }
        return super.visit(node);
    }
}
