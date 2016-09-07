package com.eeyuva.screens.home;

import android.content.Intent;

import com.eeyuva.ButterAppCompatActivity;
import com.eeyuva.base.BaseView;
import com.eeyuva.interactor.ApiInteractor;
import com.eeyuva.utils.preferences.PrefsManager;

/**
 * Created by hari on 05/09/16.
 */
public class HomePresenterImpl implements HomeContract.Presenter {

    public HomePresenterImpl(ApiInteractor driverInteractor, PrefsManager prefsManager) {

    }

    @Override
    public void setView(BaseView view) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }
}
