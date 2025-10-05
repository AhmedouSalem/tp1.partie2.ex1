package tp1.partie2.ex1.report;

import tp1.partie2.ex1.model.*;

public class ConsoleReporter {
    public void print(MetricsSummary m, int x, int percent) {
        System.out.println("1) Nb classes : " + m.nbClasses);
        System.out.println("2) Nb lignes de code (approx non vides) : " + m.nbLignesApp);
        System.out.println("3) Nb total de méthodes : " + m.totalMethods);
        System.out.println("4) Nb de packages : " + m.totalPackages);
        System.out.printf("5) Moyenne méthodes / classe : %.2f%n", m.avgMethodsPerClass);
        System.out.printf("6) Moyenne LOC / méthode : %.2f%n", m.avgLocPerMethod);
        System.out.printf("7) Moyenne attributs / classe : %.2f%n", m.avgAttrsPerClass);

        System.out.println("8) Top " + percent + "% classes par méthodes :");
        m.top10ByMethods.forEach(c -> System.out.println("   - " + c.getQualifiedName() + " (" + c.getMethodCount() + ")"));

        System.out.println("9) Top " + percent + "% classes par attributs :");
        m.top10ByAttrs.forEach(c -> System.out.println("   - " + c.getQualifiedName() + " (" + c.getAttributeCount() + ")"));

        System.out.println("10) Classes présentes dans les 2 catégories :");
        m.intersectionTop10.forEach(c -> System.out.println("   - " + c.getQualifiedName()));

        System.out.println("11) Classes avec > " + x + " méthodes :");
        m.moreThanXMethods.forEach(c -> System.out.println("   - " + c.getQualifiedName() + " (" + c.getMethodCount() + ")"));

        System.out.println("12) Top " + percent + "% méthodes par LOC :");
        m.top10MethodsByLoc.forEach(mm -> System.out.println("   - " + mm.getName() + " (LOC=" + mm.getLoc() + ")"));

        System.out.println("13) Max paramètres dans une méthode : " + m.maxParams);
    }
}
