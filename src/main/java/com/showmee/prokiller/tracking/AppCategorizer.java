/**
 * Procrastination Killer
 * Created by: Showmee
 * Year: 2025
 *
 * This project is developed by Showmee. All rights reserved.
 */
package com.showmee.prokiller.tracking;

import com.showmee.prokiller.model.AppCategory;

public class AppCategorizer {

    public static AppCategory categorize(String processName, String windowTitle) {
        if (processName == null) processName = "";
        if (windowTitle == null) windowTitle = "";

        // Combine both, just in case processName is empty
        String text = (processName + " " + windowTitle).toLowerCase();

        // IDEs / coding / docs => PRODUCTIVE
        if (text.contains("netbeans") ||
            text.contains("intellij") ||
            text.contains("idea") ||
            text.contains("vscode") ||
            text.contains("visual studio code") ||
            text.contains("eclipse") ||
            text.contains("pycharm") ||
            text.contains(".java") ||
            text.contains(".py") ||
            text.contains(".js") ||
            text.contains(".html") ||
            text.contains("microsoft word") ||
            text.contains(".doc") ||
            text.contains(".ppt") ||
            text.contains("powerpoint")
        ) {
            return AppCategory.PRODUCTIVE;
        }

        // Learning sites => PRODUCTIVE
        if (text.contains("geeksforgeeks") ||
            text.contains("leetcode") ||
            text.contains("hackerrank") ||
            text.contains("coursera") ||
            text.contains("udemy") ||
            text.contains("nptel") ||
            text.contains("w3schools") ||
            text.contains("stackoverflow")
        ) {
            return AppCategory.PRODUCTIVE;
        }

        // Entertainment => WASTE
        if (text.contains("youtube") ||
            text.contains("netflix") ||
            text.contains("prime video") ||
            text.contains("instagram") ||
            text.contains("facebook") ||
            text.contains("reddit") ||
            text.contains("spotify") ||
            text.contains("vlc") ||
            text.contains("player") ||
            text.contains("anime") ||
            text.contains("game")
        ) {
            return AppCategory.WASTE;
        }

        // Default: neutral
        return AppCategory.NEUTRAL;
    }
}

