package com.eeyuva.utils.preferences;


import com.eeyuva.screens.authentication.LoginResponse;
import com.google.android.gms.maps.model.LatLng;

public interface PrefsManager {

    int NO_USER_ID = -1;

    String SHARED_PREFERENCES_KEY = "com.eeyuva.shared_preferences";

    String SHARED_PREFERENCES_REMOTE_KEY = "fetchr";
    String PREFS_ACCESS_TOKEN = "UserAuthToken";
    String PREFS_USER_NAME = "UserName";
    String PREFS_USER_DETAILS = "UserName_Detail";
    String PREFS_FCM_TOKEN = "fcm_token";
    String SHARED_LOCATION_LAT = "lat";
    String SHARED_LOCATION_LNG = "lng";

    String getAccessToken();

    void setAccessToken(String token);


    void setFcmToken(String token);

    String getFcmToken();

    LatLng getLastLocation();

    void setLastLocation(LatLng clientLocation);

    void setUserDetail(LoginResponse responseBody);
    LoginResponse getUserDetails();
}
