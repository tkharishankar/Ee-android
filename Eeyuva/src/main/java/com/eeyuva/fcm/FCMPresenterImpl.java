package com.eeyuva.fcm;

import android.util.Log;

import com.eeyuva.base.LoadListener;
import com.eeyuva.fcmregister.FCMRegisterRequest;
import com.eeyuva.fcmregister.FCMRegisterResponse;
import com.eeyuva.interactor.ApiInteractor;
import com.eeyuva.utils.preferences.PrefsManager;

/**
 * Created by kavi on 16/08/16.
 */
public class FCMPresenterImpl implements FCMContract.Presenter{

    ApiInteractor driverInteractor;

    PrefsManager preferenceManager;

    public FCMPresenterImpl(ApiInteractor driverInteractor, PrefsManager preferenceManager) {
        this.driverInteractor = driverInteractor;
        this.preferenceManager = preferenceManager;

    }

    @Override
    public void updateTokenToServer(String token) {
        preferenceManager.setFcmToken(token);
        if(preferenceManager.getAccessToken()!=null && preferenceManager.getUserDetails()!=null) {
            FCMRegisterRequest request = new FCMRegisterRequest();
            request.setDeviceId(token);
//            driverInteractor.updateFCmToken(updateListener,request);
        }

    }

    public LoadListener<FCMRegisterResponse> updateListener = new LoadListener<FCMRegisterResponse>() {
        @Override
        public void onSuccess(FCMRegisterResponse responseBody) {
            Log.d("Update","update has been successful :) ");
        }

        @Override
        public void onFailure(Throwable t) {
            Log.e("Update","update has been Failed :) ");
        }

        @Override
        public void onError(Object error) {
            Log.d("Update","update has been failed in server :) ");

        }
    };

}
