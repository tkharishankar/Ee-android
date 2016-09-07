package com.eeyuva.screens.navigation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.eeyuva.R;
import com.eeyuva.screens.authentication.LoginActivity;
import com.eeyuva.screens.home.HomeActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDrawer extends Fragment implements View.OnClickListener {

    private final String LOG_TAG = this.getClass().getSimpleName();

    private NavDrawerListAdapter mAdapter;
    private List<AppSetting> mDrawerItems;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private View containerView;
    private View view;
    private Context mContext;

    public FragmentDrawer() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_drawer, container, false);
        mDrawerList = (ListView) view.findViewById(R.id.list);
        return view;
    }


    public void setActivity(Context context) {
        mContext = context;
    }

    @Override
    public void onResume() {
        super.onResume();
        setListDrawer();
    }

    private void setListDrawer() {
        mDrawerItems = new ArrayList<AppSetting>();
        mDrawerItems.add(new AppSetting("Box office", R.drawable.ic_content_add));
        mDrawerItems.add(new AppSetting("Elements of music", R.drawable.ic_content_add));
        mDrawerItems.add(new AppSetting("Jokes and sms", R.drawable.ic_content_add));
        mDrawerItems.add(new AppSetting("Events and action", R.drawable.ic_content_add));
        mDrawerItems.add(new AppSetting("Puzzles and games", R.drawable.ic_content_add));
        mDrawerItems.add(new AppSetting("Logout", R.drawable.ic_content_add));

        // setting the nav drawer list adapter
        mAdapter = new NavDrawerListAdapter(getActivity(), mDrawerItems);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mDrawerLayout.closeDrawer(containerView);

                    Intent i = new Intent();
                    switch (position) {
                        case 5:
                            i.setClass(getActivity(), LoginActivity.class);
                            startActivity(i);
                            break;

                    }
                }

        });
    }


    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) throws IllegalAccessException {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new EeyuvaActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }

    @Override
    public void onClick(View v) {

    }
}