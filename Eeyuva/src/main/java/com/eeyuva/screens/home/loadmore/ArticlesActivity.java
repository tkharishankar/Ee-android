package com.eeyuva.screens.home.loadmore;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import android.widget.Toast;

import com.eeyuva.ButterAppCompatActivity;
import com.eeyuva.R;
import com.eeyuva.di.component.DaggerHomeComponent;
import com.eeyuva.di.component.HomeComponent;
import com.eeyuva.di.module.HomeModule;
import com.eeyuva.screens.DetailPage.DetailActivity;
import com.eeyuva.screens.gridpages.GridHomeActivity;
import com.eeyuva.screens.home.GetArticleResponse;
import com.eeyuva.screens.home.HomeActivity;
import com.eeyuva.screens.home.HomeContract;
import com.eeyuva.screens.home.ResponseItem;
import com.eeyuva.screens.home.ResponseList;
import com.eeyuva.screens.navigation.FragmentDrawer;
import com.eeyuva.screens.searchpage.SearchActivity;
import com.eeyuva.screens.searchpage.model.SearchResponse;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by hari on 05/09/16.
 */
public class ArticlesActivity extends ButterAppCompatActivity implements HomeContract.View, HomeContract.AdapterCallBack {

    @Inject
    HomeContract.Presenter mPresenter;

    HomeComponent mComponent;
    private FragmentDrawer drawerFragment;


    @Bind(R.id.tool_bar)
    Toolbar mToolbar;

    private List<ResponseItem> mArticlesList = new ArrayList<ResponseItem>();

    private int mPrevIndex = 0;

    String mModuleId;

    String mModuleName;

    @Bind(R.id.orderlist)
    RecyclerView mRecyclerView;

    @Bind(R.id.txtHotStories)
    TextView mTxtHotStories;

    ArticlesLoadAdapter mArticleAdapter;

    RecyclerView.LayoutManager mLayoutManager;
    private String mOrderId;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        initComponent();
        mPresenter.setView(this);

        setSupportActionBar(mToolbar);
//        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawerFragment = (FragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);

        mPrevIndex = getIntent().getExtras().getInt("index");
        mModuleId = getIntent().getExtras().getString("module_id");
        mOrderId = getIntent().getExtras().getString("order_id");
        mModuleName = getIntent().getExtras().getString("module_name");
        mTxtHotStories.setText(mModuleName);
        mPresenter.getArticles(mModuleId);
    }

    private void initAdapter(final List<ResponseItem> responseItem) {
        this.mArticlesList = responseItem;
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(this, 1);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mArticleAdapter = new ArticlesLoadAdapter(this, this, mArticlesList);
        mArticleAdapter.setLoadMoreListener(new ArticlesLoadAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {

                mRecyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        int index = mArticlesList.size();
                        loadMore(index);
                    }


                });
                //Calling loadMore function in Runnable to fix the
                // java.lang.IllegalStateException: Cannot call this method while RecyclerView is computing a layout or scrolling error
            }
        });
        mRecyclerView.setAdapter(mArticleAdapter);
        mArticleAdapter.notifyDataSetChanged();
        if (mPrevIndex != 0)
            loadMore(mArticlesList.size());

    }

    @Override
    public void setLoadMoredata(GetArticleResponse responseBody) {
        //remove loading view

        mArticlesList.remove(mArticlesList.size() - 1);
        Log.i("index", "mArticlesList after rem" + mArticlesList.size());

        List<ResponseItem> result = responseBody.getResponseItem();
        Log.i("index", "mArticlesList result" + result.size());

        if (result.size() > 0) {
            //add loaded data
            mArticlesList.addAll(result);
        } else {//result size 0 means there is no more data available at server
            mArticleAdapter.setMoreDataAvailable(false);
            //telling adapter to stop calling load more as no more server data available
            Toast.makeText(this, "No More Data Available", Toast.LENGTH_LONG).show();
        }
        mArticleAdapter.notifyDataChanged();
        Log.i("index", "mArticlesList after load" + mArticlesList.size());

        try {
            mLayoutManager.scrollToPosition(mArticlesList.size() - 5);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void setLoadMoredata(SearchResponse responseBody) {

    }

    private void loadMore(int index) {
        Log.i("index", "mArticlesList before" + mArticlesList.size());
        Log.i("index", "index" + index);
        mArticlesList.add(new ResponseItem(true));
        mArticleAdapter.notifyItemInserted(mArticlesList.size() - 1);
        mPresenter.getArticles(mModuleId, index + 1, index);

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
        drawerFragment.setList(mPresenter.getModules());

    }

    private void initComponent() {
        mComponent = DaggerHomeComponent.builder()
                .appComponent(getApplicationComponent())
                .homeModule(new HomeModule(this))
                .build();
        mComponent.inject(this);
    }



    @Override
    public void setDataToAdapter(List<ResponseList> response) {

    }

    @Override
    public void setArticleAdapterNotify(List<ResponseItem> responseItem) {
        initAdapter(responseItem);
    }


    @Override
    public void onItemClick(String articleid, String modid) {
        Intent intent =
                new Intent(ArticlesActivity.this, DetailActivity.class);
        intent.putExtra("module_id", mModuleId);
        intent.putExtra("article_id", articleid);
//        intent.putExtra("order_id", mOrderId);
//        intent.putExtra("module_name", mModuleName);
        intent.putExtra("type", "home");
        startActivity(intent);
    }

    @OnClick(R.id.imgHome)
    public void onHomeClick() {
        Intent intent =
                new Intent(ArticlesActivity.this, HomeActivity.class);
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

    @OnClick(R.id.imgComment)
    public void onCommentClick() {
    }

    public void moveNext(int i) {
        Intent intent =
                new Intent(ArticlesActivity.this, GridHomeActivity.class);
        intent.putExtra("index", i);
        startActivity(intent);
    }
    @Override
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
                                new Intent(ArticlesActivity.this, SearchActivity.class);
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


    public void gotoHome(View v)
    {
        Intent intent =
                new Intent(ArticlesActivity.this, HomeActivity.class);
        startActivity(intent);
    }



    public void showModuleVideoPhoto(final File photoFile) {
        try {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void setPhoto(File photoFile) {
        showModuleVideoPhoto(photoFile);
    }

}
