package com.eeyuva.screens.splash;

import android.content.Intent;

import com.eeyuva.interactor.ApiInteractor;
import com.eeyuva.utils.preferences.PrefsManager;
import com.eeyuva.base.BaseView;

/**
 * Created by kavi on 18/07/16.
 */
public class SplashPresenterImpl implements SplashContract.Presenter {

    PrefsManager mPrefsManager;

    SplashContract.View mView;

    ApiInteractor mDriverInteractor;



    public SplashPresenterImpl(ApiInteractor driverInteractor, PrefsManager prefsManager) {
        this.mDriverInteractor = driverInteractor;
        this.mPrefsManager = prefsManager;
    }

    @Override
    public void moveForward() {
        if (mPrefsManager.getUserDetails().getUserid() != null)
            mView.moveToDashboard();
        else
            mView.moveToLogin();
    }

    @Override
    public void setView(BaseView view) {
        mView = (SplashContract.View) view;
        mView.setVersionNo();
        mView.setLoadAnim();
        moveForward();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }



}
