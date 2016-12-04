package com.eeyuva.screens.authentication;


import android.content.Intent;
import android.util.Log;

import com.eeyuva.R;
import com.eeyuva.base.BaseView;
import com.eeyuva.base.LoadListener;
import com.eeyuva.interactor.ApiInteractor;
import com.eeyuva.utils.preferences.PrefsManager;


/**
 * Created by hari on 22/6/16.
 */
public class LoginPresenterImpl implements LoginContract.Presenter {

    PrefsManager mPrefsManager;

    LoginContract.View mView;

    ApiInteractor mDriverInteractor;

    public LoginPresenterImpl(ApiInteractor mDriverInteractor, PrefsManager mPrefsManager) {
        this.mDriverInteractor = mDriverInteractor;
        this.mPrefsManager = mPrefsManager;
    }

    public LoadListener<LoginResponse> mLoginListener = new LoadListener<LoginResponse>() {
        @Override
        public void onSuccess(LoginResponse responseBody) {
            try {
                if (responseBody.getStatusCode() != null) {
                    mPrefsManager.setUserDetail(responseBody);
                    mView.movetoHome();
                } else
                    mView.showErrorDialog(responseBody.getStatusInfo());
            } catch (Exception e) {
                e.printStackTrace();
                mView.showErrorDialog(responseBody.getStatusInfo());
            }
        }

        @Override
        public void onFailure(Throwable t) {

        }

        @Override
        public void onError(Object error) {
            mPrefsManager.setAccessToken("");
        }
    };

    @Override
    public void onLoginClicked() {
        String name = mView.getUsername();
        String pass = mView.getPassword();
        if (validateAuthentication(name, pass)) {
            mDriverInteractor.getLoginResponse(mView, name, pass, mPrefsManager.getAccessToken(), mLoginListener);
        }
    }

    @Override
    public boolean validateAuthentication(String name, String pass) {
        if (!validateUsername(name))
            return false;
        else if (!validatePassword(pass))
            return false;
        return true;
    }

    @Override
    public boolean validateUsername(String name) {
        if (name.length() == 0) {
            mView.showErrorDialog(R.string.enter_user_name);
            return false;
        } else if (name.length() <= 2) {
            mView.showErrorDialog(R.string.enter_min_character_user_name);
            return false;
        }
        return true;
    }

    @Override
    public boolean validatePassword(String pass) {
        if (pass.length() == 0) {
            mView.showErrorDialog(R.string.enter_password);
            return false;
        } else if (pass.length() <= 2) {
            mView.showErrorDialog(R.string.enter_min_character_password);
            return false;
        }
        return true;
    }

    @Override
    public void onSignupClick() {
        mView.movetoSignUp();
    }

//    public LoadListener<FCMRegisterResponse> mUpdateListener = new LoadListener<FCMRegisterResponse>() {
//        @Override
//        public void onSuccess(FCMRegisterResponse responseBody) {
//        }
//
//        @Override
//        public void onFailure(Throwable t) {
//        }
//
//        @Override
//        public void onError(Object error) {
//
//        }
//    };

    @Override
    public void setView(BaseView view) {
        mView = (LoginContract.View) view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

//    public void updateTokenToServer(String token) {
//        if (mPrefsManager.getAccessToken() != null) {
//            mPrefsManager.setFcmToken(token);
//            FCMRegisterRequest request = new FCMRegisterRequest();
//            request.setDeviceId(token);
//            mDriverInteractor.updateFCmToken(mUpdateListener, request);
//        }
//
//    }
}