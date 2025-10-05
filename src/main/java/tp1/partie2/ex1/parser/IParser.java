package tp1.partie2.ex1.parser;

import java.io.File;
import java.util.List;

import org.eclipse.jdt.core.dom.CompilationUnit;

public interface IParser {
	// Récurer les fichiers sources java dans le rep src passé en param
	public List<File> listJavaFilesForFolder(final File folder);
	// Création AST
	public CompilationUnit parse(char[] classSource);
}
