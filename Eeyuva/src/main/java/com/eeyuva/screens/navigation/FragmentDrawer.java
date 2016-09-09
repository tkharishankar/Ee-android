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
import android.widget.TextView;

import com.eeyuva.R;
import com.eeyuva.screens.authentication.LoginActivity;
import com.eeyuva.screens.authentication.LoginResponse;
import com.eeyuva.screens.home.HomeActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.socket.client.Url;

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

    ImageView imgProfile;
    TextView txtName;
    public FragmentDrawer() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_drawer, container, false);
        mDrawerList = (ListView) view.findViewById(R.id.list);
        imgProfile=(ImageView)view.findViewById(R.id.imgProfile);
        txtName=(TextView)view.findViewById(R.id.txtName);
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
    public void setImage(final LoginResponse prefsDetails) {
        txtName.setText(""+prefsDetails.getFirstname());
//        getActivity().runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                if (prefsDetails.getPicpath() != null && prefsDetails.getPicpath().trim().length() != 0) {
//                    Picasso.with(getActivity()).load( prefsDetails.getPicpath())
//                            .transform(new RoundedTransformation(80, 0))
//                            .resize(80, 80)
//                            .centerCrop()
//                            .into(imgProfile);
//                }
//                else
//                {
//                    imgProfile.setImageResource(R.drawable.ic_profile_default);
//                }
//            }
//        });
    }
    private void setListDrawer() {
        mDrawerItems = new ArrayList<AppSetting>();
        mDrawerItems.add(new AppSetting("Entertainment", R.drawable.ic_content_add,true));
        mDrawerItems.add(new AppSetting("Box office", R.drawable.ic_content_add,false));
        mDrawerItems.add(new AppSetting("Elements of music", R.drawable.ic_content_add,false));
        mDrawerItems.add(new AppSetting("Jokes and sms", R.drawable.ic_content_add,false));
        mDrawerItems.add(new AppSetting("Events and action", R.drawable.ic_content_add,false));
        mDrawerItems.add(new AppSetting("Puzzles and games", R.drawable.ic_content_add,false));
        mDrawerItems.add(new AppSetting("Tech trends", R.drawable.ic_content_add,true));
        mDrawerItems.add(new AppSetting("Youth trends", R.drawable.ic_content_add,false));
        mDrawerItems.add(new AppSetting("Cars & Bikes", R.drawable.ic_content_add,false));
        mDrawerItems.add(new AppSetting("Gadgets", R.drawable.ic_content_add,false));
        mDrawerItems.add(new AppSetting("It's my Life", R.drawable.ic_content_add,true));
        mDrawerItems.add(new AppSetting("Health & fitness", R.drawable.ic_content_add,false));
        mDrawerItems.add(new AppSetting("Beauty & glamour", R.drawable.ic_content_add,false));
        mDrawerItems.add(new AppSetting("Fashion & styles", R.drawable.ic_content_add,false));
        mDrawerItems.add(new AppSetting("Just food", R.drawable.ic_content_add,false));
        mDrawerItems.add(new AppSetting("Travel & Tourism", R.drawable.ic_content_add,false));
        mDrawerItems.add(new AppSetting("Info Box", R.drawable.ic_content_add,true));
        mDrawerItems.add(new AppSetting("Career & Jobs", R.drawable.ic_content_add,false));
        mDrawerItems.add(new AppSetting("Money & Business", R.drawable.ic_content_add,false));
        mDrawerItems.add(new AppSetting("Sports & Games", R.drawable.ic_content_add,false));
        mDrawerItems.add(new AppSetting("Book mark", R.drawable.ic_content_add,false));
        mDrawerItems.add(new AppSetting("News room", R.drawable.ic_content_add,false));
        mDrawerItems.add(new AppSetting("Logout", R.drawable.ic_content_add,false));

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