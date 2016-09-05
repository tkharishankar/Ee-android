package com.eeyuva.screens.registration;

import android.content.Intent;

import com.eeyuva.base.BaseView;
import com.eeyuva.interactor.DriverInteractor;
import com.eeyuva.screens.authentication.LoginContract;
import com.eeyuva.utils.preferences.PrefsManager;

/**
 * Created by hari on 05/09/16.
 */
public class RegistrationPresenterImpl implements RegistrationContract.Presenter {
    PrefsManager mPrefsManager;

    RegistrationContract.View mView;

    DriverInteractor mDriverInteractor;
    public RegistrationPresenterImpl(DriverInteractor mDriverInteractor, PrefsManager mPrefsManager) {
        this.mDriverInteractor = mDriverInteractor;
        this.mPrefsManager = mPrefsManager;
    }


    @Override
    public void setView(BaseView view) {
        mView = (RegistrationContract.View) view;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }
}
