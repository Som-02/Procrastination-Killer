/**
 * Procrastination Killer
 * Created by: Somnath Pal
 * Year: 2025
 *
 * This project is developed by Somnath Pal. All rights reserved.
 */
package com.showmee.prokiller.ui;

import com.showmee.prokiller.storage.ActivityStorage;

import javax.swing.*;
import java.awt.*;

public class TodayYesterdayFrame extends JFrame {

    public TodayYesterdayFrame(ActivityStorage.DailySummary today,
                               ActivityStorage.DailySummary yesterday) {
        setTitle("Today vs Yesterday");
        setSize(600, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        getContentPane().setBackground(new Color(18, 18, 30));
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Today vs Yesterday Summary", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        add(title, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(4, 3, 10, 10));
        grid.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        grid.setBackground(new Color(18, 18, 30));

        String[] rowNames = {
                "Productive time (min)",
                "Waste time (min)",
                "Neutral time (min)",
                "Productivity %"
        };

        double todayProdMin = today.prodSec / 60.0;
        double todayWasteMin = today.wasteSec / 60.0;
        double todayNeutralMin = today.neutralSec / 60.0;

        double yProdMin = yesterday.prodSec / 60.0;
        double yWasteMin = yesterday.wasteSec / 60.0;
        double yNeutralMin = yesterday.neutralSec / 60.0;

        int todayScore = 0;
        long todayPW = today.prodSec + today.wasteSec;
        if (todayPW > 0) todayScore = (int) (100 * today.prodSec / todayPW);

        int yScore = 0;
        long yPW = yesterday.prodSec + yesterday.wasteSec;
        if (yPW > 0) yScore = (int) (100 * yesterday.prodSec / yPW);

        String[][] data = {
                {String.format("%.1f", todayProdMin), String.format("%.1f", yProdMin)},
                {String.format("%.1f", todayWasteMin), String.format("%.1f", yWasteMin)},
                {String.format("%.1f", todayNeutralMin), String.format("%.1f", yNeutralMin)},
                {todayScore + " %", yScore + " %"}
        };

        Color labelColor = new Color(190, 190, 210);
        Color valueColor = Color.WHITE;

        for (int row = 0; row < rowNames.length; row++) {
            JLabel name = new JLabel(rowNames[row]);
            name.setForeground(labelColor);
            name.setFont(new Font("Segoe UI", Font.BOLD, 14));
            grid.add(name);

            JLabel todayVal = new JLabel(data[row][0], SwingConstants.CENTER);
            todayVal.setForeground(valueColor);
            todayVal.setFont(new Font("Segoe UI", Font.BOLD, 14));
            grid.add(todayVal);

            JLabel yVal = new JLabel(data[row][1], SwingConstants.CENTER);
            yVal.setForeground(valueColor);
            yVal.setFont(new Font("Segoe UI", Font.BOLD, 14));
            grid.add(yVal);
        }

        JPanel headerRow = new JPanel(new GridLayout(1, 3));
        headerRow.setBackground(new Color(18, 18, 30));
        headerRow.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 20));

        headerRow.add(new JLabel("")); // empty corner

        JLabel todayHeader = new JLabel("Today", SwingConstants.CENTER);
        todayHeader.setForeground(new Color(88, 189, 255));
        todayHeader.setFont(new Font("Segoe UI", Font.BOLD, 15));
        headerRow.add(todayHeader);

        JLabel yHeader = new JLabel("Yesterday", SwingConstants.CENTER);
        yHeader.setForeground(new Color(144, 190, 109));
        yHeader.setFont(new Font("Segoe UI", Font.BOLD, 15));
        headerRow.add(yHeader);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setBackground(new Color(18, 18, 30));
        centerPanel.add(headerRow, BorderLayout.NORTH);
        centerPanel.add(grid, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);
    }
}

