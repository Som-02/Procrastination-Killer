/**
 * Procrastination Killer
 * Created by: Somnath Pal
 * Year: 2025
 *
 * This project is developed by Somnath Pal. All rights reserved.
 */
package com.showmee.prokiller.storage;

import com.showmee.prokiller.model.ActivityRecord;
import com.showmee.prokiller.model.AppCategory;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.List;

public class ActivityStorage {

    private static final String LOG_DIR_NAME = getDefaultLogDir();
    private static final DateTimeFormatter FILE_TS =
            DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
    private static String getDefaultLogDir() {
    String userHome = System.getProperty("user.home");
    return userHome + java.io.File.separator + "Documents"
            + java.io.File.separator + "ProcrastinationKiller"
            + java.io.File.separator + "logs";
}
    /**
     * Save one tracking session to a CSV file under /logs.
     */
    public static void saveSession(List<ActivityRecord> records) {
        if (records == null || records.isEmpty()) {
            return; // nothing to save
        }

        try {
            Path dir = Paths.get(LOG_DIR_NAME);
            if (!Files.exists(dir)) {
                Files.createDirectories(dir);
            }

            String ts = LocalDateTime.now().format(FILE_TS);
            Path file = dir.resolve("session-" + ts + ".csv");

            try (BufferedWriter bw = Files.newBufferedWriter(file, StandardCharsets.UTF_8)) {
                // header
                bw.write("start,end,windowTitle,processName,category,durationSeconds");
                bw.newLine();

                for (ActivityRecord r : records) {
                    String start = r.getStartTime() != null ? r.getStartTime().toString() : "";
                    String end = r.getEndTime() != null ? r.getEndTime().toString() : "";
                    String title = escape(r.getWindowTitle());
                    String process = escape(r.getProcessName());
                    AppCategory cat = r.getCategory();
                    long dur = r.getDurationInSeconds();

                    bw.write(start);
                    bw.write(",");
                    bw.write(end);
                    bw.write(",");
                    bw.write("\"" + title + "\"");
                    bw.write(",");
                    bw.write("\"" + process + "\"");
                    bw.write(",");
                    bw.write(cat != null ? cat.name() : "");
                    bw.write(",");
                    bw.write(Long.toString(dur));
                    bw.newLine();
                }
            }

            System.out.println("Session saved to: " + file.toAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // ---- Daily summary support ----

    public static class DailySummary {
        public long prodSec = 0;
        public long wasteSec = 0;
        public long neutralSec = 0;
    }

    public static DailySummary loadDailySummary(java.time.LocalDate date) {
        DailySummary summary = new DailySummary();

        Path dir = Paths.get(LOG_DIR_NAME);
        if (!Files.exists(dir) || !Files.isDirectory(dir)) {
            System.out.println("[ActivityStorage] No logs directory yet.");
            return summary;
        }

        try {
            Files.list(dir)
                    .filter(p -> p.toString().endsWith(".csv"))
                    .forEach(p -> accumulateFromFile(p, date, summary));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return summary;
    }

    private static void accumulateFromFile(Path file,
                                           java.time.LocalDate targetDate,
                                           DailySummary summary) {
        System.out.println("[ActivityStorage] Reading: " + file.toAbsolutePath());
        try (var lines = Files.lines(file)) {
            lines.skip(1).forEach(line -> {
                try {
                    // line format:
                    // start,end,"title","process",category,durationSeconds

                    int firstComma = line.indexOf(',');
                    if (firstComma < 0) return;

                    String startStr = line.substring(0, firstComma);
                    java.time.LocalDateTime startTime =
                            java.time.LocalDateTime.parse(startStr);

                    if (!startTime.toLocalDate().equals(targetDate)) {
                        return;
                    }

                    int lastComma = line.lastIndexOf(',');
                    if (lastComma < 0) return;
                    String durStr = line.substring(lastComma + 1).trim();
                    long durSec = Long.parseLong(durStr);

                    String rest = line.substring(0, lastComma);
                    int prevComma = rest.lastIndexOf(',');
                    if (prevComma < 0) return;
                    String categoryStr = rest.substring(prevComma + 1).trim();

                    // remove optional quotes
                    categoryStr = categoryStr.replace("\"", "");

                    AppCategory cat;
                    try {
                        cat = AppCategory.valueOf(categoryStr);
                    } catch (IllegalArgumentException ex) {
                        cat = AppCategory.NEUTRAL;
                    }

                    switch (cat) {
                        case PRODUCTIVE -> summary.prodSec += durSec;
                        case WASTE -> summary.wasteSec += durSec;
                        case NEUTRAL -> summary.neutralSec += durSec;
                    }

                } catch (Exception ex) {
                    // ignore bad line
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // keep this for compatibility if you ever want it
    public static void saveAll(List<ActivityRecord> records) {
        saveSession(records);
    }

    // escape quotes inside text for CSV
    private static String escape(String s) {
        if (s == null) return "";
        return s.replace("\"", "''");
    }
}


