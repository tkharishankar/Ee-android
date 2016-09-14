package com.eeyuva.interactor;



import com.eeyuva.apiservice.Api;
import com.eeyuva.base.BaseView;
import com.eeyuva.base.LoadListener;
import com.eeyuva.base.UiCallback;
import com.eeyuva.screens.DetailPage.ArticleDetailResponse;
import com.eeyuva.screens.authentication.LoginResponse;
import com.eeyuva.screens.home.GetArticleResponse;
import com.eeyuva.screens.home.HotModuleResponse;
import com.eeyuva.screens.home.ModuleOrderResponse;
import com.eeyuva.screens.registration.RegistrationResponse;
import com.eeyuva.utils.preferences.PrefsManager;

import retrofit2.Call;


/**
 * Created by hari on 22/6/16.
 */
public class ApiInteractorImpl implements ApiInteractor {

    PrefsManager mPrefsManager;
    Api mApi;

    public ApiInteractorImpl(Api remoteService, PrefsManager mPrefsManager) {
        this.mApi = remoteService;
        this.mPrefsManager = mPrefsManager;
    }

    @Override
    public void getLoginResponse(BaseView mView, String name, String pass, LoadListener<LoginResponse> mLoginListener) {
                UiCallback<LoginResponse> appConfigUiCallback = new UiCallback(mView, mLoginListener,true);
        Call<LoginResponse> appConfigCall = mApi.getAuthentication(name,pass);
        appConfigUiCallback.start(appConfigCall);
    }

    @Override
    public void getRegistrationResponse(BaseView mView, String firstName, String lastName,String gender,
                                        String email, String password, LoadListener<RegistrationResponse> mRegisterListener) {
        UiCallback<LoginResponse> callback = new UiCallback(mView, mRegisterListener,true);
        Call<LoginResponse> call = mApi.getRegistration(firstName,lastName,gender,email,password);
        callback.start(call);
    }

    @Override
    public void getModuleResponse(BaseView mView, String url, LoadListener<ModuleOrderResponse> mModuleListener) {
        UiCallback<ModuleOrderResponse> callback = new UiCallback(mView, mModuleListener,true);
        Call<ModuleOrderResponse> call = mApi.getModule(url);
        callback.start(call);
    }

    @Override
    public void getArticlesResponse(BaseView mView, String url, LoadListener<GetArticleResponse> mArticlesListener, boolean state) {
        UiCallback<GetArticleResponse> callback = new UiCallback(mView, mArticlesListener,state);
        Call<GetArticleResponse> call = mApi.getArticles(url);
        callback.start(call);
    }

    @Override
    public void getHotModuleResponse(BaseView mView, String url, LoadListener<HotModuleResponse> mHotModuleListener) {
        UiCallback<HotModuleResponse> callback = new UiCallback(mView, mHotModuleListener,true);
        Call<HotModuleResponse> call = mApi.getHotNews(url);
        callback.start(call);
    }

    @Override
    public void getArticlesDetails(BaseView mView, String url, LoadListener<ArticleDetailResponse> mArticleListener) {
        UiCallback<ArticleDetailResponse> callback = new UiCallback(mView, mArticleListener,true);
        Call<ArticleDetailResponse> call = mApi.getArticlesDetails(url);
        callback.start(call);
    }

    @Override
    public void getOtherArticlesDetails(BaseView mView, String url, LoadListener<ArticleDetailResponse> mOtherArticleListener) {
        UiCallback<ArticleDetailResponse> callback = new UiCallback(mView, mOtherArticleListener,false);
        Call<ArticleDetailResponse> call = mApi.getArticlesDetails(url);
        callback.start(call);
    }

}
