package com.itesm.labs.application;

import com.itesm.labs.BuildConfig;

/**
 * Created by mgradob on 11/2/15.
 */
public class AppConstants {

    public static final String ENDPOINT = BuildConfig.BASE_API_URL;

    /**
     * Shared preferences keys.
     */
    public static final String PREFERENCES_FILE_NAME = "labs_user_preferences";
    public static final String PREFERENCES_KEY_USER_ID = "userid";
    public static final String PREFERENCES_KEY_USER_PASS = "userpass";
    public static final String PREFERENCES_KEY_USER_TOKEN = "token";
}
