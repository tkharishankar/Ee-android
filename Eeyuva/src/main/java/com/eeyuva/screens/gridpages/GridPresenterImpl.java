package com.eeyuva.screens.gridpages;

import android.content.Intent;

import com.eeyuva.base.BaseView;
import com.eeyuva.base.LoadListener;
import com.eeyuva.interactor.ApiInteractor;
import com.eeyuva.screens.gridpages.model.PhotoGalleryList;
import com.eeyuva.screens.gridpages.model.PhotoGalleryResponse;
import com.eeyuva.screens.gridpages.model.PhotoListResponse;
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

    @Override
    public void getPhotoList(String mModuleId) {
        mApiInteractor.getPhotoList(mView, "http://mobile.eeyuva.com/getphotoalbums.php?modid=" + mModuleId, mPhotoListListener);
    }

    @Override
    public void getPhotoGalleryList(String trid) {
        mApiInteractor.getPhotoGalleryList(mView, "http://mobile.eeyuva.com/getphotogallery.php?galid=" + trid, mPhotoGalleryListListener);
    }

    @Override
    public List<PhotoGalleryList> getPhotoGallery() {
        return mPrefsManager.getPhotoGalleryList().getResponse();
    }

    LoadListener<PhotoListResponse> mPhotoListListener = new LoadListener<PhotoListResponse>() {
        @Override
        public void onSuccess(PhotoListResponse responseBody) {
            mView.setAdapter(responseBody);
        }

        @Override
        public void onFailure(Throwable t) {

        }

        @Override
        public void onError(Object error) {

        }
    };

    LoadListener<PhotoGalleryResponse> mPhotoGalleryListListener = new LoadListener<PhotoGalleryResponse>() {
        @Override
        public void onSuccess(PhotoGalleryResponse responseBody) {
            mPrefsManager.setPhotoGalleryList(responseBody);
            mView.moveToGalleryView();
        }

        @Override
        public void onFailure(Throwable t) {

        }

        @Override
        public void onError(Object error) {

        }
    };
}
