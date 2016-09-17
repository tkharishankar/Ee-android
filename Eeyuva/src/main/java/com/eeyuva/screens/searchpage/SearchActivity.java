package com.eeyuva.screens.searchpage;

import android.content.Intent;
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
import com.eeyuva.screens.home.loadmore.ArticlesLoadAdapter;
import com.eeyuva.screens.navigation.FragmentDrawer;
import com.eeyuva.screens.searchpage.model.Doc;
import com.eeyuva.screens.searchpage.model.SearchResponse;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by hari on 05/09/16.
 */
public class SearchActivity extends ButterAppCompatActivity implements HomeContract.View, HomeContract.AdapterCallBack {

    @Inject
    HomeContract.Presenter mPresenter;

    HomeComponent mComponent;
    private FragmentDrawer drawerFragment;


    @Bind(R.id.tool_bar)
    Toolbar mToolbar;

    private List<Doc> mDocsList = new ArrayList<Doc>();

    private int mPrevIndex = 0;

    String mModuleId;

    String mModuleName;

    @Bind(R.id.orderlist)
    RecyclerView mRecyclerView;

    @Bind(R.id.txtHotStories)
    TextView mTxtHotStories;

    SearchLoadAdapter mSearchAdapter;

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
    private String mkeyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initComponent();
        mPresenter.setView(this);

        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerFragment = (FragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);

        mkeyword = getIntent().getExtras().getString("keyword");
        mPresenter.getSearchResponse(mkeyword);
    }

    private void initAdapter(final List<Doc> responseItem) {
        this.mDocsList = responseItem;
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(this, 1);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mSearchAdapter = new SearchLoadAdapter(this, this, mDocsList);
//        mSearchAdapter.setLoadMoreListener(new SearchLoadAdapter.OnLoadMoreListener() {
//            @Override
//            public void onLoadMore() {
//
//                mRecyclerView.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        int index = mDocsList.size();
//                        loadMore(index);
//                    }
//
//
//                });
//                //Calling loadMore function in Runnable to fix the
//                // java.lang.IllegalStateException: Cannot call this method while RecyclerView is computing a layout or scrolling error
//            }
//        });
        mRecyclerView.setAdapter(mSearchAdapter);
        mSearchAdapter.notifyDataSetChanged();
    }

    @Override
    public void setLoadMoredata(GetArticleResponse responseBody) {
        //remove loading view

    }

    @Override
    public void setLoadMoredata(SearchResponse responseBody) {
        initAdapter(responseBody.getResponse().getDocs());
        mTxtHotStories.setText(responseBody.getResponse().getDocs().size() + " results crawled for " + mkeyword + " Keyword");
//        mDocsList.remove(mDocsList.size() - 1);
//        Log.i("index", "mArticlesList after rem" + mDocsList.size());
//
//        List<Doc> result = responseBody.getResponse().getDocs();
//        Log.i("index", "mArticlesList result" + result.size());
//
//        if (result.size() > 0) {
//            //add loaded data
//            mDocsList.addAll(result);
//        } else {//result size 0 means there is no more data available at server
//            mSearchAdapter.setMoreDataAvailable(false);
//            //telling adapter to stop calling load more as no more server data available
//            Toast.makeText(this, "No More Data Available", Toast.LENGTH_LONG).show();
//        }
//        mSearchAdapter.notifyDataChanged();
//        Log.i("index", "mArticlesList after load" + mDocsList.size());
//
//        try {
//            mLayoutManager.scrollToPosition(mDocsList.size() - 5);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    private void loadMore(int index) {
        Log.i("index", "mArticlesList before" + mDocsList.size());
        Log.i("index", "index" + index);
        mDocsList.add(new Doc(true));
        mSearchAdapter.notifyItemInserted(mDocsList.size() - 1);
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
    }


    @Override
    public void onItemClick(String articleid) {
        Intent intent =
                new Intent(SearchActivity.this, DetailActivity.class);
        intent.putExtra("module_id", mModuleId);
        intent.putExtra("article_id", articleid);
        intent.putExtra("order_id", mOrderId);
        intent.putExtra("module_name", mModuleName);
        startActivity(intent);
    }

    @OnClick(R.id.imgHome)
    public void onHomeClick() {
        Intent intent =
                new Intent(SearchActivity.this, HomeActivity.class);
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
                new Intent(SearchActivity.this, GridHomeActivity.class);
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
                        mPresenter.getSearchResponse(mkeyword);
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
}
