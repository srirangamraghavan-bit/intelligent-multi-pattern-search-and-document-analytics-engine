package documentsearch;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * Modern Java Swing GUI Frontend for Intelligent Multi-Pattern Search & Analytics Engine.
 * 
 * Redesigned for modern aesthetics, clean visual hierarchy, enhanced usability,
 * and intuitive college project demonstration.
 */
public class DocumentSearchGUI extends JFrame {

    private DocumentManager manager;

    // Color Palette
    private static final Color HEADER_BG = new Color(15, 23, 42);       // Slate 900
    private static final Color ACCENT_BLUE = new Color(56, 189, 248);    // Sky 400
    private static final Color PANEL_BG = new Color(248, 250, 252);     // Slate 50
    private static final Color CARD_BG = Color.WHITE;
    private static final Color TEXT_DARK = new Color(30, 41, 59);        // Slate 800
    private static final Color TEXT_MUTED = new Color(100, 116, 139);   // Slate 500
    private static final Color BORDER_COLOR = new Color(203, 213, 225); // Slate 300

    // Button Colors
    private static final Color BTN_SEARCH_BG = new Color(16, 185, 129);   // Emerald 500
    private static final Color BTN_COMPARE_BG = new Color(139, 92, 246);  // Purple 500
    private static final Color BTN_ANALYTICS_BG = new Color(2, 132, 199); // Sky 600
    private static final Color BTN_ADD_BG = new Color(79, 70, 229);       // Indigo 600
    private static final Color BTN_CLEAR_BG = new Color(100, 116, 139);   // Slate 500

    // Swing Components
    private JComboBox<String> docDropdown;
    private JComboBox<String> algoDropdown;
    private JTextField searchPatternField;
    private JTextArea outputArea;
    private JTextArea docPreviewArea;
    private JLabel statusLabel;
    private JLabel docStatsLabel;

    // Add Document Components
    private JTextField newTitleField;
    private JTextField newCategoryField;
    private JTextArea newContentArea;

    /**
     * Constructor accepting DocumentManager instance.
     */
    public DocumentSearchGUI(DocumentManager manager) {
        this.manager = (manager != null) ? manager : new DocumentManager();
        initLookAndFeel();
        initUI();
    }

    /**
     * Default constructor for standalone GUI launch.
     */
    public DocumentSearchGUI() {
        this(new DocumentManager());
    }

