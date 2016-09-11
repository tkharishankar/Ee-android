package com.eeyuva.screens.home.loadmore;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.eeyuva.ButterAppCompatActivity;
import com.eeyuva.R;
import com.eeyuva.di.component.DaggerHomeComponent;
import com.eeyuva.di.component.HomeComponent;
import com.eeyuva.di.module.HomeModule;
import com.eeyuva.screens.home.GetArticleResponse;
import com.eeyuva.screens.home.HomeContract;
import com.eeyuva.screens.home.ResponseItem;
import com.eeyuva.screens.home.ResponseList;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by hari on 05/09/16.
 */
public class ArticlesActivity extends ButterAppCompatActivity implements HomeContract.View {

    @Inject
    HomeContract.Presenter mPresenter;

    HomeComponent mComponent;


    @Bind(R.id.tool_bar)
    Toolbar mToolbar;

    private List<ResponseItem> mArticlesList = new ArrayList<ResponseItem>();

    private int mPrevIndex = 0;

    private String mModuleId;

    @Bind(R.id.orderlist)
    RecyclerView mRecyclerView;

    ArticlesLoadAdapter mArticleAdapter;

    RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        initComponent();
        mPresenter.setView(this);

        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPrevIndex = getIntent().getExtras().getInt("index");
        mModuleId = getIntent().getExtras().getString("module_id");
        mPresenter.getArticles(mModuleId);
    }

    private void initAdapter(final List<ResponseItem> responseItem) {
        this.mArticlesList = responseItem;
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(this, 1);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mArticleAdapter = new ArticlesLoadAdapter(this, mArticlesList);
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
            mLayoutManager.scrollToPosition(mArticlesList.size()-5);
        } catch (Exception e) {
            e.printStackTrace();
        }

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

    }

    @Override
    public void setArticleAdapterNotify(List<ResponseItem> responseItem) {
        initAdapter(responseItem);
    }


}
