/**
 * Procrastination Killer
 * Created by: Somnath Pal
 * Year: 2025
 *
 * This project is developed by Somnath Pal. All rights reserved.
 */
package com.showmee.prokiller.ui;

import com.showmee.prokiller.model.ActivityRecord;
import com.showmee.prokiller.model.AppCategory;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.awt.Color;
import java.awt.Font;
import javax.swing.table.JTableHeader;
import javax.swing.table.DefaultTableCellRenderer;

public class StatsFrame extends JFrame {

    private JTable table;

    public StatsFrame(List<ActivityRecord> records) {
    setTitle("Detailed Stats");
    setSize(800, 400);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setLocationRelativeTo(null);

    table = new JTable();
    JScrollPane scrollPane = new JScrollPane(table);

    setLayout(new BorderLayout());
    add(scrollPane, BorderLayout.CENTER);

    populateTable(records);

    // 👉 Add this
    applyTheme(scrollPane);
}


    private static class Summary {
        long prodSec = 0;
        long wasteSec = 0;
        long neutralSec = 0;
    }
private void applyTheme(JScrollPane scrollPane) {
    Color bg = new Color(20, 20, 35);
    Color cardBg = new Color(30, 30, 50);
    Color textPrimary = new Color(240, 240, 255);
    Color textSecondary = new Color(200, 200, 210);
    Color accent = new Color(88, 101, 242);

    // Frame background
    getContentPane().setBackground(bg);

    // Scroll pane background
    scrollPane.getViewport().setBackground(cardBg);
    scrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder());

    // Table body
    table.setBackground(cardBg);
    table.setForeground(Color.WHITE);
    table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
    table.setGridColor(new Color(60, 60, 80));
    table.setRowHeight(24);
    table.setShowGrid(true);
    table.setFillsViewportHeight(true);
    table.setSelectionBackground(new Color(70, 85, 200));
    table.setSelectionForeground(Color.WHITE);

    // Center last column (Productivity %)
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
    table.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);

    // Header styling
    JTableHeader header = table.getTableHeader();
    header.setBackground(new Color(25, 25, 45));
    header.setForeground(Color.BLACK);
    header.setFont(new Font("Segoe UI", Font.BOLD, 13));
    header.setReorderingAllowed(false);
}

    private void populateTable(List<ActivityRecord> records) {
        Map<String, Summary> map = new LinkedHashMap<>();

        if (records != null) {
            for (ActivityRecord r : records) {
                String title = r.getWindowTitle();
                if (title == null || title.isBlank()) {
                    title = "(No Title)";
                }
                Summary s = map.computeIfAbsent(title, k -> new Summary());
                long dur = r.getDurationInSeconds();
                AppCategory cat = r.getCategory();

                if (cat == null) {
                    s.neutralSec += dur;
                } else {
                    switch (cat) {
                        case PRODUCTIVE -> s.prodSec += dur;
                        case WASTE -> s.wasteSec += dur;
                        case NEUTRAL -> s.neutralSec += dur;
                    }
                }
            }
        }

        String[] columns = {
                "Window Title",
                "Productive (min)",
                "Waste (min)",
                "Neutral (min)",
                "Total (min)",
                "Productivity %"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // read-only
            }
        };

        for (Map.Entry<String, Summary> e : map.entrySet()) {
            String title = e.getKey();
            Summary s = e.getValue();

            double prodMin = s.prodSec / 60.0;
            double wasteMin = s.wasteSec / 60.0;
            double neutralMin = s.neutralSec / 60.0;
            double totalMin = (s.prodSec + s.wasteSec + s.neutralSec) / 60.0;

            int score = 0;
            long prodPlusWaste = s.prodSec + s.wasteSec;
            if (prodPlusWaste > 0) {
                score = (int) (100 * s.prodSec / prodPlusWaste);
            }

            model.addRow(new Object[]{
                    title,
                    String.format("%.1f", prodMin),
                    String.format("%.1f", wasteMin),
                    String.format("%.1f", neutralMin),
                    String.format("%.1f", totalMin),
                    score + " %"
            });
        }

        table.setModel(model);
    }
    
}

