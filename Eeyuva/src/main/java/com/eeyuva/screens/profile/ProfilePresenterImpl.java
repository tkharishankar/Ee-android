package com.eeyuva.screens.profile;

import android.content.Intent;

import com.eeyuva.base.BaseView;
import com.eeyuva.interactor.ApiInteractor;
import com.eeyuva.screens.home.ResponseList;
import com.eeyuva.utils.preferences.PrefsManager;

import java.util.List;

/**
 * Created by hari on 01/10/16.
 */

public class ProfilePresenterImpl implements ProfileContract.Presenter{

    private ApiInteractor mApiInteractor;
    private PrefsManager mPrefsManager;
    private ProfileContract.View mView;

    public ProfilePresenterImpl(ApiInteractor apiInteractor, PrefsManager prefsManager) {
        this.mApiInteractor=apiInteractor;
        this.mPrefsManager=prefsManager;
    }

    @Override
    public void setView(BaseView view) {
        mView= (ProfileContract.View) view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public List<ResponseList> getModules() {
        return mPrefsManager.getModules().getResponse();
    }

}
