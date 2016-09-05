package com.eeyuva.screens.authentication;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.eeyuva.di.component.DaggerLoginComponent;
import com.eeyuva.screens.home.HomeActivity;
import com.eeyuva.di.module.LoginPresenterModule;
import com.eeyuva.ButterAppCompatActivity;
import com.eeyuva.R;
import com.eeyuva.di.component.LoginComponent;
import com.eeyuva.screens.registration.RegistrationActivity;
import com.eeyuva.utils.Utils;
import com.eeyuva.utils.navigation.NavigationUtils;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by hari on 22/6/16.
 */
public class LoginActivity extends ButterAppCompatActivity implements LoginContract.View {

    @Inject
    LoginContract.Presenter mPresenter;

    private LoginComponent mComponent;

    @Bind(R.id.mEdtUsername)
    EditText mEdtUsername;

    @Bind(R.id.mEdtPassword)
    EditText mEdtPassword;

    @Bind(R.id.mBtnLogin)
    Button mLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponent();
        mPresenter.setView(this);
        mEdtPassword.setOnEditorActionListener(actionListener);
    }

    private void initComponent() {
        mComponent = DaggerLoginComponent.builder()
                .appComponent(getApplicationComponent())
                .loginPresenterModule(new LoginPresenterModule(this))
                .build();
        mComponent.inject(this);
    }

    @OnClick(R.id.mBtnLogin)
    public void onLoginClick() {
        Utils.hideSoftKeyBoard(LoginActivity.this);
        mPresenter.onLoginClicked();
    }

    @OnClick(R.id.mTxtSignup)
    public void OnSignUpClick() {
        mPresenter.onSignupClick();
    }



    @Override
    public String getUsername() {
        return mEdtUsername.getText().toString().trim();
    }

    @Override
    public String getPassword() {
        return mEdtPassword.getText().toString().trim();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void movetoDashBoard() {
        NavigationUtils.startAndFinishActivity(LoginActivity.this, HomeActivity.class);
    }

    @Override
    public void movetoSignUp() {
        NavigationUtils.startAndFinishActivity(LoginActivity.this, RegistrationActivity.class);

    }

    TextView.OnEditorActionListener actionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                Utils.hideSoftKeyBoard(LoginActivity.this);
                mPresenter.onLoginClicked();
                return true;
            }
            return false;
        }
    };


}
