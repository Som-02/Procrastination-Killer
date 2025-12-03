/**
 * Procrastination Killer
 * Created by: Showmee
 * Year: 2025
 *
 * This project is developed by Showmee. All rights reserved.
 */
package com.showmee.prokiller.tracking;

public class ActiveWindowInfo {
    private final String windowTitle;
    private final String processName;

    public ActiveWindowInfo(String windowTitle, String processName) {
        this.windowTitle = windowTitle;
        this.processName = processName;
    }

    public String getWindowTitle() {
        return windowTitle;
    }

    public String getProcessName() {
        return processName;
    }

    @Override
    public String toString() {
        return "ActiveWindowInfo{" +
                "windowTitle='" + windowTitle + '\'' +
                ", processName='" + processName + '\'' +
                '}';
    }
}

