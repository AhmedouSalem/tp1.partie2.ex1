package tp1.partie2.ex1.visitor;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import tp1.partie2.ex1.model.ClassInfo;

import java.util.List;

/** Compte les attributs (fragments) par classe. */
public class FieldDeclarationVisitor extends ASTVisitor {
    private final List<ClassInfo> classes;
    private int classIndex = -1;

    public FieldDeclarationVisitor(List<ClassInfo> classes) {
        this.classes = classes;
    }

    @Override
    public boolean visit(TypeDeclaration node) {
        classIndex++;
        return super.visit(node);
    }

    @Override
    public void endVisit(TypeDeclaration node) {
        super.endVisit(node);
    }

    @Override
    public boolean visit(FieldDeclaration node) {
        if (classIndex >= 0 && classIndex < classes.size()) {
            int fragments = node.fragments().size();
            classes.get(classIndex).incAttributeCount(fragments);
        }
        return super.visit(node);
    }
}
