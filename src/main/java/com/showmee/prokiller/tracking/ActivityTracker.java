/**
 * Procrastination Killer
 * Created by: Somnath Pal
 * Year: 2025
 *
 * This project is developed by Somnath Pal. All rights reserved.
 */
package com.showmee.prokiller.tracking;

import com.showmee.prokiller.model.ActivityRecord;
import com.showmee.prokiller.model.AppCategory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ActivityTracker {

    private boolean running = false;
    private Thread workerThread;
    private final List<ActivityRecord> records = new ArrayList<>();
    private final Consumer<List<ActivityRecord>> onUpdateCallback;

    public ActivityTracker(Consumer<List<ActivityRecord>> onUpdateCallback) {
        this.onUpdateCallback = onUpdateCallback;
    }

    public void start() {
        if (running) return;
        running = true;
        workerThread = new Thread(this::loop, "ActivityTrackerThread");
        workerThread.setDaemon(true);
        workerThread.start();
    }

    public void stop() {
        running = false;
        if (workerThread != null) {
            workerThread.interrupt();
        }
    }

    private void loop() {
    final int INTERVAL_MS = 5000; 

    ActiveWindowInfo lastInfo = null;
    java.time.LocalDateTime lastTime = java.time.LocalDateTime.now();

    while (running) {
        try {
            Thread.sleep(INTERVAL_MS);
        } catch (InterruptedException e) {
            break;
        }

        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        ActiveWindowInfo currentInfo = ActiveWindowUtil.getActiveWindow();

        if (lastInfo != null) {
            AppCategory cat = AppCategorizer.categorize(
                    lastInfo.getProcessName(),
                    lastInfo.getWindowTitle()
            );

            ActivityRecord rec = new ActivityRecord(
                    lastTime,
                    now,
                    lastInfo.getWindowTitle(),
                    lastInfo.getProcessName(),
                    cat
            );

            synchronized (records) {
                records.add(rec);
                if (onUpdateCallback != null) {
                    onUpdateCallback.accept(new ArrayList<>(records));
                }
            }
        }

        lastInfo = currentInfo;
        lastTime = now;
    }
}



    public List<ActivityRecord> getRecords() {
        return new ArrayList<>(records);
    }
}



