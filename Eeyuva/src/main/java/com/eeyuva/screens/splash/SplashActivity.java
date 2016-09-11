package com.eeyuva.screens.splash;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.eeyuva.BuildConfig;
import com.eeyuva.R;
import com.eeyuva.di.component.DaggerSplashComponent;
import com.eeyuva.di.module.SplashPresenterModule;
import com.eeyuva.screens.authentication.LoginActivity;
import com.eeyuva.screens.home.HomeActivity;
import com.eeyuva.utils.Utils;
import com.eeyuva.utils.customdialog.DialogListener;
import com.eeyuva.ButterAppCompatActivity;
import com.eeyuva.di.component.SplashComponent;
import com.eeyuva.utils.Constants;
import com.eeyuva.utils.customdialog.DialogUtils;
import com.eeyuva.utils.navigation.NavigationUtils;
import com.google.firebase.iid.FirebaseInstanceId;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by kavi on 18/07/16.
 */
public class SplashActivity extends ButterAppCompatActivity implements SplashContract.View,Animation.AnimationListener {

    @Inject
    SplashContract.Presenter mPresenter;

    SplashComponent mComponent;

    @Bind(R.id.mTxtVersion)
    TextView mTxtVersion;

    @Bind(R.id.mImgLoader)
    ImageView mImgLoader;

    boolean mIsTablet;

    Animation mImgLoaderAnim;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initComponent();
        mIsTablet = getResources().getBoolean(R.bool.isTablet);
        if (mIsTablet) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        FirebaseInstanceId.getInstance().getToken();
        mPresenter.setView(this);

    }
    @Override
    public void setLoadAnim() {
        mImgLoaderAnim = AnimationUtils.loadAnimation(this, R.anim.zoom_in);
        mImgLoaderAnim.setAnimationListener(this);
        mImgLoaderAnim.setRepeatCount(-1);
        mImgLoaderAnim.setRepeatMode(Animation.INFINITE);
    }

    @Override
    public void setVersionNo() {
        mTxtVersion.setText("v " + BuildConfig.VERSION_NAME);
        mTxtVersion.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mImgLoader.setVisibility(View.VISIBLE);
        mImgLoader.startAnimation(mImgLoaderAnim);
        setUpAnimation();
    }

    private void setUpAnimation() {
        if (!Utils.isOnline(this)) {
            mImgLoader.clearAnimation();
            mImgLoader.setVisibility(View.INVISIBLE);
            return;
        }
        mImgLoader.setVisibility(View.VISIBLE);
    }


    private void initComponent() {
        mComponent = DaggerSplashComponent.builder()
                .appComponent(getApplicationComponent())
                .splashPresenterModule(new SplashPresenterModule(this))
                .build();
        mComponent.inject(this);
    }


    @Override
    public void showErrorMessage(int id) {


    }

    @Override
    public void moveToLogin() {
        NavigationUtils.startAndFinishActivity(SplashActivity.this, LoginActivity.class);
    }

    @Override
    public void moveToDashboard() {
        NavigationUtils.startAndFinishActivity(SplashActivity.this, HomeActivity.class);
    }



    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        mImgLoader.startAnimation(mImgLoaderAnim);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}

