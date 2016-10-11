package com.eeyuva.screens.profile.userdetails;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eeyuva.ButterAppCompatActivity;
import com.eeyuva.R;
import com.eeyuva.screens.DetailPage.model.CommentsList;
import com.eeyuva.screens.gridpages.GridHomeActivity;
import com.eeyuva.screens.home.HomeActivity;
import com.eeyuva.screens.home.loadmore.RoundedTransformation;
import com.eeyuva.screens.navigation.FragmentDrawer;
import com.eeyuva.screens.profile.DaggerProfileComponent;
import com.eeyuva.screens.profile.ProfileComponent;
import com.eeyuva.screens.profile.ProfileContract;
import com.eeyuva.screens.profile.ProfileModule;
import com.eeyuva.screens.profile.model.AlertList;
import com.eeyuva.screens.profile.model.CommentResponse;
import com.eeyuva.screens.profile.model.NewsResponse;
import com.eeyuva.screens.profile.model.NotificationResponse;
import com.eeyuva.screens.profile.model.ProfileList;
import com.eeyuva.screens.profile.model.ProfileResponse;
import com.eeyuva.screens.searchpage.SearchActivity;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by hari on 01/10/16.
 */

public class ProfileActivity extends ButterAppCompatActivity implements ProfileContract.View, IFragmentToActivity {


    @Inject
    ProfileContract.Presenter mPresenter;

    ProfileComponent mComponent;

    @Bind(R.id.tool_bar)
    Toolbar mToolbar;

    @Bind(R.id.imgProfile)
    ImageView imgProfile;

    @Bind(R.id.mTxtName)
    TextView mTxtName;

    @Bind(R.id.mTxtMail)
    TextView mTxtMail;

    @Bind(R.id.mTxtEditProfile)
    TextView mTxtEditProfile;

    @Bind(R.id.imgHome)
    ImageView imgHome;
    @Bind(R.id.imgList)
    ImageView imgList;
    @Bind(R.id.imgPhoto)
    ImageView imgPhoto;
    @Bind(R.id.imgViedo)
    ImageView imgViedo;
    @Bind(R.id.imgComment)
    ImageView imgComment;
    FragmentDrawer drawerFragment;
    TabLayout tabLayout;
    PagerAdapter adapter;
    ProfileList mProfileInfo;

    boolean mProfile = false;
    boolean mPhotoVideo = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initComponent();
        mPresenter.setView(this);
        Bundle args = null;

        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawerFragment = (FragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        ArrayList<String> tabs = new ArrayList<>();
        tabs.add("Personal");
        tabs.add("Settings");
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        adapter = new PagerAdapter(getSupportFragmentManager(), tabs);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mPresenter.getProfile();

    }

    public void initComponent() {
        mComponent = DaggerProfileComponent.builder()
                .appComponent(getApplicationComponent())
                .profileModule(new ProfileModule(this))
                .build();
        mComponent.inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        drawerFragment.setActivity(this);
        drawerFragment.setList(mPresenter.getModules());

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_search:
                showDialog();
                break;
            case R.id.action_add:
                showModuleVideoPhoto(null);
                break;

        }

