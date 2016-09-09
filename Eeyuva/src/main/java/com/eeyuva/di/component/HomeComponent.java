package com.eeyuva.di.component;

import com.eeyuva.di.scope.PerActivity;
import com.eeyuva.screens.home.HomeActivity;
import com.eeyuva.screens.home.HomeContract;
import com.eeyuva.di.module.HomeModule;

import dagger.Component;

/**
 * Created by hari on 05/09/16.
 */

@PerActivity
@Component(
        dependencies = AppComponent.class,
        modules = {HomeModule.class})
public interface HomeComponent {

    void inject(HomeActivity homeActivity);

    HomeContract.Presenter homePresenter();
}
