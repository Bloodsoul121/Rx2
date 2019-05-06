package com.blood.rx2.main;

import android.util.Log;

/**
 *  统一日志输出
 */
public class LLog {

    private static final String TAG = "LLOG ---> ";

    private static final boolean sIsRelease = false;

    public static void v(String msg) {
        log("Verbose", msg);
    }

    public static void d(String msg) {
        log("Debug", msg);
    }

    public static void i(String msg) {
        log("Info", msg);
    }

    public static void w(String msg) {
        log("Warn", msg);
    }

    public static void e(String msg) {
        log("Error", msg);
    }

    private static void log(String info, String msg) {
        if (sIsRelease) {
            return;
        }
        switch (info) {
            case "Verbose":
                Log.v(TAG, msg);
                break;
            case "Debug":
                Log.d(TAG, msg);
                break;
            case "Info":
                Log.i(TAG, msg);
                break;
            case "Warn":
                Log.w(TAG, msg);
                break;
            case "Error":
                Log.e(TAG, msg);
                break;
        }
    }

}
