package com.eeyuva.screens.home;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.eeyuva.ButterAppCompatActivity;
import com.eeyuva.R;
import com.eeyuva.screens.navigation.FragmentDrawer;

import javax.inject.Inject;

import butterknife.Bind;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initComponent();
        mPresenter.setView(this);
        drawerFragment = (FragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

}
