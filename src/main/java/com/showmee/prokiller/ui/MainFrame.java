/**
 * Procrastination Killer
 * Created by: Somnath Pal
 * Year: 2025
 *
 * This project is developed by Somnath Pal. All rights reserved.
 */
package com.showmee.prokiller.ui;
import com.showmee.prokiller.storage.ActivityStorage;
import javax.swing.JOptionPane;
import com.showmee.prokiller.ui.StatsFrame;
import com.showmee.prokiller.storage.ActivityStorage;
import com.showmee.prokiller.ui.TodayYesterdayFrame;
import java.time.LocalDate;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BorderFactory;


import com.showmee.prokiller.model.ActivityRecord;
import com.showmee.prokiller.model.AppCategory;
import com.showmee.prokiller.tracking.ActivityTracker;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
public class MainFrame extends javax.swing.JFrame {
     private ActivityTracker tracker;
    private List<ActivityRecord> currentRecords = new ArrayList<>();
    private boolean tracking = false;
        private void styleUI() {
        Color bg = new Color(18, 18, 30);
        Color cardBg = new Color(30, 30, 50);
        Color accent = new Color(88, 101, 242);
        Color textPrimary = Color.WHITE;
        Color textSecondary = new Color(190, 190, 210);

        getContentPane().setBackground(bg);

        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 22));
        jLabel1.setForeground(textPrimary);

        jLabel2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jLabel2.setForeground(textSecondary);

        jLabel3.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jLabel3.setForeground(textSecondary);

        jProgressBar1.setForeground(accent);
        jProgressBar1.setBackground(new Color(50, 50, 80));
        jProgressBar1.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

        styleButton(jButton1, accent, bg);
        styleButton(jButton2, accent, bg);
        styleButton(jButton3, accent, bg);
    }

    private void styleButton(JButton btn, Color accent, Color bg) {
        btn.setBackground(accent);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setBorder(new LineBorder(accent.darker(), 1, true));
        btn.setContentAreaFilled(true);
    }
private void configureLayout() {
    // Root panel with vertical layout
    JPanel root = new JPanel();
    root.setBackground(new Color(20, 20, 35));
    root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));
    root.setBorder(new EmptyBorder(40, 40, 40, 40));

    // Center all components horizontally
    jLabel1.setAlignmentX(Component.CENTER_ALIGNMENT);
    jLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
    jLabel3.setAlignmentX(Component.CENTER_ALIGNMENT);
    jProgressBar1.setAlignmentX(Component.CENTER_ALIGNMENT);
    jButton1.setAlignmentX(Component.CENTER_ALIGNMENT);
    jButton2.setAlignmentX(Component.CENTER_ALIGNMENT);
    jButton3.setAlignmentX(Component.CENTER_ALIGNMENT);

    // Build vertical ordering with spacing
    root.add(jLabel1);
    root.add(Box.createVerticalStrut(60));

    root.add(jLabel3); // Waste
    root.add(Box.createVerticalStrut(5));
    root.add(jLabel2); // Productive
    root.add(Box.createVerticalStrut(10));
    root.add(jProgressBar1);

    root.add(Box.createVerticalStrut(60));

    root.add(jButton3); // Start/Stop
    root.add(Box.createVerticalStrut(10));
    root.add(jButton1); // Detailed stats
    root.add(Box.createVerticalStrut(10));
    root.add(jButton2); // Today vs Yesterday

    setContentPane(root);   // replace the old GroupLayout content pane
    pack();
}
private void applyTheme() {
    Color bg = new Color(20, 20, 35);
    Color accent = new Color(88, 101, 242);
    Color textPrimary = new Color(240, 240, 255);
    Color textSecondary = new Color(200, 200, 210);

    getContentPane().setBackground(bg);

    jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 22));
    jLabel1.setForeground(textPrimary);

    jLabel2.setFont(new Font("Segoe UI", Font.BOLD, 14));
    jLabel2.setForeground(textSecondary);

    jLabel3.setFont(new Font("Segoe UI", Font.BOLD, 14));
    jLabel3.setForeground(textSecondary);

    jProgressBar1.setForeground(accent);
    jProgressBar1.setBackground(new Color(50, 50, 80));
    jProgressBar1.setStringPainted(true);
    jProgressBar1.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
    jProgressBar1.setFont(new Font("Segoe UI", Font.BOLD, 12));

    styleButton(jButton1, accent);
    styleButton(jButton2, accent);
    styleButton(jButton3, accent);
}

