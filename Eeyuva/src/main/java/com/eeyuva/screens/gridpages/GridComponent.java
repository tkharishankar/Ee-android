package com.eeyuva.screens.gridpages;

import com.eeyuva.di.component.AppComponent;
import com.eeyuva.di.module.HomeModule;
import com.eeyuva.di.scope.PerActivity;
import com.eeyuva.screens.gridpages.model.PhotoList;
import com.eeyuva.screens.home.HomeContract;

import dagger.Component;

/**
 * Created by hari on 17/09/16.
 */
@PerActivity
@Component(
        dependencies = AppComponent.class,
        modules = {GridModule.class})
public interface GridComponent {

    void inject(GridHomeActivity gridHomeActivity);

    void inject(PhotoListActivity photoListActivity);

    void inject(PhotoGalleryActivity photoGalleryActivity);

    GridContract.Presenter gridPresenter();
}