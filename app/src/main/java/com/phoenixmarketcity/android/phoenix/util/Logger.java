
package com.phoenixmarketcity.android.phoenix.util;

import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;

public final class Logger {
    public static final int NONE = 0;
    private static final int ERRORS_ONLY = 1;
    private static final int ERRORS_WARNINGS = 2;
    private static final int ERRORS_WARNINGS_INFO = 3;
    public static final int ERRORS_WARNINGS_INFO_DEBUG = 4;
    // Logging level in this app
    private static int APP_LOGGING_LEVEL = NONE;

    private static final String TAG = "Phoenix";
    private static final String TAG_GENERAL_OUTPUT = "Logger";

    static {
        i("Log class reloaded");
    }

    public static void setLoggingLevel(int logLevel) {
        APP_LOGGING_LEVEL = logLevel;
    }

    public static int getLoggingLevel() {
        return APP_LOGGING_LEVEL;
    }

    public static void e(final Object obj, final Throwable cause) {
        if (isELoggable()) {
            Log.e(TAG, getTrace() + String.valueOf(obj));
            Log.e(TAG, getThrowableTrace(cause));
            Log.e(TAG_GENERAL_OUTPUT, getTrace() + String.valueOf(obj));
            Log.e(TAG_GENERAL_OUTPUT, getThrowableTrace(cause));
        }
    }

    public static void e(final Object obj) {
        if (isELoggable()) {
            Log.e(TAG, getTrace() + String.valueOf(obj));
            Log.e(TAG_GENERAL_OUTPUT, getTrace() + String.valueOf(obj));
        }
    }

    public static void w(final Object obj, final Throwable cause) {
        if (isQLoggable()) {
            Log.w(TAG, getTrace() + String.valueOf(obj));
            Log.w(TAG, getThrowableTrace(cause));
            Log.w(TAG_GENERAL_OUTPUT, getTrace() + String.valueOf(obj));
            Log.w(TAG_GENERAL_OUTPUT, getThrowableTrace(cause));
        }
    }

    public static void w(final Object obj) {
        if (isQLoggable()) {
            Log.w(TAG, getTrace() + String.valueOf(obj));
            Log.w(TAG_GENERAL_OUTPUT, getTrace() + String.valueOf(obj));
        }
    }

    public static void i(final Object obj) {
        if (isILoggable()) {
            Log.i(TAG, getTrace() + String.valueOf(obj));
        }
    }

    public static void i(final Object obj, final Object obj2) {
        if (isILoggable()) {
            Log.i(TAG, String.valueOf(obj) + ": " + String.valueOf(obj2));
        }
    }

    public static void d(final Object obj) {
        if (isDLoggable()) {
            Log.d(TAG, getTrace() + String.valueOf(obj));
        }
    }

    public static void v(final Object obj) {
        if (isVLoggable()) {
            Log.v(TAG, getTrace() + String.valueOf(obj));
        }
    }

    // Prints the stack trace to standard log
    public static void handleException(final Exception e) {
        Logger.e(e.toString());
        e.printStackTrace();
    }

    public static boolean isVLoggable() {
        return APP_LOGGING_LEVEL > ERRORS_WARNINGS_INFO_DEBUG;
    }

    public static boolean isDLoggable() {
        return APP_LOGGING_LEVEL > ERRORS_WARNINGS_INFO;
    }

    public static boolean isILoggable() {
        return APP_LOGGING_LEVEL > ERRORS_WARNINGS;
    }

    public static boolean isQLoggable() {
        return APP_LOGGING_LEVEL > ERRORS_ONLY;
    }

    public static boolean isELoggable() {
        return APP_LOGGING_LEVEL > NONE;
    }

    private static String getThrowableTrace(final Throwable thr) {
        final StringWriter b = new StringWriter();
        thr.printStackTrace(new PrintWriter(b));
        return b.toString();
    }

    private static String getTrace() {
        final int depth = 2;
        final Throwable t = new Throwable();
        final StackTraceElement[] elements = t.getStackTrace();
        final String callerMethodName = elements[depth].getMethodName();
        final String callerClassPath = elements[depth].getClassName();
        final int lineNo = elements[depth].getLineNumber();
        final int i = callerClassPath.lastIndexOf('.');
        final String callerClassName = callerClassPath.substring(i + 1);
        final String trace = callerClassName + ": " + callerMethodName + "() [" + lineNo + "] - ";
        return trace;
    }

    private Logger() {
    }
}
