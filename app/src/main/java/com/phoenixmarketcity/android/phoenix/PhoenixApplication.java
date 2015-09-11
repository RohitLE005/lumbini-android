
package com.phoenixmarketcity.android.phoenix;

import android.app.Application;
import android.content.Intent;
import android.content.res.AssetManager;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;
import com.kontakt.sdk.android.ble.util.BluetoothUtils;
import com.kontakt.sdk.android.common.KontaktSDK;
import com.kontakt.sdk.android.common.log.LogLevel;
import com.phoenixmarketcity.android.phoenix.beacon.BeaconService;
import com.phoenixmarketcity.android.phoenix.push.RegistrationIntentService;
import com.phoenixmarketcity.android.phoenix.util.ConfigManager;
import com.phoenixmarketcity.android.phoenix.util.Logger;
import com.phoenixmarketcity.android.phoenix.util.Preferences;
import com.phoenixmarketcity.android.phoenix.util.User;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import org.parceler.Parcel;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import io.fabric.sdk.android.Fabric;

/**
 * Phoenix Application Class
 * Store key global variables and initialize them here.
 * Also manage app startup from here.
 */
public final class PhoenixApplication extends Application {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "7RjOaWvZJCM3COaRoSUbpKQ8n";
    private static final String TWITTER_SECRET = "18wVdMyc0C2SpjQuYAOhHLokAl5b9tdB80P5M7BZDfPfkC5vvJ";

    private static final String TAG = "PhoenixApplication";
    // Build configurations property file.
    private static final String BUILD_CONFIGURATION_PROPERTIES_FILE = "config.properties";
    private static final String BUILD_CONFIGURATION_PROPERTY_DEVELOPMENT_SETTINGS = "developmentsettings";
    private static final String BUILD_CONFIGURATION_PROPERTY_VALUE_FALSE = "false";
    private static final String BUILD_CONFIGURATION_PROPERTY_DEFAULT_LOG_LEVEL = "logLevel";
    private static final String BUILD_CONFIGURATION_PROPERTY_PRODUCTION_EVNIRONMENT = "backendEnvironment";

    private static PhoenixApplication mSelf;

    // Constructor
    public PhoenixApplication() {
        mSelf = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        readBuildConfigurations();

        if(BluetoothUtils.isBluetoothLeSupported(getApplicationContext())) {
            KontaktSDK.initialize(this)
                    .setDebugLoggingEnabled(BuildConfig.DEBUG)
                    .setLogLevelEnabled(LogLevel.DEBUG, true);

            // Start beacon service if BT is enabled
            if (BluetoothUtils.isBluetoothEnabled()) {
                Intent intent = new Intent(this, BeaconService.class);
                startService(intent);
            }
        }

        registerUser();
        registerForPushNotification();
    }

    // Returns a reference to self;
    public static PhoenixApplication getInstance() {
        return mSelf;
    }

    private void registerUser() {
        // If not already registered
        if (TextUtils.isEmpty(Preferences.getUserId())) {
            User.register(null, null);
        }
    }

    private void registerForPushNotification() {
        if (!Preferences.getGCMSentTokenToServer()) {
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }
    }

    // Read build configuration from assets/config.properties
    private void readBuildConfigurations() {
        AssetManager assetManager = getAssets();
        try {
            InputStream inputStream = assetManager.open(BUILD_CONFIGURATION_PROPERTIES_FILE);
            Properties properties = new Properties();
            properties.load(inputStream);

            // Read development Settings
            String dev_settings = properties.getProperty(
                    BUILD_CONFIGURATION_PROPERTY_DEVELOPMENT_SETTINGS,
                    BUILD_CONFIGURATION_PROPERTY_VALUE_FALSE);
            if (dev_settings.equalsIgnoreCase(BUILD_CONFIGURATION_PROPERTY_VALUE_FALSE)) {
                ConfigManager.getInstance().setDevelopmentMode(false);
            } else {
                ConfigManager.getInstance().setDevelopmentMode(true);
            }

            // Read and Set default log level settings
            String default_log_level = properties.getProperty(
                    BUILD_CONFIGURATION_PROPERTY_DEFAULT_LOG_LEVEL,
                    Integer.toString(Logger.ERRORS_WARNINGS_INFO_DEBUG));
            int log_level = Logger.NONE;
            try {
                log_level = Integer.parseInt(default_log_level);
            } catch (NumberFormatException nfe) {
                // we will just take Logger.NONE as the value.
            }
            ConfigManager.getInstance().setLoggingLevel(log_level);

            // Read production environment Setting
            String prod_Environment = properties.getProperty(
                    BUILD_CONFIGURATION_PROPERTY_PRODUCTION_EVNIRONMENT,
                    BUILD_CONFIGURATION_PROPERTY_VALUE_FALSE);
            if (prod_Environment.equalsIgnoreCase(BUILD_CONFIGURATION_PROPERTY_VALUE_FALSE)) {
                ConfigManager.getInstance().setBackendEnv(false);
            } else {
                ConfigManager.getInstance().setBackendEnv(true);
            }
            inputStream.close();
        } catch (IOException e) {
            Logger.e("Failed to load configuration", e);
        }
    }

    @Parcel
    public class UserRegisterRequest {
        @SerializedName("username")
        public String mUsername;
        @SerializedName("password")
        public String mPassword;
        @SerializedName("lastKnownID")
        public String mLastKnownId;
        @SerializedName("data")
        public UserData mUserData;

        public class UserData {
        }
    }

    @Parcel
    public class UserRegisterResponse {
        @SerializedName("userID")
        public String mUserId;
        @SerializedName("sessionID")
        public String mSessionId;
    }
}
