package com.eeyuva.screens.registration;

import android.content.Intent;
import android.text.TextUtils;

import com.eeyuva.R;
import com.eeyuva.base.BaseView;
import com.eeyuva.base.LoadListener;
import com.eeyuva.interactor.DriverInteractor;
import com.eeyuva.screens.authentication.LoginContract;
import com.eeyuva.utils.preferences.PrefsManager;

/**
 * Created by hari on 05/09/16.
 */
public class RegistrationPresenterImpl implements RegistrationContract.Presenter {
    PrefsManager mPrefsManager;

    RegistrationContract.View mView;

    DriverInteractor mDriverInteractor;

    public RegistrationPresenterImpl(DriverInteractor mDriverInteractor, PrefsManager mPrefsManager) {
        this.mDriverInteractor = mDriverInteractor;
        this.mPrefsManager = mPrefsManager;
    }


    @Override
    public void setView(BaseView view) {
        mView = (RegistrationContract.View) view;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void onLoginClicked() {
        mView.movetoLogin();
    }

    @Override
    public void onSignupClicked() {
        if (validateUserInput()) {
            mDriverInteractor.getRegistrationResponse(mView, mView.getFirstName(), mView.getLastName(),mView.getGender(),mView.getEmail(),
                    mView.getPassword(), mRegisterListener);
        }
    }

    boolean validateUserInput() {
        if (mView.getFirstName().length() == 0) {
            mView.showErrorDialog(R.string.enter_first_name);
            return false;
        } else if (mView.getFirstName().length() <= 2) {
            mView.showErrorDialog(R.string.enter_min_character_first_name);
            return false;
        } else if (mView.getLastName().length() == 0) {
            mView.showErrorDialog(R.string.enter_last_name);
            return false;
        } else if (mView.getLastName().length() <= 2) {
            mView.showErrorDialog(R.string.enter_min_character_last_name);
            return false;
        } else if (mView.getEmail().length() == 0) {
            mView.showErrorDialog(R.string.enter_email_id);
            return false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(mView.getEmail()).matches()) {
            mView.showErrorDialog(R.string.enter_valid_email_id);
            return false;
        } else if (mView.getPassword().length() == 0) {
            mView.showErrorDialog(R.string.enter_password);
            return false;
        } else if (mView.getConfirmPassword().length() == 0) {
            mView.showErrorDialog(R.string.enter_confirm_password);
            return false;
        } else if (!mView.getConfirmPassword().equals(mView.getPassword())) {
            mView.showErrorDialog(R.string.enter_min_character_not_confirm_password);
            return false;
        }
        return true;
    }

    private LoadListener<RegistrationResponse> mRegisterListener= new LoadListener<RegistrationResponse>() {
        @Override
        public void onSuccess(RegistrationResponse responseBody) {

        }

        @Override
        public void onFailure(Throwable t) {

        }

        @Override
        public void onError(Object error) {

        }
    };

}
