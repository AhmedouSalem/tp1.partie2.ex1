package tp1.partie2.ex1.model;

import java.util.List;
import java.util.Set;

public class AnalysisResult {
    private final List<ClassInfo> classes;
    private final Set<String> packages;
    private final int totalAppLoc;

    public AnalysisResult(List<ClassInfo> classes, Set<String> packages, int totalAppLoc) {
        this.classes = classes;
        this.packages = packages;
        this.totalAppLoc = totalAppLoc;
    }

    public List<ClassInfo> getClasses() { return classes; }
    public Set<String> getPackages() { return packages; }
    public int getTotalAppLoc() { return totalAppLoc; }
}
