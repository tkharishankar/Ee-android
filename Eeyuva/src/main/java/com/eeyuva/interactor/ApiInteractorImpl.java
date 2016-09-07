package com.eeyuva.interactor;



import com.eeyuva.apiservice.Api;
import com.eeyuva.base.BaseView;
import com.eeyuva.base.LoadListener;
import com.eeyuva.base.UiCallback;
import com.eeyuva.screens.authentication.LoginResponse;
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

}
