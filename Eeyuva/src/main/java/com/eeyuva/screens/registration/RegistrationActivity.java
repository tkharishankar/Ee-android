package com.eeyuva.screens.registration;

import android.os.Bundle;

import com.eeyuva.ButterAppCompatActivity;
import com.eeyuva.R;
import com.eeyuva.di.component.DaggerLoginComponent;
import com.eeyuva.di.component.DaggerRegistrationComponent;
import com.eeyuva.di.component.RegistrationComponent;
import com.eeyuva.di.module.LoginPresenterModule;
import com.eeyuva.di.module.RegistrationPresenterModule;

import javax.inject.Inject;

/**
 * Created by hari on 05/09/16.
 */
public class RegistrationActivity extends ButterAppCompatActivity implements RegistrationContract.View {
    @Inject
    RegistrationContract.Presenter mPresenter;

    RegistrationComponent mComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        initComponent();
        mPresenter.setView(this);
    }

    private void initComponent() {
        mComponent = DaggerRegistrationComponent.builder()
                .appComponent(getApplicationComponent())
                .registrationPresenterModule(new RegistrationPresenterModule(this))
                .build();
        mComponent.inject(this);
    }

}
