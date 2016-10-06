package com.eeyuva.screens.profile;

import com.eeyuva.di.component.AppComponent;
import com.eeyuva.di.module.GridModule;
import com.eeyuva.di.scope.PerActivity;

import dagger.Component;

/**
 * Created by hari on 01/10/16.
 */
@PerActivity
@Component(
        dependencies = AppComponent.class,
        modules = {ProfileModule.class})
public interface ProfileComponent {

    void inject(ProfileActivity profileActivity);

    void inject(ChangePasswordActivity changePasswordActivity);

    ProfileContract.Presenter profilePresenter();
}