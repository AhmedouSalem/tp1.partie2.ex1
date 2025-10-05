package tp1.partie2.ex1.model;

import java.util.ArrayList;
import java.util.List;

public class ClassInfo {
    private final String packageName;
    private final String className;
    private final List<MethodInfo> methods = new ArrayList<>();
    private int attributeCount; 
    private int sourceLines;

    public ClassInfo(String packageName, String className) {
        this.packageName = packageName == null ? "" : packageName;
        this.className = className;
    }

    public String getQualifiedName() {
        return (packageName.isEmpty() ? "" : packageName + ".") + className;
    }

    public String getPackageName() { return packageName; }
    public String getClassName() { return className; }

    public List<MethodInfo> getMethods() { return methods; }

    public int getAttributeCount() { return attributeCount; }
    public void incAttributeCount(int n) { this.attributeCount += n; }

    public int getSourceLines() { return sourceLines; }
    public void setSourceLines(int loc) { this.sourceLines = loc; }

    public int getMethodCount() { return methods.size(); }
    public int getTotalMethodLOC() {
        return methods.stream().mapToInt(MethodInfo::getLoc).sum();
    }
    public int getMaxParamsInMethods() {
        return methods.stream().mapToInt(MethodInfo::getParamCount).max().orElse(0);
    }
}
