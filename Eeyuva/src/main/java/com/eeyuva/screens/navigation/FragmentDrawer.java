package com.eeyuva.screens.navigation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Process;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eeyuva.R;
import com.eeyuva.screens.authentication.LoginActivity;
import com.eeyuva.screens.authentication.LoginResponse;
import com.eeyuva.screens.home.HomeActivity;
import com.eeyuva.screens.home.ResponseList;
import com.eeyuva.screens.home.loadmore.ArticlesActivity;
import com.eeyuva.screens.home.loadmore.RoundedTransformation;
import com.eeyuva.screens.profile.ChangePasswordActivity;
import com.eeyuva.screens.profile.alerts.AlertActivity;
import com.eeyuva.screens.profile.notification.NotificationActivity;
import com.eeyuva.screens.profile.stuffs.StuffsActivity;
import com.eeyuva.screens.profile.userdetails.ProfileActivity;
import com.eeyuva.screens.splash.SplashActivity;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

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

    public RelativeLayout LayProfile;

    Button mBtnStuff, mBtnAlert;

    ImageView imgProfile;
    TextView txtName;
    public List<ResponseList> mMenuModuleList = new ArrayList<ResponseList>();
    private String mLoginOut = "";
    private Gson gson;

    public FragmentDrawer() {
        // Required empty public constructor
    }

    private SharedPreferences remotePrefs;
    private SharedPreferences.Editor remoteEditor;
    String SHARED_PREFERENCES_REMOTE_KEY = "fetchr";

    String PREFS_USER_DETAILS = "UserName_Detail";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_drawer, container, false);
        gson = new Gson();

        mDrawerList = (ListView) view.findViewById(R.id.list);
        imgProfile = (ImageView) view.findViewById(R.id.imgProfile);
        txtName = (TextView) view.findViewById(R.id.txtName);
        mBtnStuff = (Button) view.findViewById(R.id.mBtnStuff);
        mBtnAlert = (Button) view.findViewById(R.id.mBtnAlerts);

        LayProfile = (RelativeLayout) view.findViewById(R.id.LayProfile);


        remotePrefs = getActivity().getSharedPreferences(SHARED_PREFERENCES_REMOTE_KEY, Context.MODE_PRIVATE);
        remoteEditor = remotePrefs.edit();

        LayProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getUserDetails() != null) {
                    Intent intent =
                            new Intent(getActivity(), ProfileActivity.class);
                    intent.putExtra("mode", "normal");
                    startActivity(intent);
                } else {
                    Intent intent =
                            new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
        mBtnStuff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getUserDetails() != null) {
                    Intent intent =
                            new Intent(getActivity(), StuffsActivity.class);
                    intent.putExtra("mode", "normal");
                    startActivity(intent);
                } else {
                    Intent intent =
                            new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }

