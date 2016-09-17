package com.eeyuva.screens.gridpages;

import android.content.Intent;

import com.eeyuva.base.BaseView;
import com.eeyuva.interactor.ApiInteractor;
import com.eeyuva.screens.home.ResponseList;
import com.eeyuva.utils.preferences.PrefsManager;

import java.util.List;

/**
 * Created by hari on 17/09/16.
 */
public class GridPresenterImpl implements GridContract.Presenter {
    ApiInteractor mApiInteractor;
    PrefsManager mPrefsManager;
    GridContract.View mView;

    public GridPresenterImpl(ApiInteractor apiInteractor, PrefsManager prefsManager) {
        this.mApiInteractor = apiInteractor;
        this.mPrefsManager = prefsManager;
    }

    @Override
    public void setView(BaseView view) {
        this.mView = (GridContract.View) view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public List<ResponseList> getModules() {
        return mPrefsManager.getModules().getResponse();
    }
}
