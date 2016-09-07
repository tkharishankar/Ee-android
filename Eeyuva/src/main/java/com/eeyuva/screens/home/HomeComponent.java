package com.eeyuva.screens.home;

import com.eeyuva.di.component.AppComponent;
import com.eeyuva.di.scope.PerActivity;

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