        return super.onOptionsItemSelected(item);
    }


    @OnClick(R.id.imgHome)
    public void onHomeClick() {
        Intent intent =
                new Intent(ProfileActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.imgList)
    public void onListClick() {
        moveNext(1);
    }

    @OnClick(R.id.imgPhoto)
    public void onPhotoClick() {
        moveNext(2);
    }

    @OnClick(R.id.imgViedo)
    public void onVideoClick() {
        moveNext(3);
    }

    public void moveNext(int i) {
        Intent intent =
                new Intent(ProfileActivity.this, GridHomeActivity.class);
        intent.putExtra("index", i);
        startActivity(intent);
    }

    AlertDialog mDialog;

    public void showDialog() {
        try {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
                //return;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View dialogView = LayoutInflater.from(this).inflate(R.layout.search_dialog, null);
            builder.setView(dialogView);

            final EditText mEdtSearch = (EditText) dialogView.findViewById(R.id.btnSearch);
            Button mBtnok = (Button) dialogView.findViewById(R.id.btnOk);
            String sKeyword;

            mBtnok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String sKeyword;
                    sKeyword = mEdtSearch.getText().toString().trim();
                    if (sKeyword != null && sKeyword.length() != 0) {
                        mDialog.dismiss();
                        Intent intent =
                                new Intent(ProfileActivity.this, SearchActivity.class);
                        intent.putExtra("keyword", sKeyword);
                        startActivity(intent);
                        return;
                    }
                }
            });
            mDialog = builder.create();
            mDialog.setCancelable(true);
            mDialog.show();
            mDialog.getWindow().setGravity(Gravity.TOP);
            Window window = mDialog.getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.verticalMargin = .055f;
            wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            window.setAttributes(wlp);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void gotoHome(View v) {
        Intent intent =
                new Intent(ProfileActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void setImage(String url) {
        Picasso.with(this).load("http://eeyuva.com/static/userpics/" + url).transform(new RoundedTransformation(100, 0)).placeholder(getResources().getDrawable(R.drawable.ic_profile_default)).resize(80, 80).into(imgProfile);
    }

    @Override
    public void setUserDetails(ProfileResponse responseBody) {
        try {
            mProfileInfo = responseBody.getmProfileList().get(0);
            mTxtName.setText(responseBody.getmProfileList().get(0).getFname());
            mTxtMail.setText(responseBody.getmProfileList().get(0).getLname());
            Fragment fragment = adapter.getFragment(tabLayout
                    .getSelectedTabPosition());
            if (fragment != null)
                ((TabFragment1) fragment).onRefresh(responseBody);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setAdapter(List<AlertList> alertList) {

    }

    @Override
    public void setCommentAdapter(CommentResponse responseBody) {

    }

    @Override
    public void setNewsAdapter(NewsResponse responseBody) {

    }

    @Override
    public void setNotificationAdapter(NotificationResponse responseBody) {

    }

    @Override
    public void setPhoto(File photoFile) {
        if (mProfile) {
            showUpdatePhoto(photoFile);
            mPresenter.uploadImage(photoFile);
        } else if (mPhotoVideo)
        {
            showModuleVideoPhoto(photoFile);
        }
    }

    @Override
    public void setCommentsListToAdapter(List<CommentsList> response) {

    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public void communicateToFragment2() {

    }

    @Override
    public void communicateToStuffsActivity(String moduleid, String artid) {

    }

    @OnClick(R.id.mTxtEditProfile)
    public void editProfileClick() {
        showCommentDialog();
    }

    @OnClick(R.id.imgProfile)
    public void updateProfileClick() {
        showUpdatePhoto(null);
    }

    public void showCommentDialog() {
        try {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_edit_profile, null);
            builder.setView(dialogView);

            RelativeLayout LayRating = (RelativeLayout) dialogView.findViewById(R.id.LayRating);
            TextView mBtnok = (TextView) dialogView.findViewById(R.id.btnOk);
            TextView txtRate = (TextView) dialogView.findViewById(R.id.mTxtRate);

            final EditText mEdtFName = (EditText) dialogView.findViewById(R.id.mEdtFName);
            final EditText mEdtLName = (EditText) dialogView.findViewById(R.id.mEdtLName);
            final EditText mEdtMobile = (EditText) dialogView.findViewById(R.id.mEdtMobile);
            final EditText mEdtMailid = (EditText) dialogView.findViewById(R.id.mEdtMailid);
            final EditText mEdtDob = (EditText) dialogView.findViewById(R.id.mEdtDob);
            final EditText mEdtGender = (EditText) dialogView.findViewById(R.id.mEdtGender);
            final EditText mEdtEducation = (EditText) dialogView.findViewById(R.id.mEdtEducation);
            final EditText mEdtJob = (EditText) dialogView.findViewById(R.id.mEdtJob);
            final EditText mEdtAbout = (EditText) dialogView.findViewById(R.id.mEdtAbout);

            mEdtFName.setText(mProfileInfo.getFname());
            mEdtLName.setText(mProfileInfo.getLname());
            mEdtMobile.setText(mProfileInfo.getMobile());
            mEdtMailid.setText(mProfileInfo.getMobile());
            mEdtDob.setText(mProfileInfo.getDob());
            mEdtGender.setText(mProfileInfo.getGender());
            mEdtAbout.setText(mProfileInfo.getAboutMe());

            mBtnok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPresenter.setUpdateProfile(mEdtFName.getText().toString().trim(),
                            mEdtLName.getText().toString().trim(),
                            mEdtMobile.getText().toString().trim(),
                            mEdtGender.getText().toString().trim(),
                            mEdtDob.getText().toString().trim(),
                            mEdtAbout.getText().toString().trim());
                    mDialog.dismiss();
                }
            });

            mDialog = builder.create();
            mDialog.setCancelable(true);
            mDialog.show();
            mDialog.getWindow().setGravity(Gravity.TOP);
            mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            Window window = mDialog.getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.verticalMargin = .1f;
            window.setAttributes(wlp);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showUpdatePhoto(File photoFile) {
        try {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }

            mProfile = true;
            mPhotoVideo = false;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_update_photo, null);
            builder.setView(dialogView);

            TextView mBtnTakePhoto = (TextView) dialogView.findViewById(R.id.mBtnTakePhoto);
            ImageView mImgProfile = (ImageView) dialogView.findViewById(R.id.mImgProfile);
            TextView mBtnGallery = (TextView) dialogView.findViewById(R.id.mBtnGallery);


            mBtnTakePhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                    mPresenter.snapPhotoClick();
                }
            });

            mBtnGallery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                    mPresenter.pickFromGalleryClick();
                }
            });

            mDialog = builder.create();
            mDialog.setCancelable(true);
            mDialog.show();
            mDialog.getWindow().setGravity(Gravity.TOP);
            mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            Window window = mDialog.getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.verticalMargin = .1f;
            window.setAttributes(wlp);
            if (photoFile != null) {
                Picasso.with(this).load(photoFile).transform(new RoundedTransformation(10, 0)).into(mImgProfile);
            }
            mImgProfile.setDrawingCacheEnabled(false); // clear drawing cache
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.onActivityResult(requestCode, resultCode, data);
    }

    public void showModuleVideoPhoto(final File photoFile) {
        try {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
            mProfile = false;
            mPhotoVideo = true;

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_module_photo, null);
            builder.setView(dialogView);

            final TextView mBtnTakePhoto = (TextView) dialogView.findViewById(R.id.mBtnTakePhoto);
            ImageView mImgProfile = (ImageView) dialogView.findViewById(R.id.mImgProfile);
            TextView mBtnGallery = (TextView) dialogView.findViewById(R.id.mBtnGallery);
            TextView mBtnor = (TextView) dialogView.findViewById(R.id.mBtnor);
            final EditText mEdtModule = (EditText) dialogView.findViewById(R.id.mEdtModule);
            final EditText mEdtTitle = (EditText) dialogView.findViewById(R.id.mEdtTitle);
            final EditText mEdtDesc = (EditText) dialogView.findViewById(R.id.mEdtDesc);
            if (photoFile != null) {
                mBtnTakePhoto.setText("Post");
                mEdtModule.setVisibility(View.VISIBLE);
                mEdtTitle.setVisibility(View.VISIBLE);
                mEdtDesc.setVisibility(View.VISIBLE);
                mBtnGallery.setVisibility(View.GONE);
                mBtnor.setVisibility(View.GONE);
            }

            mBtnTakePhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                    if(mBtnTakePhoto.getText().toString().trim().equalsIgnoreCase("Post"))
                        mPresenter.uploadImageOrVideo(photoFile,mEdtModule.getText().toString().trim(),
                                mEdtTitle.getText().toString().trim(),
                                mEdtDesc.getText().toString().trim());
                    else
                    mPresenter.snapPhotoClick();

                }
            });

            mBtnGallery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                    mPresenter.pickFromGalleryClick();
                }
            });

            mDialog = builder.create();
            mDialog.setCancelable(true);
            mDialog.show();
            mDialog.getWindow().setGravity(Gravity.TOP);
            mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            Window window = mDialog.getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.verticalMargin = .1f;
            window.setAttributes(wlp);
            if (photoFile != null) {
                Picasso.with(this).load(photoFile).transform(new RoundedTransformation(10, 0)).into(mImgProfile);
            }
            mImgProfile.setDrawingCacheEnabled(false); // clear drawing cache
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