private void styleButton(JButton btn, Color accent) {
    btn.setOpaque(true);
    btn.setBackground(accent);
    btn.setForeground(Color.BLACK);
    btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
    btn.setFocusPainted(false);
    btn.setBorder(BorderFactory.createEmptyBorder(6, 16, 6, 16));
}

    public MainFrame() {
    initComponents();          // NetBeans creates all components

    setLocationRelativeTo(null);

    // build our own layout instead of the auto GroupLayout
    configureLayout();
    applyTheme();
    setSize(500, 500);                          // overall window size
    setMinimumSize(new java.awt.Dimension(500, 530));  // don't let it shrink smaller
    jButton3.setText("Start Tracking");
    jButton2.setText("Today vs Yesterday");

    // tracker + callbacks
    tracker = new ActivityTracker(records -> {
        currentRecords = records;
        javax.swing.SwingUtilities.invokeLater(this::updateDashboard);
    });

    jButton2.addActionListener(evt -> openTodayYesterday());
}



private void updateDashboard() {

        long productiveSeconds = 0;
        long wasteSeconds = 0;

        for (ActivityRecord rec : currentRecords) {
            long dur = rec.getDurationInSeconds();

            AppCategory cat = rec.getCategory();
            if (cat == null) continue;

            switch (cat) {
                case PRODUCTIVE:
                    productiveSeconds += dur;
                    break;
                case WASTE:
                    wasteSeconds += dur;
                    break;
                default:
                    break;
            }
        }

        long total = productiveSeconds + wasteSeconds;
        int score = (total == 0) ? 0 : (int) (100 * productiveSeconds / total);

        jLabel2.setText("Productive Time Today: " + productiveSeconds + " sec");
        jLabel3.setText("Waste Time Today: " + wasteSeconds + " sec");


        jProgressBar1.setValue(score);
        jProgressBar1.setString(score + "% productive");
    }
    private void openTodayYesterday() {
        ActivityStorage.DailySummary today =
                ActivityStorage.loadDailySummary(LocalDate.now());
        ActivityStorage.DailySummary yesterday =
                ActivityStorage.loadDailySummary(LocalDate.now().minusDays(1));

        TodayYesterdayFrame frame = new TodayYesterdayFrame(today, yesterday);
        frame.setVisible(true);
    }




    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        buttonGroup5 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jProgressBar1 = new javax.swing.JProgressBar();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Procastination Killer");

        jLabel2.setText("Productive Time Today: 0 min");

        jLabel3.setText("Waste Time Today: 0 min");

        jButton1.setText("View Detailed Stats");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Today Vs Yesterday");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Start Tracking / Stop Tracking");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(140, 140, 140)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(65, 65, 65)
                                .addComponent(jButton2))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(201, 201, 201)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(182, 182, 182)
                        .addComponent(jButton3))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(220, 220, 220)
                        .addComponent(jLabel1)))
                .addContainerGap(160, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addGap(130, 130, 130)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 127, Short.MAX_VALUE)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
         StatsFrame stats = new StatsFrame(currentRecords);
         stats.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if (!tracking) {
        tracking = true;
        jButton3.setText("Stop Tracking");
        tracker.start();
    } else {
        tracking = false;
        jButton3.setText("Start Tracking");
        tracker.stop();

        // Save current session to /logs
        ActivityStorage.saveSession(currentRecords);

        JOptionPane.showMessageDialog(
                this,
                "Session saved in the 'logs' folder.",
                "Session Saved",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.ButtonGroup buttonGroup5;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JProgressBar jProgressBar1;
    // End of variables declaration//GEN-END:variables
}
