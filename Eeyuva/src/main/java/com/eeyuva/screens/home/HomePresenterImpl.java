package com.eeyuva.screens.home;

import android.content.Intent;

import com.eeyuva.ButterAppCompatActivity;
import com.eeyuva.base.BaseView;
import com.eeyuva.base.LoadListener;
import com.eeyuva.interactor.ApiInteractor;
import com.eeyuva.utils.preferences.PrefsManager;

import java.util.List;

/**
 * Created by hari on 05/09/16.
 */
public class HomePresenterImpl implements HomeContract.Presenter {

    private PrefsManager mPrefsManager;
    private ApiInteractor mApiInteractor;
    private HomeContract.View mView;

    public HomePresenterImpl(ApiInteractor apiInteractor, PrefsManager prefsManager) {
        this.mApiInteractor = apiInteractor;
        this.mPrefsManager = prefsManager;
    }

    @Override
    public void setView(BaseView view) {
        this.mView = (HomeContract.View) view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void getHomeModule() {
        mApiInteractor.getModuleResponse(mView, "http://mobile.eeyuva.com/moduleorder.json", mModuleListener);
    }

    @Override
    public void getArticles(String moduleid) {
        mApiInteractor.getArticlesResponse(mView, "http://mobile.eeyuva.com/getarticles.php?moduleid="+moduleid+"&startindex=1&endindex=10", mArticlesListener);

    }

    @Override
    public List<ResponseList> getModules() {
        return mPrefsManager.getModules().getResponse();
    }

    LoadListener<ModuleOrderResponse> mModuleListener= new LoadListener<ModuleOrderResponse>() {
        @Override
        public void onSuccess(ModuleOrderResponse responseBody) {
            mView.setDataToAdapter(responseBody.getResponse());
        }

        @Override
        public void onFailure(Throwable t) {

        }

        @Override
        public void onError(Object error) {

        }
    };

    LoadListener<GetArticleResponse> mArticlesListener=new LoadListener<GetArticleResponse>() {
        @Override
        public void onSuccess(GetArticleResponse responseBody) {
            mView.setArticleAdapterNotify(responseBody.getResponseItem());
        }

        @Override
        public void onFailure(Throwable t) {

        }

        @Override
        public void onError(Object error) {

        }
    };
}
