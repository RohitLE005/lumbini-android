package com.phoenixmarketcity.android.phoenix.util;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.phoenixmarketcity.android.phoenix.R;

/**
 * Stores and manages application configuration.
 */
public class ConfigManager {
    private boolean mIsDevelopmentMode = false;
    private boolean mIsBackendEnvProduction = false;
    private boolean mIsStrictModeEnabled = false;
    private String mBaseUrl = "http://admin.demo.phoenixmarketcity.com/phoenix/api";
    private String mBaseUserApiUrl = "http://userservice.demo.phoenixmarketcity.com:5000";
    private int mMallIndex = 1;
    private String mResolutionString = null;

    private static class Holder {
        static final ConfigManager INSTANCE = new ConfigManager();
    }

    // Returns singleton instance
    public static ConfigManager getInstance() {
        return Holder.INSTANCE;
    }

    // Set debug mode
    public void setDevelopmentMode(boolean debugMode) {
        mIsDevelopmentMode = debugMode;
    }

    // Returns true if app is running in debug mode
    public boolean isDevelopmentMode() {
        return mIsDevelopmentMode;
    }

    // Sets backend environment
    public void setBackendEnv(boolean isProduction) {
        mIsBackendEnvProduction = isProduction;
    }

    // Returns true if backend env is production
    public boolean useProductionEnv() {
        return mIsBackendEnvProduction;
    }

    // Returns Base URL used for the app
    public String getBaseUrl() { return mBaseUrl; }

    // Returns base URL used for the app
    public void setBaseUrl(String url) { mBaseUrl = url; }

    // Returns Base login API URL for the app
    public String getBaseUserApiUrl() { return mBaseUserApiUrl; }

    // Get Base City
    public int getDefaultMallIndex() {
        return mMallIndex;
    }

    //  Set City Index
    public void setCityIndex(int mallIndex) {
        mMallIndex = mallIndex;
    }

    // Sets logging level
    public void setLoggingLevel(int level) {
        Logger.setLoggingLevel(level);
    }

    // Gets logging level
    public int getLoggingLevel() {
        return Logger.getLoggingLevel();
    }

    // Returns true if strict mode is enabled
    public boolean isStrictModeEnabled() {
        if (!mIsDevelopmentMode) return false;
        return mIsStrictModeEnabled;
    }

    // Sets strict mode flag
    public void setStrictModeEnabled(final boolean enabled) {
        mIsStrictModeEnabled = enabled;
    }

    // Get screen resoultion string for backend API
    public String getScreenResolution(Resources rsc) {
        if (null != mResolutionString) {
            return mResolutionString;
        }

        int density = rsc.getDisplayMetrics().densityDpi;
        switch(density) {
            case DisplayMetrics.DENSITY_HIGH:
                mResolutionString = rsc.getString(R.string.density_high_string);
                break;
            case DisplayMetrics.DENSITY_XHIGH:
                mResolutionString = rsc.getString(R.string.density_extra_high_string);
                break;
            case DisplayMetrics.DENSITY_XXHIGH:
                mResolutionString = rsc.getString(R.string.density_exxtra_high_string);
                break;
            default:
                mResolutionString = rsc.getString(R.string.density_default_string);
                break;
        }
        return mResolutionString;
    }

}
