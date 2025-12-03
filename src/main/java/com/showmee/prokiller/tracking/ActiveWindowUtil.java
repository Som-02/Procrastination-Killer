/**
 * Procrastination Killer
 * Created by: Showmee
 * Year: 2025
 *
 * This project is developed by Showmee. All rights reserved.
 */
package com.showmee.prokiller.tracking;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;

public class ActiveWindowUtil {

    private static final int MAX_TITLE_LENGTH = 1024;

    public static ActiveWindowInfo getActiveWindow() {
        // Get the foreground window handle
        HWND hwnd = User32.INSTANCE.GetForegroundWindow();
        if (hwnd == null) {
            return new ActiveWindowInfo("", "");
        }

        // Read the window title
        char[] buffer = new char[MAX_TITLE_LENGTH];
        User32.INSTANCE.GetWindowText(hwnd, buffer, MAX_TITLE_LENGTH);
        String title = Native.toString(buffer);

        // For now, we don't resolve process name – just use empty string
        String processName = "";

        return new ActiveWindowInfo(title, processName);
    }
}

