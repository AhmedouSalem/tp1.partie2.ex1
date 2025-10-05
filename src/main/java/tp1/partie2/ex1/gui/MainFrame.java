package tp1.partie2.ex1.gui;

import tp1.partie2.ex1.model.*;
import tp1.partie2.ex1.parser.IParser;
import tp1.partie2.ex1.parser.Parser;
import tp1.partie2.ex1.service.Analyzer;
import tp1.partie2.ex1.service.MetricsService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.io.File;
import java.util.List;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

    // --- services ---
    private final IParser parser = new Parser();
    private final Analyzer analyzer = new Analyzer();
    private final MetricsService metricsService = new MetricsService();

    // --- contrôles haut ---
    private final JTextField tfSource = new JTextField(30);
    private final JSpinner spPercent = new JSpinner(new SpinnerNumberModel(10, 1, 100, 1));
    private final JSpinner spX = new JSpinner(new SpinnerNumberModel(10, 0, 10_000, 1));
    private final JButton btBrowse = new JButton("Parcourir…");
    private final JButton btAnalyze = new JButton("Analyser");

    // --- résumé (labels 1→7,13) ---
    private final JLabel lbNbClasses = new JLabel("-");
    private final JLabel lbNbLoc = new JLabel("-");
    private final JLabel lbNbMethods = new JLabel("-");
    private final JLabel lbNbPackages = new JLabel("-");
    private final JLabel lbAvgMethPerClass = new JLabel("-");
    private final JLabel lbAvgLocPerMethod = new JLabel("-");
    private final JLabel lbAvgAttrsPerClass = new JLabel("-");
    private final JLabel lbMaxParams = new JLabel("-");

    // --- tables ---
    private final ClassesByMetricTableModel tmTopClassesByMethods = new ClassesByMetricTableModel("Classe", "Méthodes");
    private final ClassesByMetricTableModel tmTopClassesByAttrs   = new ClassesByMetricTableModel("Classe", "Attributs");
    private final MethodsByMetricTableModel tmTopMethodsByLoc     = new MethodsByMetricTableModel();
    private final ClassesByMetricTableModel tmMoreThanX           = new ClassesByMetricTableModel("Classe", "> X méthodes");

    public MainFrame() {
        super("Analyse statique OO — métriques");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        // Contenu
        JPanel root = new JPanel(new BorderLayout(10,10));
        root.setBorder(new EmptyBorder(10,10,10,10));
        setContentPane(root);

        root.add(buildControls(), BorderLayout.NORTH);
        root.add(buildCenter(), BorderLayout.CENTER);

        // actions
        btBrowse.addActionListener(e -> onBrowse());
        btAnalyze.addActionListener(e -> onAnalyze());
    }

    // --- UI builders ---
    private JComponent buildControls() {
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4,4,4,4);
        c.gridx=0; c.gridy=0; c.anchor=GridBagConstraints.WEST;
        p.add(new JLabel("Source (dossier src) :"), c);
        c.gridx=1; c.fill=GridBagConstraints.HORIZONTAL; c.weightx=1;
        p.add(tfSource, c);
        c.gridx=2; c.fill=GridBagConstraints.NONE; c.weightx=0;
        p.add(btBrowse, c);

        c.gridx=0; c.gridy=1;
        p.add(new JLabel("Top % :"), c);
        c.gridx=1; p.add(spPercent, c);
        c.gridx=2; p.add(new JLabel("X (classe > X méthodes) :"), c);
        c.gridx=3; p.add(spX, c);

        c.gridx=4; p.add(btAnalyze, c);

        return p;
    }

    private JComponent buildCenter() {
        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        split.setResizeWeight(0.35);

        // Résumé
        JPanel resume = new JPanel(new GridLayout(0,2,8,6));
        resume.setBorder(BorderFactory.createTitledBorder("Résumé des métriques"));
        resume.add(new JLabel("1) Nombre de classes :"));         resume.add(lbNbClasses);
        resume.add(new JLabel("2) Lignes de code (approx) :"));   resume.add(lbNbLoc);
        resume.add(new JLabel("3) Nombre total de méthodes :"));  resume.add(lbNbMethods);
        resume.add(new JLabel("4) Nombre de packages :"));        resume.add(lbNbPackages);
        resume.add(new JLabel("5) Moy. méthodes / classe :"));    resume.add(lbAvgMethPerClass);
        resume.add(new JLabel("6) Moy. LOC / méthode :"));        resume.add(lbAvgLocPerMethod);
        resume.add(new JLabel("7) Moy. attributs / classe :"));   resume.add(lbAvgAttrsPerClass);
        resume.add(new JLabel("13) Max paramètres (toutes méthodes) :")); resume.add(lbMaxParams);

        split.setTopComponent(resume);

        // Tabs listes
        JTabbedPane tabs = new JTabbedPane();

        tabs.addTab("Top % classes par méthodes",
            new JScrollPane(new JTable(tmTopClassesByMethods)));

        tabs.addTab("Top % classes par attributs",
            new JScrollPane(new JTable(tmTopClassesByAttrs)));

        tabs.addTab("Top % méthodes par LOC",
            new JScrollPane(new JTable(tmTopMethodsByLoc)));

        tabs.addTab("Classes avec > X méthodes",
            new JScrollPane(new JTable(tmMoreThanX)));

        split.setBottomComponent(tabs);
        return split;
    }

    // --- actions ---
    private void onBrowse() {
        JFileChooser ch = new JFileChooser();
        ch.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (ch.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            tfSource.setText(ch.getSelectedFile().getAbsolutePath());
        }
    }

    private void onAnalyze() {
        String src = tfSource.getText().trim();
        if (src.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Choisis un dossier 'src' de projet Java.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int percent = (int) spPercent.getValue();
        int x = (int) spX.getValue();

        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        btAnalyze.setEnabled(false);

        try {
            AnalysisResult ar = analyzer.analyzeProjectSources(parser, new File(src));
            MetricsSummary m = metricsService.compute(ar, percent, x);
            updateSummary(m, percent, x);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur: " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        } finally {
            btAnalyze.setEnabled(true);
            setCursor(Cursor.getDefaultCursor());
        }
    }

    private void updateSummary(MetricsSummary m, int percent, int x) {
        lbNbClasses.setText(String.valueOf(m.nbClasses));
        lbNbLoc.setText(String.valueOf(m.nbLignesApp));
        lbNbMethods.setText(String.valueOf(m.totalMethods));
        lbNbPackages.setText(String.valueOf(m.totalPackages));
        lbAvgMethPerClass.setText(String.format("%.2f", m.avgMethodsPerClass));
        lbAvgLocPerMethod.setText(String.format("%.2f", m.avgLocPerMethod));
        lbAvgAttrsPerClass.setText(String.format("%.2f", m.avgAttrsPerClass));
        lbMaxParams.setText(String.valueOf(m.maxParams));

        tmTopClassesByMethods.setData(m.top10ByMethods, c -> c.getQualifiedName(), c -> c.getMethodCount());
        tmTopClassesByAttrs.setData(m.top10ByAttrs, c -> c.getQualifiedName(), c -> c.getAttributeCount());
        tmTopMethodsByLoc.setData(m.top10MethodsByLoc);
        tmMoreThanX.setData(m.moreThanXMethods, c -> c.getQualifiedName(), c -> c.getMethodCount());

        setTitle(String.format("Analyse statique OO — Top %d%%, X=%d", percent, x));
    }

    // ==================== TableModels ====================

    /** Table générique pour classes + une métrique entière. */
    private static class ClassesByMetricTableModel extends AbstractTableModel {
        private List<ClassInfo> data = List.of();
        private java.util.function.Function<ClassInfo,String> nameFn;
        private java.util.function.ToIntFunction<ClassInfo> metricFn;
        private final String col0;
        private final String col1;

        public ClassesByMetricTableModel(String col0, String col1) {
            this.col0 = col0; this.col1 = col1;
        }

        public void setData(List<ClassInfo> list, java.util.function.Function<ClassInfo,String> nameFn,
                            java.util.function.ToIntFunction<ClassInfo> metricFn) {
            this.data = list;
            this.nameFn = nameFn;
            this.metricFn = metricFn;
            fireTableDataChanged();
        }

        @Override public int getRowCount() { return data.size(); }
        @Override public int getColumnCount() { return 2; }
        @Override public String getColumnName(int c) { return c==0? col0 : col1; }
        @Override public Object getValueAt(int r, int c) {
            ClassInfo ci = data.get(r);
            return c==0 ? nameFn.apply(ci) : metricFn.applyAsInt(ci);
        }
    }

    /** Table pour méthodes (nom + LOC + (optionnel) paramètres). */
    private static class MethodsByMetricTableModel extends AbstractTableModel {
        private List<MethodInfo> data = List.of();

        public void setData(List<MethodInfo> list) {
            this.data = list;
            fireTableDataChanged();
        }

        @Override public int getRowCount() { return data.size(); }
        @Override public int getColumnCount() { return 3; }
        @Override public String getColumnName(int c) {
            return switch (c) {
                case 0 -> "Méthode";
                case 1 -> "LOC";
                case 2 -> "Nb paramètres";
                default -> "";
            };
        }
        @Override public Object getValueAt(int r, int c) {
            MethodInfo mi = data.get(r);
            return switch (c) {
                case 0 -> mi.getName();
                case 1 -> mi.getLoc();
                case 2 -> mi.getParamCount();
                default -> "";
            };
        }
    }
}
