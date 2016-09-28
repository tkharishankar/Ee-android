package com.eeyuva.screens.gridpages;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.TextView;

import com.eeyuva.ButterAppCompatActivity;
import com.eeyuva.R;
import com.eeyuva.screens.gridpages.model.PhotoGalleryList;
import com.eeyuva.screens.gridpages.model.PhotoGalleryResponse;
import com.eeyuva.screens.gridpages.model.PhotoList;
import com.eeyuva.screens.gridpages.model.PhotoListResponse;
import com.eeyuva.screens.gridpages.model.UserNewsList;
import com.eeyuva.screens.gridpages.model.UserNewsListResponse;
import com.eeyuva.screens.home.GetArticleResponse;
import com.eeyuva.screens.home.HomeActivity;
import com.eeyuva.screens.home.HomeContract;
import com.eeyuva.screens.home.ResponseItem;
import com.eeyuva.screens.home.ResponseList;
import com.eeyuva.screens.navigation.FragmentDrawer;
import com.eeyuva.screens.searchpage.SearchActivity;
import com.eeyuva.screens.searchpage.model.SearchResponse;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by hari on 17/09/16.
 */
public class GridHomeActivity extends ButterAppCompatActivity implements GridContract.View, GridContract.AdapterCallBack {

    @Inject
    GridContract.Presenter mPresenter;

    GridComponent mComponent;

    @Bind(R.id.tool_bar)
    Toolbar mToolbar;

    @Bind(R.id.txtHotStories)
    TextView mTxtHotStories;

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

    int mIndex;

    @Bind(R.id.orderlist)
    RecyclerView mRecyclerView;

    GridLoadAdapter mGridLoadAdapter;

    RecyclerView.LayoutManager mLayoutManager;

    public List<ResponseList> mModuleList = new ArrayList<ResponseList>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gridhome);
        initComponent();
        mPresenter.setView(this);

        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawerFragment = (FragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);

        mIndex = getIntent().getExtras().getInt("index");
        moveNext(mIndex);
        mModuleList = mPresenter.getModules();
        initAdapter();
    }

    private void moveNext(int i) {
        mIndex = i;

        if (mIndex == 2)
            mTxtHotStories.setText("Images");
        else if (mIndex == 3)
            mTxtHotStories.setText("Videos");
        else if (mIndex == 1)
            mTxtHotStories.setText("User News");

    }

    private void initAdapter() {
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mGridLoadAdapter = new GridLoadAdapter(this, this, mModuleList);
        mRecyclerView.setAdapter(mGridLoadAdapter);
        mGridLoadAdapter.notifyDataSetChanged();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return true;
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

    private void initComponent() {
        mComponent = DaggerGridComponent.builder()
                .appComponent(getApplicationComponent())
                .gridModule(new GridModule(this))
                .build();
        mComponent.inject(this);
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
                break;

        }

        return super.onOptionsItemSelected(item);
    }


    @OnClick(R.id.imgHome)
    public void onHomeClick() {
        Intent intent =
                new Intent(GridHomeActivity.this, HomeActivity.class);
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
                                new Intent(GridHomeActivity.this, SearchActivity.class);
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

    @Override
    public void setAdapter(PhotoListResponse responseBody) {

    }

    @Override
    public void moveToGalleryView() {

    }

    @Override
    public void setAdapter(PhotoGalleryResponse responseBody) {

    }

    @Override
    public void setAdapter(UserNewsListResponse responseBody) {

    }

    @Override
    public void setSelectItem(ResponseList rl) {
        if (mIndex == 2) {
            Intent intent =
                    new Intent(GridHomeActivity.this, PhotoListActivity.class);
            intent.putExtra("title", rl.getTitle());
            intent.putExtra("order_id", rl.getOrderid());
            intent.putExtra("module_id", rl.getModuleid());
            startActivity(intent);
        } else if (mIndex == 3) {
            Intent intent =
                    new Intent(GridHomeActivity.this, VideoListActivity.class);
            intent.putExtra("title", rl.getTitle());
            intent.putExtra("order_id", rl.getOrderid());
            intent.putExtra("module_id", rl.getModuleid());
            startActivity(intent);
        }else if(mIndex==1)
        {
            Intent intent =
                    new Intent(GridHomeActivity.this, UserNewsActivity.class);
            intent.putExtra("title", rl.getTitle());
            intent.putExtra("order_id", rl.getOrderid());
            intent.putExtra("module_id", rl.getModuleid());
            startActivity(intent);
        }
    }

    @Override
    public void setSelectItem(PhotoList rl) {

    }

    @Override
    public void setSelectItem(PhotoGalleryList rl) {

    }

    @Override
    public void setSelectItem(UserNewsList rl) {

    }

    public void gotoHome(View v)
    {
        Intent intent =
                new Intent(GridHomeActivity.this, HomeActivity.class);
        startActivity(intent);
    }
}
