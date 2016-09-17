package com.eeyuva.screens.gridpages;

import android.app.Activity;

import com.eeyuva.di.module.ActivityModule;
import com.eeyuva.di.scope.PerActivity;
import com.eeyuva.interactor.ApiInteractor;
import com.eeyuva.screens.home.HomeContract;
import com.eeyuva.screens.home.HomePresenterImpl;
import com.eeyuva.utils.preferences.PrefsManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hari on 17/09/16.
 */
@Module
@PerActivity
public class GridModule extends ActivityModule {

    public GridModule(Activity activity) {
        super(activity);
    }

    @Provides
    @PerActivity
    public GridContract.Presenter gridpresenter(ApiInteractor driverInteractor, PrefsManager prefsManager) {
        return new GridPresenterImpl(driverInteractor, prefsManager);
    }

}