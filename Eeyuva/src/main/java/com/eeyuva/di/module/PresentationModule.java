package com.eeyuva.di.module;

import android.content.Context;

import com.eeyuva.apiservice.Api;
import com.eeyuva.di.scope.GsonRestAdapter;
import com.eeyuva.interactor.DriverInteractor;
import com.eeyuva.interactor.DriverInteractorImpl;
import com.eeyuva.utils.preferences.PrefsManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hari on 10/7/16.
 */
@Module
public class PresentationModule {
    @Provides
    @Singleton
    public DriverInteractor driverInteractor(
            @GsonRestAdapter Api remoteService, PrefsManager prefsManager) {
        return new DriverInteractorImpl(remoteService, prefsManager);
    }


}
