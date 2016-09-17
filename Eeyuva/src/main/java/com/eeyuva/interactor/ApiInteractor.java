package com.eeyuva.interactor;


import com.eeyuva.base.BaseView;
import com.eeyuva.base.LoadListener;
import com.eeyuva.screens.DetailPage.ArticleDetailResponse;
import com.eeyuva.screens.DetailPage.DetailContract;
import com.eeyuva.screens.authentication.LoginContract;
import com.eeyuva.screens.authentication.LoginResponse;
import com.eeyuva.screens.home.GetArticleResponse;
import com.eeyuva.screens.home.HomeContract;
import com.eeyuva.screens.home.HotModuleResponse;
import com.eeyuva.screens.home.ModuleOrderResponse;
import com.eeyuva.screens.registration.RegistrationContract;
import com.eeyuva.screens.registration.RegistrationResponse;
import com.eeyuva.screens.searchpage.model.SearchResponse;

/**
 * Created by hari on 22/6/16.
 */
public interface ApiInteractor {
    void getLoginResponse(BaseView mView, String name, String pass, LoadListener<LoginResponse> mLoginListener);

    void getRegistrationResponse(BaseView mView, String firstName, String lastName, String email, String password, String confirmPassword, LoadListener<RegistrationResponse> mRegisterListener);

    void getModuleResponse(BaseView mView, String s, LoadListener<ModuleOrderResponse> mModuleListener);

    void getArticlesResponse(BaseView mView, String s, LoadListener<GetArticleResponse> mArticlesListener, boolean b);

    void getHotModuleResponse(BaseView mView, String s, LoadListener<HotModuleResponse> mHotModuleListener);

    void getArticlesDetails(BaseView mView, String s, LoadListener<ArticleDetailResponse> mArticleListener);

    void getOtherArticlesDetails(BaseView mView, String s, LoadListener<ArticleDetailResponse> mOtherArticleListener);

    void getSearchResponse(BaseView mView, String s, LoadListener<SearchResponse> mArticlesListener, boolean b);
}

