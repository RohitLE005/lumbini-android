package com.phoenixmarketcity.android.phoenix.util;

import com.google.gson.annotations.SerializedName;
import com.phoenixmarketcity.android.phoenix.webservices.WebService;

import org.parceler.Parcel;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by neeraj.nayan on 17/08/15.
 */
public class User {

    public static void login(String username, String password) {
        UserAccountRequest request = new UserAccountRequest();
        request.mUsername = username;
        request.mPassword = password;
        WebService.getInstance().login(request, new Callback<UserAccountResponse>() {
            @Override
            public void success(UserAccountResponse userAccountResponse, Response response) {
                Preferences.getInstance().setUserId(userAccountResponse.mUserId);
                Preferences.getInstance().setAccessToken(userAccountResponse.mAccessToken);
            }

            @Override
            public void failure(RetrofitError error) {
                Logger.d("Login failed with error: " + error.getMessage());
            }
        });
    }

    // Register a new user
    public static void register(String username, String password) {
        UserAccountRequest request = new UserAccountRequest();
        request.mUsername = username;
        request.mPassword = password;
        WebService.getInstance().register(request, new Callback<UserAccountResponse>() {
            @Override
            public void success(UserAccountResponse userAccountResponse, Response response) {
                Preferences.getInstance().setUserId(userAccountResponse.mUserId);
                Preferences.getInstance().setAccessToken(userAccountResponse.mAccessToken);
            }

            @Override
            public void failure(RetrofitError error) {
                Logger.d("Login failed with error: " + error.getMessage());
            }
        });
    }

    // User API request object
    @Parcel
    public static class UserAccountRequest {
        @SerializedName("username")
        public String mUsername;
        @SerializedName("password")
        public String mPassword;
        @SerializedName("lastKnownID")
        public String mLastKnownId;
    }

    // User API response object
    public static class UserAccountResponse {
        @SerializedName("userID")
        public String mUserId;
        @SerializedName("access_token")
        public String mAccessToken;
    }
}
