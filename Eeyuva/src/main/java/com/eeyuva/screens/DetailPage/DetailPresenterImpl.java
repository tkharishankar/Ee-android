package com.eeyuva.screens.DetailPage;

import android.content.Intent;

import com.eeyuva.base.BaseView;
import com.eeyuva.base.LoadListener;
import com.eeyuva.interactor.ApiInteractor;
import com.eeyuva.screens.home.ModuleOrderResponse;
import com.eeyuva.screens.home.ResponseList;
import com.eeyuva.utils.preferences.PrefsManager;

import java.util.List;

/**
 * Created by hari on 14/09/16.
 */
public class DetailPresenterImpl implements DetailContract.Presenter {

    private PrefsManager mPrefsManager;
    private ApiInteractor mApiInteractor;
    private DetailContract.View mView;

    public DetailPresenterImpl(ApiInteractor apiInteractor, PrefsManager prefsManager) {
        this.mApiInteractor = apiInteractor;
        this.mPrefsManager = prefsManager;
    }

    @Override
    public void setView(BaseView view) {
        mView = (DetailContract.View) view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void getArticlesDetails(String mModuleId, String mArticleId) {
        mApiInteractor.getArticlesDetails(mView, "http://mobile.eeyuva.com/getarticleinfo.php?moduleid=" + mModuleId + "&arid=" + mArticleId, mArticleListener);

    }

    @Override
    public void getOtherArticlesDetails(String mModuleId, String mArticleId) {

        mApiInteractor.getOtherArticlesDetails(mView, "http://mobile.eeyuva.com/getrelatedarticles.php?articleid=" + mArticleId + "&modid=" + mModuleId, mOtherArticleListener);

    }

    @Override
    public List<ResponseList> getModules() {
        return mPrefsManager.getModules().getResponse();
    }


    LoadListener<ArticleDetailResponse> mArticleListener = new LoadListener<ArticleDetailResponse>() {
        @Override
        public void onSuccess(ArticleDetailResponse responseBody) {
            mView.setArticleDetails(responseBody.getResponse().get(0));
        }

        @Override
        public void onFailure(Throwable t) {

        }

        @Override
        public void onError(Object error) {

        }
    };
    LoadListener<ArticleDetailResponse> mOtherArticleListener = new LoadListener<ArticleDetailResponse>() {
        @Override
        public void onSuccess(ArticleDetailResponse responseBody) {
            mView.setOtherArticleDetails(responseBody.getResponse());
        }

        @Override
        public void onFailure(Throwable t) {

        }

        @Override
        public void onError(Object error) {

        }
    };
}
