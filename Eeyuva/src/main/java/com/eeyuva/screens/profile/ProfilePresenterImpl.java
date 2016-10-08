package com.eeyuva.screens.profile;

import android.content.Intent;

import com.eeyuva.base.BaseView;
import com.eeyuva.base.LoadListener;
import com.eeyuva.interactor.ApiInteractor;
import com.eeyuva.screens.home.ResponseList;
import com.eeyuva.screens.profile.model.AlertResponse;
import com.eeyuva.screens.profile.model.EditResponse;
import com.eeyuva.screens.profile.model.ProfileResponse;
import com.eeyuva.utils.preferences.PrefsManager;

import java.util.List;

/**
 * Created by hari on 01/10/16.
 */

public class ProfilePresenterImpl implements ProfileContract.Presenter {

    private ApiInteractor mApiInteractor;
    private PrefsManager mPrefsManager;
    private ProfileContract.View mView;

    public ProfilePresenterImpl(ApiInteractor apiInteractor, PrefsManager prefsManager) {
        this.mApiInteractor = apiInteractor;
        this.mPrefsManager = prefsManager;
    }

    @Override
    public void setView(BaseView view) {
        mView = (ProfileContract.View) view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public List<ResponseList> getModules() {
        return mPrefsManager.getModules().getResponse();
    }

    @Override
    public void getProfile() {
        mApiInteractor.getProfile(mView, "http://mobile.eeyuva.com/getuserinfo.php?uid=3939", mProfileListener);
    }

    @Override
    public void setUpdateProfile(String fname, String lastname, String mobile, String gender, String dob, String about) {

        mApiInteractor.getEditProfile(mView, "http://mobile.eeyuva.com/edituserinfo.php?uid=3939" +
                "&fname=" + fname +
                "&lname=" + lastname +
                "&mob=" + mobile + "" +
                "&gen=" + gender +
                "&dob=" + dob +
                "&aboutme=" + about, mEditProfileListener);

    }

    @Override
    public void getUserAlerts() {
        mApiInteractor.getUserAlerts(mView, "http://mobile.eeyuva.com/fetchalerts.php?uid=3939", mAlertListner);
    }

    LoadListener<ProfileResponse> mProfileListener = new LoadListener<ProfileResponse>() {
        @Override
        public void onSuccess(ProfileResponse responseBody) {
            mView.setImage(responseBody.getmProfileList().get(0).getProfilePic());
            mView.setUserDetails(responseBody);
        }

        @Override
        public void onFailure(Throwable t) {

        }

        @Override
        public void onError(Object error) {

        }
    };

    LoadListener<EditResponse> mEditProfileListener = new LoadListener<EditResponse>() {
        @Override
        public void onSuccess(EditResponse responseBody) {
            mView.showErrorDialog(responseBody.getSTATUSINFO());
        }

        @Override
        public void onFailure(Throwable t) {

        }

        @Override
        public void onError(Object error) {

        }
    };

    LoadListener<AlertResponse> mAlertListner = new LoadListener<AlertResponse>() {
        @Override
        public void onSuccess(AlertResponse responseBody) {
            mView.setAdapter(responseBody.getAlertList());
        }

        @Override
        public void onFailure(Throwable t) {

        }

        @Override
        public void onError(Object error) {

        }
    };

}