    private void initLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
            // Fallback to default Swing Look & Feel
        }
    }

    private void initUI() {
        setTitle("Intelligent Multi-Pattern Search & Document Analytics Engine");
        setSize(980, 720);
        setMinimumSize(new Dimension(900, 650));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainContainer = new JPanel(new BorderLayout(0, 0));
        mainContainer.setBackground(PANEL_BG);

        // Header Panel
        mainContainer.add(createHeaderPanel(), BorderLayout.NORTH);

        // Tabbed Center View
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("SansSerif", Font.BOLD, 13));
        tabbedPane.setBackground(PANEL_BG);

        tabbedPane.addTab("  🔍 Search & Analytics Workspace  ", createSearchWorkspacePanel());
        tabbedPane.addTab("  ➕ Add New Document  ", createAddDocPanel());

        mainContainer.add(tabbedPane, BorderLayout.CENTER);

        // Footer Status Bar
        mainContainer.add(createStatusBar(), BorderLayout.SOUTH);

        add(mainContainer);

        // Initial Data Populate
        refreshDocDropdown();
        updateDocumentPreview();
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(HEADER_BG);
        headerPanel.setBorder(new EmptyBorder(14, 20, 14, 20));

        // Title and Subtitle container
        JPanel titleBox = new JPanel(new GridLayout(2, 1, 0, 4));
        titleBox.setOpaque(false);

        JLabel titleLabel = new JLabel("INTELLIGENT MULTI-PATTERN SEARCH ENGINE");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);

        JLabel subLabel = new JLabel("College Academic DSA Project | String Indexing, Analytics & Algorithm Benchmarking");
        subLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        subLabel.setForeground(ACCENT_BLUE);

        titleBox.add(titleLabel);
        titleBox.add(subLabel);

        // Workflow Badge Indicator on Header Right
        JLabel workflowBadge = new JLabel(" Workflow: Add Doc → Select Doc → Pattern → Search → Analytics ");
        workflowBadge.setFont(new Font("SansSerif", Font.BOLD, 11));
        workflowBadge.setForeground(Color.WHITE);
        workflowBadge.setOpaque(true);
        workflowBadge.setBackground(new Color(30, 41, 59));
        workflowBadge.setBorder(new CompoundBorder(
                new LineBorder(ACCENT_BLUE, 1, true),
                new EmptyBorder(6, 12, 6, 12)
        ));

        headerPanel.add(titleBox, BorderLayout.WEST);
        headerPanel.add(workflowBadge, BorderLayout.EAST);

        return headerPanel;
    }

    private JPanel createSearchWorkspacePanel() {
        JPanel workspace = new JPanel(new BorderLayout(12, 12));
        workspace.setBackground(PANEL_BG);
        workspace.setBorder(new EmptyBorder(12, 14, 12, 14));

        // Top Control Panel (GridBagLayout for crisp alignment)
        JPanel controlCard = new JPanel(new GridBagLayout());
        controlCard.setBackground(CARD_BG);
        controlCard.setBorder(new CompoundBorder(
                new LineBorder(BORDER_COLOR, 1, true),
                new EmptyBorder(12, 16, 12, 16)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Row 0: Select Document
        JLabel lblDoc = new JLabel("Select Document:");
        lblDoc.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblDoc.setForeground(TEXT_DARK);
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.15;
        controlCard.add(lblDoc, gbc);

        docDropdown = new JComboBox<>();
        docDropdown.setFont(new Font("SansSerif", Font.PLAIN, 13));
        docDropdown.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                updateDocumentPreview();
            }
        });
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 0.5;
        controlCard.add(docDropdown, gbc);

        // Doc Stats Summary Label
        docStatsLabel = new JLabel("Category: General | Chars: 0 | Words: 0");
        docStatsLabel.setFont(new Font("SansSerif", Font.ITALIC, 12));
        docStatsLabel.setForeground(TEXT_MUTED);
        gbc.gridx = 2; gbc.gridy = 0; gbc.weightx = 0.35;
        controlCard.add(docStatsLabel, gbc);

        // Row 1: Search Pattern Input
        JLabel lblPattern = new JLabel("Search Pattern:");
        lblPattern.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblPattern.setForeground(TEXT_DARK);
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.15;
        controlCard.add(lblPattern, gbc);

        searchPatternField = new JTextField();
        searchPatternField.setFont(new Font("SansSerif", Font.PLAIN, 13));
        searchPatternField.setToolTipText("Enter keyword or phrase to search");
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 0.5;
        controlCard.add(searchPatternField, gbc);

        // Quick tip label
        JLabel lblTip = new JLabel("Case-sensitive string search");
        lblTip.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lblTip.setForeground(TEXT_MUTED);
        gbc.gridx = 2; gbc.gridy = 1; gbc.weightx = 0.35;
        controlCard.add(lblTip, gbc);

        // Row 2: Select Algorithm
        JLabel lblAlgo = new JLabel("Select Algorithm:");
        lblAlgo.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblAlgo.setForeground(TEXT_DARK);
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.15;
        controlCard.add(lblAlgo, gbc);

        algoDropdown = new JComboBox<>(new String[]{
            "KMP (Knuth-Morris-Pratt)", 
            "Z Algorithm", 
            "Rabin-Karp Rolling Hash", 
            "Suffix Array Search",
            "LCP Array Analysis"
        });
        algoDropdown.setFont(new Font("SansSerif", Font.PLAIN, 13));
        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 0.5;
        controlCard.add(algoDropdown, gbc);

        // Buttons Panel (Row 3)
        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 4));
        buttonRow.setOpaque(false);

        JButton btnSearch = createStyledButton("Run Search", BTN_SEARCH_BG);
        JButton btnCompare = createStyledButton("Compare Performance", BTN_COMPARE_BG);
        JButton btnAnalytics = createStyledButton("Document Analytics", BTN_ANALYTICS_BG);
        JButton btnReset = createStyledButton("Clear / Reset", BTN_CLEAR_BG);

        btnSearch.addActionListener(this::handleSearchAction);
        btnCompare.addActionListener(this::handleCompareAction);
        btnAnalytics.addActionListener(this::handleAnalyticsAction);
        btnReset.addActionListener(this::handleResetAction);

        buttonRow.add(btnSearch);
        buttonRow.add(btnCompare);
        buttonRow.add(btnAnalytics);
        buttonRow.add(btnReset);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 3; gbc.weightx = 1.0;
        controlCard.add(buttonRow, gbc);

        // Center Output Section: Split between Document Preview and Results Output
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setResizeWeight(0.3);
        splitPane.setDividerSize(6);
        splitPane.setBorder(null);

        // Document Preview Card
        docPreviewArea = new JTextArea();
        docPreviewArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        docPreviewArea.setEditable(false);
        docPreviewArea.setBackground(new Color(250, 250, 250));
        docPreviewArea.setMargin(new Insets(8, 8, 8, 8));

        JScrollPane previewScroll = new JScrollPane(docPreviewArea);
        previewScroll.setBorder(BorderFactory.createTitledBorder(
                new LineBorder(BORDER_COLOR, 1), "Selected Document Text Preview",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("SansSerif", Font.BOLD, 12), TEXT_DARK));

        // Results Output Card
        outputArea = new JTextArea();
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        outputArea.setEditable(false);
        outputArea.setBackground(new Color(255, 255, 255));
        outputArea.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane outputScroll = new JScrollPane(outputArea);
        outputScroll.setBorder(BorderFactory.createTitledBorder(
                new LineBorder(BORDER_COLOR, 1), "Results, Benchmark & Analytics Output",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("SansSerif", Font.BOLD, 12), TEXT_DARK));

        splitPane.setTopComponent(previewScroll);
        splitPane.setBottomComponent(outputScroll);

        workspace.add(controlCard, BorderLayout.NORTH);
        workspace.add(splitPane, BorderLayout.CENTER);

        return workspace;
    }

    private JPanel createAddDocPanel() {
        JPanel panel = new JPanel(new BorderLayout(14, 14));
        panel.setBackground(PANEL_BG);
        panel.setBorder(new EmptyBorder(16, 20, 16, 20));

        JPanel formCard = new JPanel(new GridBagLayout());
        formCard.setBackground(CARD_BG);
        formCard.setBorder(new CompoundBorder(
                new LineBorder(BORDER_COLOR, 1, true),
                new EmptyBorder(16, 20, 16, 20)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title Field
        JLabel lblTitle = new JLabel("Document Title:");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblTitle.setForeground(TEXT_DARK);
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.15;
        formCard.add(lblTitle, gbc);

        newTitleField = new JTextField();
        newTitleField.setFont(new Font("SansSerif", Font.PLAIN, 13));
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 0.85;
        formCard.add(newTitleField, gbc);

        // Category Field
        JLabel lblCategory = new JLabel("Category / Topic:");
        lblCategory.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblCategory.setForeground(TEXT_DARK);
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.15;
        formCard.add(lblCategory, gbc);

        newCategoryField = new JTextField();
        newCategoryField.setFont(new Font("SansSerif", Font.PLAIN, 13));
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 0.85;
        formCard.add(newCategoryField, gbc);

        // Content Area
        JLabel lblContent = new JLabel("Document Content:");
        lblContent.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblContent.setForeground(TEXT_DARK);
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.15;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        formCard.add(lblContent, gbc);

        newContentArea = new JTextArea(12, 50);
        newContentArea.setFont(new Font("SansSerif", Font.PLAIN, 13));
        newContentArea.setLineWrap(true);
        newContentArea.setWrapStyleWord(true);

        JScrollPane scrollContent = new JScrollPane(newContentArea);
        scrollContent.setBorder(new LineBorder(BORDER_COLOR, 1));
        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 0.85; gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        formCard.add(scrollContent, gbc);

        // Save Button
        JButton btnSave = createStyledButton("Save & Register Document", BTN_ADD_BG);
        btnSave.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnSave.addActionListener(this::handleAddDocument);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.setOpaque(false);
        btnPanel.add(btnSave);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formCard.add(btnPanel, gbc);

        panel.add(formCard, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createStatusBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(new Color(241, 245, 249));
        bar.setBorder(new CompoundBorder(
                new LineBorder(BORDER_COLOR, 1),
                new EmptyBorder(4, 14, 4, 14)
        ));

        statusLabel = new JLabel("System Ready. Total Documents: " + manager.getAllDocuments().size());
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        statusLabel.setForeground(TEXT_MUTED);

        JLabel infoLabel = new JLabel("Pure Java Swing | Zero Dependencies");
        infoLabel.setFont(new Font("SansSerif", Font.ITALIC, 11));
        infoLabel.setForeground(TEXT_MUTED);

        bar.add(statusLabel, BorderLayout.WEST);
        bar.add(infoLabel, BorderLayout.EAST);

        return bar;
    }

    private JButton createStyledButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setForeground(Color.WHITE);
        btn.setBackground(bg);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(new CompoundBorder(
                new LineBorder(bg.darker(), 1, true),
                new EmptyBorder(8, 14, 8, 14)
        ));
        return btn;
    }

    private Document getSelectedDocument() {
        int selectedIndex = docDropdown.getSelectedIndex();
        List<Document> docs = manager.getAllDocuments();
        if (selectedIndex >= 0 && selectedIndex < docs.size()) {
            return docs.get(selectedIndex);
        }
        return null;
    }

    private void refreshDocDropdown() {
        docDropdown.removeAllItems();
        List<Document> docs = manager.getAllDocuments();
        for (Document doc : docs) {
            docDropdown.addItem("[ID: " + doc.getId() + "] " + doc.getTitle());
        }
        if (statusLabel != null) {
            statusLabel.setText("System Ready. Total Documents: " + docs.size());
        }
    }

    private void updateDocumentPreview() {
        Document doc = getSelectedDocument();
        if (doc != null) {
            docStatsLabel.setText(String.format("Category: %s | Chars: %d | Words: %d",
                    doc.getCategory(), doc.getCharacterCount(), doc.getWordCount()));
            docPreviewArea.setText(doc.getContent());
            docPreviewArea.setCaretPosition(0);
        } else {
            docStatsLabel.setText("No document selected.");
            docPreviewArea.setText("");
        }
    }

    private void handleSearchAction(ActionEvent e) {
        Document doc = getSelectedDocument();
        if (doc == null) {
            JOptionPane.showMessageDialog(this, "Please select a document first.", "No Document Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String pattern = searchPatternField.getText().trim();
        String selectedAlgo = (String) algoDropdown.getSelectedItem();

        if (selectedAlgo != null && selectedAlgo.contains("LCP Array Analysis")) {
            SuffixArray sa = new SuffixArray(doc.getContent());
            LCPArray lcp = new LCPArray(doc.getContent(), sa.getSuffixArrayAsIntArray());
            
            StringBuilder sb = new StringBuilder();
            sb.append("========================================================================\n");
            sb.append("LCP (LONGEST COMMON PREFIX) ARRAY ANALYSIS\n");
            sb.append("========================================================================\n");
            sb.append("Document Title : ").append(doc.getTitle()).append("\n");
            sb.append("Text Length    : ").append(doc.getCharacterCount()).append(" characters\n");
            sb.append("------------------------------------------------------------------------\n");
            sb.append(String.format("%-6s | %-12s | %-10s | %-30s\n", "Rank", "Suffix Index", "LCP Value", "Suffix String"));
            sb.append("------------------------------------------------------------------------\n");

            int[] sArr = sa.getSuffixArrayAsIntArray();
            int[] lArr = lcp.getLcpArray();
            int limit = Math.min(doc.getCharacterCount(), 25);
            for (int i = 0; i < limit; i++) {
                String suf = doc.getContent().substring(sArr[i]);
                if (suf.length() > 27) suf = suf.substring(0, 24) + "...";
                suf = suf.replace("\n", " ").replace("\r", " ");
                sb.append(String.format("%-6d | %-12d | %-10d | %-30s\n", i, sArr[i], lArr[i], suf));
            }
            if (doc.getCharacterCount() > 25) {
                sb.append("... and ").append(doc.getCharacterCount() - 25).append(" more suffixes.\n");
            }
            sb.append("========================================================================\n");
            sb.append("VIVA NOTE: LCP[i] stores common prefix length between adjacent sorted suffixes,\n");
            sb.append("reducing redundant character comparisons in binary search.\n");
            sb.append("========================================================================\n");
            outputArea.setText(sb.toString());
            outputArea.setCaretPosition(0);
            statusLabel.setText("Executed LCP Array Analysis on Document ID: " + doc.getId());
            return;
        }

        if (pattern.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a search pattern / keyword.", "Empty Pattern", JOptionPane.WARNING_MESSAGE);
            return;
        }

        SearchResult result = null;
        if (selectedAlgo.contains("KMP")) {
            result = KMP.search(doc, pattern);
        } else if (selectedAlgo.contains("Z Algorithm")) {
            result = ZAlgorithm.search(doc, pattern);
        } else if (selectedAlgo.contains("Rabin-Karp")) {
            result = RabinKarp.search(doc, pattern);
        } else if (selectedAlgo.contains("Suffix Array")) {
            SuffixArray sa = new SuffixArray(doc.getContent());
            result = sa.search(doc, pattern);
        }

        if (result != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("========================================================================\n");
            sb.append("SEARCH RESULT SUMMARY\n");
            sb.append("========================================================================\n");
            sb.append("Algorithm Used    : ").append(result.getAlgorithmName()).append("\n");
            sb.append("Document ID       : ").append(result.getDocId()).append("\n");
            sb.append("Document Title    : ").append(result.getDocTitle()).append("\n");
            sb.append("Search Pattern    : \"").append(result.getPattern()).append("\"\n");
            sb.append("Total Matches     : ").append(result.getMatchCount()).append("\n");
            sb.append("Matching Indices  : ").append(result.getMatchingPositions().isEmpty() ? "None" : result.getMatchingPositions().toString()).append("\n");
            sb.append(String.format("Execution Time    : %.4f ms (%d ns)\n", result.getExecutionTimeMs(), result.getExecutionTimeNs()));
            sb.append("========================================================================\n");
            outputArea.setText(sb.toString());
            outputArea.setCaretPosition(0);
            statusLabel.setText(String.format("Search complete using %s. Matches found: %d", result.getAlgorithmName(), result.getMatchCount()));
        }
    }

    private void handleCompareAction(ActionEvent e) {
        Document doc = getSelectedDocument();
        String pattern = searchPatternField.getText().trim();

        if (doc == null || pattern.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a document and enter a search pattern to compare performance.", "Missing Input", JOptionPane.WARNING_MESSAGE);
            return;
        }

        List<SearchResult> results = PerformanceComparison.compareAlgorithms(doc, pattern);
        StringBuilder sb = new StringBuilder();
        sb.append("========================================================================\n");
        sb.append("ALGORITHM PERFORMANCE COMPARISON BENCHMARK\n");
        sb.append("========================================================================\n");
        sb.append("Document ID    : ").append(doc.getId()).append(" (").append(doc.getTitle()).append(")\n");
        sb.append("Text Length    : ").append(doc.getCharacterCount()).append(" characters\n");
        sb.append("Search Pattern : \"").append(pattern).append("\" (Length: ").append(pattern.length()).append(")\n");
        sb.append("------------------------------------------------------------------------\n");
        sb.append(String.format("%-22s | %-12s | %-16s | %-12s\n", "Algorithm", "Matches", "Time (ms)", "Time (ns)"));
        sb.append("------------------------------------------------------------------------\n");

        for (SearchResult res : results) {
            sb.append(String.format("%-22s | %-12d | %-16.4f | %-12d\n",
                    res.getAlgorithmName(), res.getMatchCount(), res.getExecutionTimeMs(), res.getExecutionTimeNs()));
        }
        sb.append("========================================================================\n");
        sb.append("Observation Note:\n");
        sb.append("- Empirical timings recorded live using System.nanoTime().\n");
        sb.append("- Never invents performance metrics.\n");
        sb.append("========================================================================\n");

        outputArea.setText(sb.toString());
        outputArea.setCaretPosition(0);
        statusLabel.setText("Benchmark executed across all 4 search algorithms.");
    }

    private void handleAnalyticsAction(ActionEvent e) {
        Document doc = getSelectedDocument();
        if (doc == null) {
            JOptionPane.showMessageDialog(this, "Please select a document first.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String pattern = searchPatternField.getText().trim();
        String content = doc.getContent();

        int charCount = doc.getCharacterCount();
        int wordCount = doc.getWordCount();
        int lineCount = content.isEmpty() ? 0 : content.split("\r\n|\r|\n").length;

        double avgWordLen = 0.0;
        String[] words = content.trim().isEmpty() ? new String[0] : content.trim().split("\\s+");
        if (words.length > 0) {
            long charSum = 0;
            for (String w : words) charSum += w.replaceAll("[^a-zA-Z0-9]", "").length();
            avgWordLen = (double) charSum / words.length;
        }

        int patternOccurrences = 0;
        double patternDensity = 0.0;
        if (!pattern.isEmpty()) {
            SearchResult sr = KMP.search(doc, pattern);
            patternOccurrences = sr.getMatchCount();
            if (wordCount > 0) {
                patternDensity = ((double) patternOccurrences / wordCount) * 100.0;
            }
        }

        // Frequency distribution
        Map<String, Integer> freqMap = new HashMap<>();
        for (String w : words) {
            String cleaned = w.toLowerCase().replaceAll("[^a-zA-Z0-9]", "");
            if (cleaned.length() > 2) {
                freqMap.put(cleaned, freqMap.getOrDefault(cleaned, 0) + 1);
            }
        }
        List<Map.Entry<String, Integer>> sortedFreq = new ArrayList<>(freqMap.entrySet());
        sortedFreq.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

        StringBuilder sb = new StringBuilder();
        sb.append("========================================================================\n");
        sb.append("DOCUMENT ANALYTICS REPORT\n");
        sb.append("========================================================================\n");
        sb.append("Document ID          : ").append(doc.getId()).append("\n");
        sb.append("Title                : ").append(doc.getTitle()).append("\n");
        sb.append("Category             : ").append(doc.getCategory()).append("\n");
        sb.append("------------------------------------------------------------------------\n");
        sb.append("Total Characters     : ").append(charCount).append("\n");
        sb.append("Total Words          : ").append(wordCount).append("\n");
        sb.append("Total Lines          : ").append(lineCount).append("\n");
        sb.append(String.format("Avg Word Length      : %.2f characters\n", avgWordLen));

        if (!pattern.isEmpty()) {
            sb.append("------------------------------------------------------------------------\n");
            sb.append("Analyzed Keyword     : \"").append(pattern).append("\"\n");
            sb.append("Keyword Occurrences  : ").append(patternOccurrences).append("\n");
            sb.append(String.format("Keyword Density      : %.2f%% of total words\n", patternDensity));
        }

        sb.append("------------------------------------------------------------------------\n");
        sb.append("Top Frequent Words   :\n");
        int limit = Math.min(sortedFreq.size(), 5);
        if (limit == 0) {
            sb.append("  (No significant words found)\n");
        } else {
            for (int i = 0; i < limit; i++) {
                Map.Entry<String, Integer> entry = sortedFreq.get(i);
                sb.append(String.format("  %d. %-15s : %d occurrences\n", (i + 1), entry.getKey(), entry.getValue()));
            }
        }
        sb.append("========================================================================\n");

        outputArea.setText(sb.toString());
        outputArea.setCaretPosition(0);
        statusLabel.setText("Analytics generated for Document ID: " + doc.getId());
    }

    private void handleAddDocument(ActionEvent e) {
        String title = newTitleField.getText().trim();
        String category = newCategoryField.getText().trim();
        String content = newContentArea.getText().trim();

        if (title.isEmpty() || content.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Title and Content are required fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Document doc = manager.createAndAddDocument(title, content, category);
        refreshDocDropdown();
        docDropdown.setSelectedIndex(docDropdown.getItemCount() - 1);
        updateDocumentPreview();

        newTitleField.setText("");
        newCategoryField.setText("");
        newContentArea.setText("");

        JOptionPane.showMessageDialog(this, "Document added successfully! Assigned ID: " + doc.getId(), "Success", JOptionPane.INFORMATION_MESSAGE);
        statusLabel.setText("Added new document: " + doc.getTitle());
    }

    private void handleResetAction(ActionEvent e) {
        searchPatternField.setText("");
        outputArea.setText("");
        if (docDropdown.getItemCount() > 0) {
            docDropdown.setSelectedIndex(0);
        }
        updateDocumentPreview();
        statusLabel.setText("Output and controls reset.");
    }

    /**
     * Standalone main method to launch DocumentSearchGUI directly.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DocumentSearchGUI gui = new DocumentSearchGUI();
            gui.setVisible(true);
        });
    }
}
