package com.eeyuva.screens.splash;

import android.content.Intent;
import android.os.Build;

import com.eeyuva.BuildConfig;
import com.eeyuva.utils.preferences.PrefsManager;
import com.eeyuva.interactor.DriverInteractor;
import com.eeyuva.base.BaseView;
import com.eeyuva.base.LoadListener;
import com.eeyuva.utils.Constants;
import com.eeyuva.utils.Utils;

/**
 * Created by kavi on 18/07/16.
 */
public class SplashPresenterImpl implements SplashContract.Presenter {

    PrefsManager mPrefsManager;

    SplashContract.View mView;

    DriverInteractor mDriverInteractor;



    public SplashPresenterImpl(DriverInteractor driverInteractor, PrefsManager prefsManager) {
        this.mDriverInteractor = driverInteractor;
        this.mPrefsManager = prefsManager;
    }

    @Override
    public void moveForward() {
        if (mPrefsManager.getAccessToken() != null && mPrefsManager.getAccessToken().length() > 0)
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
