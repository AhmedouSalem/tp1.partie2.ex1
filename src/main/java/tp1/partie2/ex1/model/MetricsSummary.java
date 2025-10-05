package tp1.partie2.ex1.model;

import java.util.List;

public class MetricsSummary {
    public final int nbClasses;
    public final int nbLignesApp;
    public final int totalMethods;
    public final int totalPackages;
    public final double avgMethodsPerClass;
    public final double avgLocPerMethod;
    public final double avgAttrsPerClass;
    public final List<ClassInfo> top10ByMethods;
    public final List<ClassInfo> top10ByAttrs;
    public final List<ClassInfo> intersectionTop10;
    public final List<ClassInfo> moreThanXMethods;
    public final List<MethodInfo> top10MethodsByLoc;
    public final int maxParams;

    public MetricsSummary(int nbClasses, int nbLignesApp, int totalMethods, int totalPackages,
                          double avgMethodsPerClass, double avgLocPerMethod, double avgAttrsPerClass,
                          List<ClassInfo> top10ByMethods, List<ClassInfo> top10ByAttrs,
                          List<ClassInfo> intersectionTop10, List<ClassInfo> moreThanXMethods,
                          List<MethodInfo> top10MethodsByLoc, int maxParams) {
        this.nbClasses = nbClasses;
        this.nbLignesApp = nbLignesApp;
        this.totalMethods = totalMethods;
        this.totalPackages = totalPackages;
        this.avgMethodsPerClass = avgMethodsPerClass;
        this.avgLocPerMethod = avgLocPerMethod;
        this.avgAttrsPerClass = avgAttrsPerClass;
        this.top10ByMethods = top10ByMethods;
        this.top10ByAttrs = top10ByAttrs;
        this.intersectionTop10 = intersectionTop10;
        this.moreThanXMethods = moreThanXMethods;
        this.top10MethodsByLoc = top10MethodsByLoc;
        this.maxParams = maxParams;
    }
}
