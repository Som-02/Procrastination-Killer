/**
 * Procrastination Killer
 * Created by: Showmee
 * Year: 2025
 *
 * This project is developed by Showmee. All rights reserved.
 */
package com.showmee.prokiller.model;

import java.time.Duration;
import java.time.LocalDateTime;

public class ActivityRecord {

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String windowTitle;
    private String processName;
    private AppCategory category;   // <-- IMPORTANT: AppCategory, not int

    public ActivityRecord() {
    }

    public ActivityRecord(LocalDateTime startTime,
                          LocalDateTime endTime,
                          String windowTitle,
                          String processName,
                          AppCategory category) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.windowTitle = windowTitle;
        this.processName = processName;
        this.category = category;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getWindowTitle() {
        return windowTitle;
    }

    public void setWindowTitle(String windowTitle) {
        this.windowTitle = windowTitle;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public AppCategory getCategory() {       // <-- returns AppCategory
        return category;
    }

    public void setCategory(AppCategory category) {  // <-- takes AppCategory
        this.category = category;
    }

    public long getDurationInSeconds() {
        if (startTime == null || endTime == null) return 0;
        return Duration.between(startTime, endTime).getSeconds();
    }
}

