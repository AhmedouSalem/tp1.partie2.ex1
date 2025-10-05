package tp1.partie2.ex1.model;

public class MethodInfo {
    private final String name;
    private final int paramCount;
    private final int loc; // lignes de code (approx) = endLine - startLine + 1

    public MethodInfo(String name, int paramCount, int loc) {
        this.name = name;
        this.paramCount = paramCount;
        this.loc = loc;
    }

    public String getName() { return name; }
    public int getParamCount() { return paramCount; }
    public int getLoc() { return loc; }
}
