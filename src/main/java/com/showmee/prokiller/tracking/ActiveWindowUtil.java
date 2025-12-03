/**
 * Procrastination Killer
 * Created by: Somnath Pal
 * Year: 2025
 *
 * This project is developed by Somnath Pal. All rights reserved.
 */
package com.showmee.prokiller.tracking;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;

public class ActiveWindowUtil {

    private static final int MAX_TITLE_LENGTH = 1024;

    public static ActiveWindowInfo getActiveWindow() {
        HWND hwnd = User32.INSTANCE.GetForegroundWindow();
        if (hwnd == null) {
            return new ActiveWindowInfo("", "");
        }

        char[] buffer = new char[MAX_TITLE_LENGTH];
        User32.INSTANCE.GetWindowText(hwnd, buffer, MAX_TITLE_LENGTH);
        String title = Native.toString(buffer);

        String processName = "";

        return new ActiveWindowInfo(title, processName);
    }
}