//                getActivity().finish();
            }
        });
        mBtnAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getUserDetails() != null) {
                    Intent intent =
                            new Intent(getActivity(), AlertActivity.class);
                    intent.putExtra("mode", "normal");
                    startActivity(intent);
                } else {
                    Intent intent =
                            new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
        return view;
    }

    public void setUserDetail() {
        remoteEditor.remove(PREFS_USER_DETAILS);
        remoteEditor.putString(PREFS_USER_DETAILS, null);
        remoteEditor.commit();
    }

    public LoginResponse getUserDetails() {
        return gson.fromJson(remotePrefs.getString(PREFS_USER_DETAILS, null), LoginResponse.class);
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
        try {
            txtName.setText("" + prefsDetails.getFirstname());
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (prefsDetails.getPicpath() != null && prefsDetails.getPicpath().trim().length() != 0) {
                        Picasso.with(getActivity()).load(prefsDetails.getPicpath())
                                .transform(new RoundedTransformation(80, 0))
                                .resize(80, 80)
                                .centerCrop()
                                .into(imgProfile);
                        mLoginOut = "Logout";
                    } else {
                        mLoginOut = "Login";
                        imgProfile.setImageResource(R.drawable.ic_profile_default);
                    }
                }
            });
        } catch (Exception e) {
            mLoginOut = "Login";
            e.printStackTrace();
        }
    }

    private void setListDrawer() {
        mDrawerItems = new ArrayList<AppSetting>();
        mDrawerItems.add(new AppSetting("Entertainment", R.drawable.m_smile, true));
        mDrawerItems.add(new AppSetting("Box office", R.drawable.boxoffice, false));
        mDrawerItems.add(new AppSetting("Elements of music", R.drawable.music, false));
        mDrawerItems.add(new AppSetting("Jokes and sms", R.drawable.jokes, false));
        mDrawerItems.add(new AppSetting("Events and action", R.drawable.events, false));
//        mDrawerItems.add(new AppSetting("Puzzles and games", R.drawable.ic_content_add, false));
        mDrawerItems.add(new AppSetting("Tech trends", R.drawable.m_tech_trends, true));
        mDrawerItems.add(new AppSetting("Youth trends", R.drawable.youth, false));
        mDrawerItems.add(new AppSetting("Cars & Bikes", R.drawable.car, false));
        mDrawerItems.add(new AppSetting("Gadgets", R.drawable.gadgets, false));
        mDrawerItems.add(new AppSetting("It's my Life", R.drawable.m_its_my_life, true));
        mDrawerItems.add(new AppSetting("Health & fitness", R.drawable.fitness, false));
        mDrawerItems.add(new AppSetting("Beauty & glamour", R.drawable.beauty_glamor, false));
        mDrawerItems.add(new AppSetting("Fashion & styles", R.drawable.fashion, false));
        mDrawerItems.add(new AppSetting("Just food", R.drawable.m_food, false));
        mDrawerItems.add(new AppSetting("Travel & Tourism", R.drawable.travel, false));
        mDrawerItems.add(new AppSetting("Info Box", R.drawable.m_info_box, true));
        mDrawerItems.add(new AppSetting("Career & Jobs", R.drawable.m_caree_job, false));
        mDrawerItems.add(new AppSetting("Money & Business", R.drawable.money_business, false));
        mDrawerItems.add(new AppSetting("Sports & Games", R.drawable.sports_games, false));
        mDrawerItems.add(new AppSetting("Book mark", R.drawable.m_bookmark, false));
        mDrawerItems.add(new AppSetting("News room", R.drawable.newroom, false));
        mDrawerItems.add(new AppSetting("Account Settings", R.drawable.account_settings, true));
        mDrawerItems.add(new AppSetting("Change Password", 0, false));
        mDrawerItems.add(new AppSetting(mLoginOut, 0, false));
        mDrawerItems.add(new AppSetting("Notification", 0, false));
        mDrawerItems.add(new AppSetting("About App", 0, false));
        mDrawerItems.add(new AppSetting("Privacy", R.drawable.m_privacy, true));
        mDrawerItems.add(new AppSetting("Terms of use", 0, true));

        // setting the nav drawer list adapter
        mAdapter = new NavDrawerListAdapter(getActivity(), mDrawerItems);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                Intent i = new Intent();
//                switch (position) {
//                    case 22:
                try {
                    if (!mDrawerItems.get(position).isHeader()) {
                        if (mDrawerItems.get(position).getTitle().equalsIgnoreCase("logout")) {
                            mDrawerLayout.closeDrawer(containerView);
                            Intent intent =
                                    new Intent(getActivity(), SplashActivity.class);
                            setUserDetail();
                            startActivity(intent);
                        } else if (mDrawerItems.get(position).getTitle().equalsIgnoreCase("Change Password")) {
                            mDrawerLayout.closeDrawer(containerView);
                            Intent intent =
                                    new Intent(getActivity(), ChangePasswordActivity.class);
                            startActivity(intent);
                        } else if (mDrawerItems.get(position).getTitle().equalsIgnoreCase("login")) {
                            mDrawerLayout.closeDrawer(containerView);
                            Intent intent =
                                    new Intent(getActivity(), LoginActivity.class);
                            startActivity(intent);
                        } else if (mDrawerItems.get(position).getTitle().equalsIgnoreCase("notification")) {
                            if (getUserDetails() != null) {
                                Intent intent =
                                        new Intent(getActivity(), NotificationActivity.class);
                                startActivity(intent);
                            } else {
                                Intent intent =
                                        new Intent(getActivity(), LoginActivity.class);
                                startActivity(intent);
                            }
                        } else {
                            mDrawerLayout.closeDrawer(containerView);
                            ResponseList rl = getPosition(position);
                            Intent intent =
                                    new Intent(getActivity(), ArticlesActivity.class);
                            intent.putExtra("index", 0);
                            intent.putExtra("module_id", rl.getModuleid());
                            intent.putExtra("order_id", rl.getOrderid());
                            intent.putExtra("module_name", rl.getTitle());
                            startActivity(intent);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                        break;

//                }
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

    public void setList(List<ResponseList> modules) {
        this.mMenuModuleList = modules;
        Log.i("mMenuModuleList", "mMenuModuleList" + mMenuModuleList.size());
    }


    private ResponseList getPosition(int position) {
        String[] s = mDrawerItems.get(position).getTitle().split(" ");
        for (ResponseList Rl : mMenuModuleList) {
            Log.i("mMenuModuleListtitle", "mMenuModuleList" + Rl.getTitle() + "," + mDrawerItems.get(position).getTitle());
            if ((Rl.getTitle().toLowerCase()).contains(s[0].toLowerCase()))
                return Rl;
        }
        return null;
    }
}