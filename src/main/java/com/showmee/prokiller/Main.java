package com.showmee.prokiller;

import com.showmee.prokiller.ui.MainFrame;

public class Main {

    public static void main(String[] args) {
        // Make UI look better (optional)
        try {
            javax.swing.UIManager.setLookAndFeel(
                javax.swing.UIManager.getSystemLookAndFeelClassName()
            );
        } catch (Exception e) {
            // ignore
        }

        // Start UI on the Event Dispatch Thread
        javax.swing.SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setLocationRelativeTo(null); // center on screen
            frame.setVisible(true);
        });
    }
}
