package tp1.partie2.ex1.service;

import tp1.partie2.ex1.model.*;

import java.util.*;
import java.util.stream.Collectors;

public class MetricsService {

    public MetricsSummary compute(AnalysisResult ar, int topPercent, int xMethods) {
        List<ClassInfo> classes = ar.getClasses();
        int nbClasses = classes.size();
        int nbLignesApp = ar.getTotalAppLoc();
        int totalPackages = ar.getPackages().size();

        int totalMethods = classes.stream().mapToInt(ClassInfo::getMethodCount).sum();
        double avgMethodsPerClass = nbClasses == 0 ? 0 : (double) totalMethods / nbClasses;

        int totalMethodLOC = classes.stream().mapToInt(ClassInfo::getTotalMethodLOC).sum();
        double avgLocPerMethod = totalMethods == 0 ? 0 : (double) totalMethodLOC / totalMethods;

        int totalAttrs = classes.stream().mapToInt(ClassInfo::getAttributeCount).sum();
        double avgAttrsPerClass = nbClasses == 0 ? 0 : (double) totalAttrs / nbClasses;

        List<ClassInfo> topByMethods = topPercent(classes, topPercent,
                Comparator.comparingInt(ClassInfo::getMethodCount));
        List<ClassInfo> topByAttrs = topPercent(classes, topPercent,
                Comparator.comparingInt(ClassInfo::getAttributeCount));

        Set<String> namesTopMethods = topByMethods.stream().map(ClassInfo::getQualifiedName).collect(Collectors.toSet());
        List<ClassInfo> intersection = topByAttrs.stream()
                .filter(c -> namesTopMethods.contains(c.getQualifiedName()))
                .collect(Collectors.toList());

        List<ClassInfo> moreThanX = classes.stream()
                .filter(c -> c.getMethodCount() > xMethods)
                .collect(Collectors.toList());

        List<MethodInfo> allMethods = classes.stream().flatMap(c -> c.getMethods().stream()).collect(Collectors.toList());
        List<MethodInfo> topMethodsByLoc = topPercentMethods(allMethods, topPercent,
                Comparator.comparingInt(MethodInfo::getLoc));

        int maxParams = allMethods.stream().mapToInt(MethodInfo::getParamCount).max().orElse(0);

        return new MetricsSummary(
                nbClasses, nbLignesApp, totalMethods, totalPackages,
                avgMethodsPerClass, avgLocPerMethod, avgAttrsPerClass,
                topByMethods, topByAttrs, intersection, moreThanX, topMethodsByLoc, maxParams
        );
    }

    // ---- helpers ----
    private <T> List<T> topPercent(List<T> list, int percent, Comparator<T> byMetric) {
        if (list.isEmpty()) return List.of();
        int k = Math.max(1, (int) Math.ceil(list.size() * (percent / 100.0)));
        return list.stream().sorted(byMetric.reversed()).limit(k).collect(Collectors.toList());
    }

    private List<MethodInfo> topPercentMethods(List<MethodInfo> list, int percent,
                                               Comparator<MethodInfo> byMetric) {
        if (list.isEmpty()) return List.of();
        int k = Math.max(1, (int) Math.ceil(list.size() * (percent / 100.0)));
        return list.stream().sorted(byMetric.reversed()).limit(k).collect(Collectors.toList());
    }
}
