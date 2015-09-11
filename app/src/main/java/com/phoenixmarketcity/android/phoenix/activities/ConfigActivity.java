package com.phoenixmarketcity.android.phoenix.activities;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ConfigurationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.phoenixmarketcity.android.phoenix.R;
import com.phoenixmarketcity.android.phoenix.util.ConfigManager;

/**
 * Config activity for runtime app configuration.
 * Relevant only in debug mode.
 */
public class ConfigActivity extends Activity {
    // View components
    private CheckBox mStrictModeCheckBox;
    private EditText mBaseUrlString;
    private Spinner mEnvironmentSpinner, mLoggingLevelSpinner, mBaseCitySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_config);

        // Initialize UI
        findViewById(R.id.config_launch_app_button).setOnClickListener(mOnLaunchAppClickListener);
        initVersionDetails();
        initDensityResolution();
        initDeviceSize();
        initOpenGLS();
        initEnvironmentSpinner();
        initBaseURL();
        initBaseCity();
        initStrictModeCheckbox();
        initLoggingLevelSpinner();
    }

    // Click listener
    private final View.OnClickListener mOnLaunchAppClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            // Remember Backend Environment
            ConfigManager.getInstance().setBackendEnv(
                    mEnvironmentSpinner.getSelectedItemPosition() == 1);

            ConfigManager.getInstance().setBaseUrl(mBaseUrlString.getText().toString());
            ConfigManager.getInstance().setCityIndex(mBaseCitySpinner.getSelectedItemPosition());

            // Remember strict mode selection
            ConfigManager.getInstance().setStrictModeEnabled(mStrictModeCheckBox.isChecked());
            if (mStrictModeCheckBox.isChecked()) {
                StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads()
                        .detectDiskWrites().detectNetwork().penaltyLog()
                        .penaltyFlashScreen().build());
                StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                        .detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog()
                        .penaltyDeath().build());
            }

            // set logging level
            ConfigManager.getInstance().setLoggingLevel(
                    mLoggingLevelSpinner.getSelectedItemPosition());

            // Keep a progress dialog over UI for a few seconds, give us a
            // chance to initialize.
            final ProgressDialog dialog = ProgressDialog.show(ConfigActivity.this, null, "Loading...");
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }, 3000);
        }
    };

    // Update version details to UI
    private void initVersionDetails() {
        try {
            final int code = getPackageManager().getPackageInfo(
                    getPackageName(), 0).versionCode;
            final String name = getPackageManager().getPackageInfo(
                    getPackageName(), 0).versionName;

            ((TextView) findViewById(R.id.config_version_name_number_text)).setText(name + "/" + String.valueOf(code));
        } catch (final PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Update device screen density and resolution
    private void initDensityResolution() {
        int density = getResources().getDisplayMetrics().densityDpi;
        String resol = "";
        switch(density) {
            case DisplayMetrics.DENSITY_LOW:
                resol = "LDPI";
                break;
            case DisplayMetrics.DENSITY_MEDIUM:
                resol = "MDPI";
                break;
            case DisplayMetrics.DENSITY_HIGH:
                resol = "HDPI";
                break;
            case DisplayMetrics.DENSITY_XHIGH:
                resol = "XHDPI";
                break;
            case DisplayMetrics.DENSITY_XXHIGH:
                resol = "XXHDPI";
                break;
            case DisplayMetrics.DENSITY_XXXHIGH:
                resol = "XXXHDPI";
                break;
        }

        ((TextView) findViewById(R.id.config_device_resolution)).setText(density + "/" + resol);
    }

    // Update device size
    private void initDeviceSize() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        ((TextView) findViewById(R.id.config_device_size_px)).setText(dm.widthPixels + "/" + dm.heightPixels);
    }

    // Update Backend Environment to UI
    private void initEnvironmentSpinner() {
        mEnvironmentSpinner = (Spinner) findViewById(R.id.config_environment_spinner);
        mEnvironmentSpinner.setSelection(ConfigManager.getInstance().useProductionEnv()
            ? 1 : 0);
    }

    // Update OpenGL version to UI
    private void initOpenGLS() {
        final ActivityManager activityManager =
                (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        final ConfigurationInfo configInfo =
                activityManager.getDeviceConfigurationInfo();
        if (!TextUtils.isEmpty(configInfo.getGlEsVersion().toString())) {
            ((TextView) findViewById(R.id.config_open_gls_version)).setText(configInfo
                    .getGlEsVersion().toString());
        }
    }

    private void initBaseURL() {
        mBaseUrlString = (EditText) findViewById(R.id.config_base_url_edittext);
        mBaseUrlString.setText(ConfigManager.getInstance().getBaseUrl());
    }

    private void initBaseCity() {
        mBaseCitySpinner = (Spinner) findViewById(R.id.config_location_spinner);
        mBaseCitySpinner.setSelection(ConfigManager.getInstance().getDefaultMallIndex());
    }

    // Update Strict mode to UI
    private void initStrictModeCheckbox() {
        final boolean strictModeEnabled = ConfigManager.getInstance().isStrictModeEnabled();
        mStrictModeCheckBox = (CheckBox) findViewById(R.id.config_strict_mode_checkbox);
        mStrictModeCheckBox.setChecked(strictModeEnabled);
    }

    // Update log level to UI
    private void initLoggingLevelSpinner() {
        mLoggingLevelSpinner = (Spinner) findViewById(R.id.confing_logging_level_spinner);
        mLoggingLevelSpinner.setSelection(ConfigManager.getInstance().getLoggingLevel());
    }
}
