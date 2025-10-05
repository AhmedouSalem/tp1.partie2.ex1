# 🧩 TP1 – Partie 2 : Analyse Statique d’une Application Orientée Objet

> **Université de Montpellier – Master 2 Génie Logiciel (UE HAI913I)**  
> _Évolution et Restructuration des Logiciels_  
> **Auteur :** Ahmedou Salem  
> **Date :** 05 octobre 2025  

---

## 🎯 Objectif du projet

Ce projet consiste à développer un outil d’**analyse statique** en Java capable d’extraire automatiquement des **métriques orientées objet** à partir du code source d’une application Java.

L’outil analyse le code à l’aide de l’API **Eclipse JDT (Java Development Tools)** et calcule diverses statistiques sur :
- le nombre de classes, méthodes et attributs ;
- le nombre moyen de lignes de code par méthode ;
- les classes les plus complexes (Top 10%) ;
- et d’autres indicateurs structurels (13 métriques au total).

Une **interface graphique Swing** permet de lancer l’analyse et d’afficher les résultats de manière interactive.

---

## 🏗️ Architecture du projet

```text
tp1.partie2.ex1
├── gui
│   ├── Application.java
│   └── MainFrame.java
├── model
│   ├── ClassInfo.java
│   ├── MethodInfo.java
│   ├── AnalysisResult.java
│   └── MetricsSummary.java
├── parser
│   ├── IParser.java
│   └── Parser.java
├── service
│   ├── Analyzer.java
│   └── MetricsService.java
└── visitor
    ├── TypeDeclarationVisitor.java
    ├── FieldDeclarationVisitor.java
    └── MethodDeclarationVisitor.java
```

---

## ⚙️ Fonctionnement général

1. **Parser** (`Parser.java`)  
   - Liste récursivement tous les fichiers `.java` d’un dossier `src`.  
   - Utilise `ASTParser` (Eclipse JDT) pour construire l’arbre syntaxique de chaque fichier.

2. **Visiteurs**  
   - `TypeDeclarationVisitor` : récupère les classes et leurs packages.  
   - `FieldDeclarationVisitor` : compte les attributs.  
   - `MethodDeclarationVisitor` : récupère les méthodes, leurs paramètres et lignes de code.  

3. **Analyzer**  
   - Combine les résultats des visiteurs dans des objets `ClassInfo` et `MethodInfo`.

4. **MetricsService**  
   - Calcule les 13 métriques globales demandées.

5. **Interface Swing** (`MainFrame.java`)  
   - Permet à l’utilisateur de choisir un dossier `src`, de définir les paramètres (`Top %`, `X`), et d’afficher les résultats sous forme tabulaire.

---

## 📊 Métriques calculées

| #  | Description |
|----|--------------|
| 1  | Nombre total de classes |
| 2  | Nombre total de lignes de code |
| 3  | Nombre total de méthodes |
| 4  | Nombre total de packages |
| 5  | Moyenne de méthodes par classe |
| 6  | Moyenne de lignes de code par méthode |
| 7  | Moyenne d’attributs par classe |
| 8  | Top 10% des classes avec le plus de méthodes |
| 9  | Top 10% des classes avec le plus d’attributs |
| 10 | Intersection des deux catégories précédentes |
| 11 | Classes possédant plus de X méthodes |
| 12 | Top 10% des méthodes avec le plus de lignes de code |
| 13 | Nombre maximal de paramètres dans toutes les méthodes |

---

## 🖥️ Interface Swing

### Aperçu

![Interface Swing](images/viewSwing.png)

### Composants principaux

| Composant | Rôle |
|------------|------|
| `JFrame` | Fenêtre principale |
| `GridBagLayout` | Organisation du panneau de contrôle |
| `JTextField`, `JButton`, `JSpinner` | Entrées utilisateur |
| `JSplitPane`, `JTabbedPane` | Organisation en zones et onglets |
| `JTable` + `AbstractTableModel` | Affichage des résultats |
| `JFileChooser`, `JOptionPane` | Interaction utilisateur |

---

## 🚀 Installation et exécution

### 🧩 Prérequis
- **Java 17+** installé (`java -version`)
- **Maven** (ou exécution depuis Eclipse/IntelliJ)
- Librairie **Eclipse JDT Core** (ajoutée via Maven)

---

### 🧰 Étapes d’installation

1. **Cloner le projet :**
   ```bash
   git clone https://github.com/AhmedouSalem/tp1.partie2.ex1.git
   cd tp1.partie2.ex1
   ```

2. **Compiler :**
   ```bash
   mvn clean compile
   ```

3. **Exécuter :**
   ```bash
   mvn exec:java -Dexec.mainClass="tp1.partie2.ex1.gui.Application"
   ```

> ⚠️ Alternativement, depuis Eclipse ou IntelliJ :  
> Lancer la classe `tp1.partie2.ex1.gui.Application.java`.

---

## 🧮 Exemple d’exécution

**Projet analysé :** `project.exemple.etude`

**Résultats obtenus :**
![Interface Swing](images/resultSwing.png)

---

## 🔍 Références

- **Eclipse JDT API :** [https://help.eclipse.org/latest/topic/org.eclipse.jdt.doc.isv/reference/api/](https://help.eclipse.org/latest/topic/org.eclipse.jdt.doc.isv/reference/api/)  
- **Tutoriel AST Visitor :** [https://www.vogella.com/tutorials/EclipseJDT/article.html](https://www.vogella.com/tutorials/EclipseJDT/article.html)  
- **Documentation Swing :** [https://docs.oracle.com/javase/tutorial/uiswing/](https://docs.oracle.com/javase/tutorial/uiswing/)

---

## ✨ Auteur

**Ahmedou Salem**  
_Master 2 Génie Logiciel – Université de Montpellier_  
📧 ahmedou8salem@gmail.com  
📅 05 octobre 2025  # tp1.partie2.ex1
