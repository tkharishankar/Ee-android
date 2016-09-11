package com.eeyuva.screens.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;

import com.eeyuva.ButterAppCompatActivity;
import com.eeyuva.R;
import com.eeyuva.di.component.DaggerHomeComponent;
import com.eeyuva.di.component.HomeComponent;
import com.eeyuva.di.module.HomeModule;
import com.eeyuva.screens.home.coverflow.ArticlesAdapter;
import com.eeyuva.screens.home.coverflow.CoverFlowAdapter;
import com.eeyuva.screens.home.hotNewsCoverFlow.HotNewsCoverFlowAdapter;
import com.eeyuva.screens.home.loadmore.ArticlesActivity;
import com.eeyuva.screens.navigation.FragmentDrawer;
import com.eeyuva.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import it.moondroid.coverflow.components.ui.containers.FeatureCoverFlow;

/**
 * Created by hari on 05/09/16.
 */
public class HomeActivity extends ButterAppCompatActivity implements HomeContract.View {

    @Inject
    HomeContract.Presenter mPresenter;

    HomeComponent mComponent;

    private FragmentDrawer drawerFragment;

    @Bind(R.id.tool_bar)
    Toolbar mToolbar;

    @Bind(R.id.label)
    TextView label;

    @Bind(R.id.imgLoadMore)
    TextView txtLoadMore;

    private FeatureCoverFlow mCoverFlow;
    private FeatureCoverFlow mHotNewscoverflow;
    private CoverFlowAdapter mAdapter;
    private HotNewsCoverFlowAdapter mHotNewsAdapter;
    private TextSwitcher mTitle;
    private List<ResponseList> mModuleList = new ArrayList<ResponseList>();
    private List<ModuleList> mHotModuleList = new ArrayList<ModuleList>();

    @Bind(R.id.orderlist)
    RecyclerView mRecyclerView;

    ArticlesAdapter mArticleAdapter;

    RecyclerView.LayoutManager mLayoutManager;
    public int mScrolledToPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initComponent();
        mPresenter.setView(this);
        drawerFragment = (FragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);

        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAdapter = new CoverFlowAdapter(this);
        mModuleList = mPresenter.getModules();
        mAdapter.setData(mModuleList);
        mCoverFlow = (FeatureCoverFlow) findViewById(R.id.coverflow);
        mCoverFlow.setAdapter(mAdapter);

        mHotNewsAdapter = new HotNewsCoverFlowAdapter(this);
        mHotModuleList = mPresenter.getHotModules();
        mHotNewsAdapter.setData(mHotModuleList);
        mHotNewscoverflow = (FeatureCoverFlow) findViewById(R.id.hotNewscoverflow);
        mHotNewscoverflow.setAdapter(mHotNewsAdapter);


        mCoverFlow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(HomeActivity.this, "" + mModuleList.get(position).getTitle(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        mCoverFlow.setOnScrollPositionListener(new FeatureCoverFlow.OnScrollPositionListener() {

            @Override
            public void onScrolledToPosition(int position) {
                mScrolledToPosition = position;
                label.setText("" + mModuleList.get(position).getTitle());
                mPresenter.getArticles(mModuleList.get(position).getModuleid());
            }

            @Override
            public void onScrolling() {

            }
        });

        mHotNewscoverflow.setOnScrollPositionListener(new FeatureCoverFlow.OnScrollPositionListener() {
            @Override
            public void onScrolledToPosition(int position) {
            }

            @Override
            public void onScrolling() {

            }
        });

    }

    @OnClick(R.id.imgLoadMore)
    public void onLoadMoreClick() {
        Intent intent =
                new Intent(HomeActivity.this, ArticlesActivity.class);
        intent.putExtra("index", mModuleList.size());
        intent.putExtra("module_id", mModuleList.get(mScrolledToPosition).getModuleid());
        startActivity(intent);
    }

    private void initAdapter(List<ResponseItem> responseItem) {
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(this, 1);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mArticleAdapter = new ArticlesAdapter(this, responseItem);
        mRecyclerView.setAdapter(mArticleAdapter);
        mArticleAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        drawerFragment.setActivity(this);

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
        drawerFragment.setImage(mPresenter.getUserdetails());
    }

    private void initComponent() {
        mComponent = DaggerHomeComponent.builder()
                .appComponent(getApplicationComponent())
                .homeModule(new HomeModule(this))
                .build();
        mComponent.inject(this);
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
                break;
            case R.id.action_add:
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setDataToAdapter(List<ResponseList> response) {
        this.mModuleList = response;
        mAdapter.setData(mModuleList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void setArticleAdapterNotify(List<ResponseItem> responseItem) {
        initAdapter(responseItem);
    }

    @Override
    public void setLoadMoredata(GetArticleResponse responseBody) {

    }


}
