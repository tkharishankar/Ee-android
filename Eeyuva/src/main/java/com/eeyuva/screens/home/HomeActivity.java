package com.eeyuva.screens.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.LinkagePager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
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
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.eeyuva.ButterAppCompatActivity;
import com.eeyuva.R;
import com.eeyuva.di.component.DaggerHomeComponent;
import com.eeyuva.di.component.HomeComponent;
import com.eeyuva.di.module.HomeModule;
import com.eeyuva.screens.DetailPage.DetailActivity;
import com.eeyuva.screens.gridpages.GridHomeActivity;
import com.eeyuva.screens.home.coverflow.ArticlesAdapter;
import com.eeyuva.screens.home.coverflow.CoverFlowAdapter;
import com.eeyuva.screens.home.hotNewsCoverFlow.HotNewsCoverFlowAdapter;
import com.eeyuva.screens.home.infiniteCoverFlow.InfinitePagerAdapter;
import com.eeyuva.screens.home.infiniteHotCoverFlow.InfiniteHotFragment;
import com.eeyuva.screens.home.infiniteHotCoverFlow.InfiniteHotPagerAdapter;
import com.eeyuva.screens.home.loadmore.ArticlesActivity;
import com.eeyuva.screens.navigation.FragmentDrawer;
import com.eeyuva.screens.searchpage.SearchActivity;
import com.eeyuva.screens.searchpage.model.SearchResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import it.moondroid.coverflow.components.ui.containers.FeatureCoverFlow;
import me.crosswall.lib.coverflow.core.LinkageCoverTransformer;
import me.crosswall.lib.coverflow.core.LinkagePagerContainer;

/**
 * Created by hari on 05/09/16.
 */
public class HomeActivity extends ButterAppCompatActivity implements HomeContract.View, HomeContract.AdapterCallBack, InfiniteHotFragment.CommmunicateListener {

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


    private FeatureCoverFlow mCoverFlow;
    private FeatureCoverFlow mHotNewscoverflow;
    private CoverFlowAdapter mAdapter;
    private HotNewsCoverFlowAdapter mHotNewsAdapter;
    private TextSwitcher mTitle;
    public static List<ResponseList> mModuleList = new ArrayList<ResponseList>();
    public static List<ResponseList> mFinalModuleList = new ArrayList<ResponseList>();
    public static List<ModuleList> mHotModuleList = new ArrayList<ModuleList>();

    @Bind(R.id.orderlist)
    RecyclerView mRecyclerView;

    @Bind(R.id.Layscroll)
    ScrollView Layscroll;

    ArticlesAdapter mArticleAdapter;

    RecyclerView.LayoutManager mLayoutManager;
    public int mScrolledToPosition;


    private LinkagePagerContainer customPagerContainer;
    //    private LinkagePager pager;
    private AppBarLayout appBarLayout;
    private int parallaxHeight;
    private View tab;
    boolean initialflag;


    public static int PAGES = 0;
    // You can choose a bigger number for LOOPS, but you know, nobody will fling
    // more than 1000 times just in order to test your "infinite" ViewPager :D
    public static int LOOPS = 10;
    public static int FIRST_PAGE;

    public InfinitePagerAdapter infinitePagerAdapter;
    public InfiniteHotPagerAdapter infinitehotPagerAdapter;
    public ViewPager pager;
    public ViewPager hotpager;

    public static int HotPAGES = 0;
    // You can choose a bigger number for LOOPS, but you know, nobody will fling
    // more than 1000 times just in order to test your "infinite" ViewPager :D
    public static int HotLOOPS = 10;
    public static int HotFIRST_PAGE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initComponent();
        mPresenter.setView(this);
        drawerFragment = (FragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        Log.i("Device", "device size" + getResources().getString(R.string.size));
        setSupportActionBar(mToolbar);
//        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mModuleList = mPresenter.getModules();
        mHotModuleList = mPresenter.getHotModules();

        for (int i = 0; i <= 1000; i++) {
            mFinalModuleList.addAll(mModuleList);
        }

        PAGES = mModuleList.size();
        FIRST_PAGE = PAGES * LOOPS / 2;


        HotPAGES = mHotModuleList.size();
        HotFIRST_PAGE = HotPAGES * HotLOOPS / 2;

        pager = (ViewPager) findViewById(R.id.infiniteviewpager);

        infinitePagerAdapter = new InfinitePagerAdapter(this, this.getSupportFragmentManager());
        pager.setAdapter(infinitePagerAdapter);
        pager.setPageTransformer(false, infinitePagerAdapter);

        // Set current item to the middle page so we can fling to both
        // directions left and right
        pager.setCurrentItem(FIRST_PAGE);

        // Necessary or the pager will only have one extra page to show
        // make this at least however many pages you can see
        pager.setOffscreenPageLimit(3);

        // Set margin for pages as a negative number, so a part of next and
        // previous pages will be showed
//        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
//        if (currentapiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//            // Do something for lollipop and above versions
//            pager.setPageMargin(-700);
//        } else {
////         do something for phones running an SDK before lollipop
//            pager.setPageMargin(-400);
//        }
        if (getResources().getString(R.string.size).equals("mdpi"))
            pager.setPageMargin(-600);
        else if (getResources().getString(R.string.size).equals("hdpi"))
            pager.setPageMargin(-650);
        else if (getResources().getString(R.string.size).equals("xhdpi"))
            pager.setPageMargin(-650);
        else if (getResources().getString(R.string.size).equals("xxhdpi"))
            pager.setPageMargin(-650);

        Log.i("screen size", "screen" + getResources().getString(R.string.dimens));


        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.i("position", "onPageScrolled" + position);
                if (!initialflag) {
                    label.setText(mModuleList.get((position % mModuleList.size())).getTitle());
                    mPresenter.getArticles(mModuleList.get((position % mModuleList.size())).getModuleid());
                }
            }

            @Override
            public void onPageSelected(int position) {
                Log.i("position", "onPageSelected" + position);
                mScrolledToPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.i("position", "onPageScrollStateChanged" + state);
                Log.i("position", "mScrolledToPosition" + mScrolledToPosition);

                if (state == 0) {
                    label.setText(mModuleList.get((mScrolledToPosition % mModuleList.size())).getTitle());
                    mPresenter.getArticles(mModuleList.get((mScrolledToPosition % mModuleList.size())).getModuleid());
                }
            }
        });

//        mAdapter = new CoverFlowAdapter(this);
//        mAdapter.setData(mModuleList);
//        mCoverFlow = (FeatureCoverFlow) findViewById(R.id.coverflow);
//        mCoverFlow.setAdapter(mAdapter);
//
//        mHotNewsAdapter = new HotNewsCoverFlowAdapter(this);
//        mHotNewsAdapter.setData(mHotModuleList);
//        mHotNewscoverflow = (FeatureCoverFlow) findViewById(R.id.hotNewscoverflow);
//        mHotNewscoverflow.setAdapter(mHotNewsAdapter);


//        mCoverFlow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(HomeActivity.this, "" + mModuleList.get(position).getTitle(),
//                        Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        mCoverFlow.setOnScrollPositionListener(new FeatureCoverFlow.OnScrollPositionListener() {
//
//            @Override
//            public void onScrolledToPosition(int position) {
//                mScrolledToPosition = position;
////                label.setText("" + mModuleList.get(position).getTitle());
//                mPresenter.getArticles(mModuleList.get(position).getModuleid());
//            }
//
//            @Override
//            public void onScrolling() {
//
//            }
//        });

//        mHotNewscoverflow.setOnScrollPositionListener(new FeatureCoverFlow.OnScrollPositionListener() {
//            @Override
//            public void onScrolledToPosition(int position) {
//            }
//
//            @Override
//            public void onScrolling() {
//
//            }
//        });
//
//        parallaxHeight = getResources().getDimensionPixelSize(R.dimen.cover_pager_height) - getResources().getDimensionPixelSize(R.dimen.tab_height);
//
//        Log.d("###", "parallaxHeight:" + parallaxHeight);

//        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);

//        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//            @Override
//            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                // Log.d("###","verticalOffset: " + Math.abs(verticalOffset));
////                if(Math.abs(verticalOffset) >= parallaxHeight){
////                    tab.setVisibility(View.VISIBLE);
////                }else{
////                    tab.setVisibility(View.GONE);
////                }
//
//            }
//        });

//        customPagerContainer = (LinkagePagerContainer) findViewById(R.id.pager_container);
//
////        tab = findViewById(R.id.tab);
//        final LinkagePager cover = customPagerContainer.getViewPager();
//
//        StuffPagerAdapter coverAdapter = new MyPagerAdapter();
//        cover.setAdapter(coverAdapter);
//        cover.setOffscreenPageLimit(mModuleList.size());
//
//        new CoverFlow.Builder()
//                .withLinkage(cover)
//                .pagerMargin(0f)
//                .scale(0.3f)
//                .spaceSize(0f)
//                .build();
//
//        cover.addOnPageChangeListener(new LinkagePager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                Log.i("position", "onPageScrolled" + position);
//                if (!initialflag) {
//                    label.setText("" + mModuleList.get(position).getTitle());
//                    mPresenter.getArticles(mModuleList.get(position).getModuleid());
//                }
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                Log.i("position", "onPageSelected" + position);
//                mScrolledToPosition = position;
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//                Log.i("position", "onPageScrollStateChanged" + state);
//                if (state == 0) {
//                    label.setText("" + mModuleList.get(mScrolledToPosition).getTitle());
//                    mPresenter.getArticles(mModuleList.get(mScrolledToPosition).getModuleid());
//                }
//
//            }
//        });
//        pager = (LinkagePager) findViewById(R.id.pager);
//
//        MyListPagerAdapter adapter = new MyListPagerAdapter();
//
//        pager.setOffscreenPageLimit(5);
//        pager.setAdapter(adapter);
//
//        cover.setLinkagePager(pager);
//        pager.setLinkagePager(cover);


//        PagerContainer container = (PagerContainer) findViewById(R.id.bottom_pager_container);
//        ViewPager pager = container.getViewPager();
//        pager.setAdapter(new BottomPagerAdapter());
//        pager.setClipChildren(false);
//        pager.setOffscreenPageLimit(15);
//        new CoverFlow.Builder()
//                .with(pager)
//                .scale(0.3f)
//                .pagerMargin(0)
//                .spaceSize(0f)
//                .build();

        hotpager = (ViewPager) findViewById(R.id.infinitehotviewpager);
        infinitehotPagerAdapter = new InfiniteHotPagerAdapter(this, this.getSupportFragmentManager());
        hotpager.setAdapter(infinitehotPagerAdapter);
        hotpager.setPageTransformer(false, infinitehotPagerAdapter);

        // Set current item to the middle page so we can fling to both
        // directions left and right
        hotpager.setCurrentItem(HotFIRST_PAGE);

        // Necessary or the pager will only have one extra page to show
        // make this at least however many pages you can see
        hotpager.setOffscreenPageLimit(3);

        // Set margin for pages as a negative number, so a part of next and
        // previous pages will be showed
        hotpager.setPageMargin(0);


        LinkagePagerContainer mContainer = (LinkagePagerContainer) findViewById(R.id.pager_container);

        final LinkagePager pager = mContainer.getViewPager();

        final PagerAdapter adapter = new MyPagerAdapter();

        pager.setAdapter(adapter);

        pager.setOffscreenPageLimit(3);

        pager.setClipChildren(false);

        pager.setPageMargin(20);

        pager.setPageTransformer(false, new LinkageCoverTransformer(0.3f, 0f, 0f, 0f));
        mScrolledToPosition = mFinalModuleList.size() / 2;
        pager.setCurrentItem(mScrolledToPosition++);
//        label.setText(mFinalModuleList.get((mScrolledToPosition % mFinalModuleList.size())).getTitle());
//        mPresenter.getArticles(mFinalModuleList.get((mScrolledToPosition % mFinalModuleList.size())).getModuleid());
        pager.addOnPageChangeListener(new LinkagePager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.i("position", "onPageScrolled" + position);
                if (!initialflag) {
                    label.setText(mFinalModuleList.get((position+1 % mFinalModuleList.size())).getTitle());
                    mPresenter.getArticles(mFinalModuleList.get((position+1 % mFinalModuleList.size())).getModuleid());
                    mScrolledToPosition=position+1;
                }
            }

            @Override
            public void onPageSelected(int position) {
                Log.i("position", "onPageSelected" + position);
                mScrolledToPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.i("position", "onPageScrollStateChanged" + state);
                Log.i("position", "mScrolledToPosition" + mScrolledToPosition);

                if (state == 0) {
                    label.setText(mFinalModuleList.get((mScrolledToPosition % mFinalModuleList.size())).getTitle());
                    mPresenter.getArticles(mFinalModuleList.get((mScrolledToPosition % mFinalModuleList.size())).getModuleid());
                }
            }
        });
    }

    @OnClick(R.id.imgLoadMore)
    public void onLoadMoreClick() {
        Intent intent =
                new Intent(HomeActivity.this, ArticlesActivity.class);
        intent.putExtra("index", mFinalModuleList.size());
        intent.putExtra("module_id", mFinalModuleList.get(mScrolledToPosition % mFinalModuleList.size()).getModuleid());
        intent.putExtra("order_id", mFinalModuleList.get(mScrolledToPosition % mFinalModuleList.size()).getOrderid());
        intent.putExtra("module_name", mFinalModuleList.get(mScrolledToPosition % mFinalModuleList.size()).getTitle());
        startActivity(intent);
    }

    @OnClick(R.id.imgHome)
    public void onHomeClick() {
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
                new Intent(HomeActivity.this, GridHomeActivity.class);
        intent.putExtra("index", i);
        startActivity(intent);
    }

    private void initAdapter(List<ResponseItem> responseItem) {
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(this, 1);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mArticleAdapter = new ArticlesAdapter(this, this, responseItem);
        mRecyclerView.setAdapter(mArticleAdapter);
        mArticleAdapter.notifyDataSetChanged();
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
//        drawerFragment.setImage(mPresenter.getUserdetails());
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
                showDialog();
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
        initialflag = true;
        Layscroll.setVisibility(View.VISIBLE);

        initAdapter(responseItem);
    }

    @Override
    public void setLoadMoredata(GetArticleResponse responseBody) {

    }

    @Override
    public void setLoadMoredata(SearchResponse responseBody) {

    }

    @Override
    public void onItemClick(String articleid, String modid) {
        Intent intent =
                new Intent(HomeActivity.this, DetailActivity.class);
        intent.putExtra("article_id", articleid);
        intent.putExtra("module_id", mFinalModuleList.get(mScrolledToPosition % mFinalModuleList.size()).getModuleid());
//        intent.putExtra("order_id", mFinalModuleList.get(mScrolledToPosition % mFinalModuleList.size()).getOrderid());
        intent.putExtra("type", "home");
        startActivity(intent);
    }

    @Override
    public void showUpdatedDetails(String module_id, String entityid) {
//        for (ModuleList ml : mHotModuleList) {
//            if (ml.getModid().equals(module_id)) {
                Intent intent =
                        new Intent(HomeActivity.this, DetailActivity.class);
                intent.putExtra("article_id", entityid);
                intent.putExtra("module_id", module_id);
        intent.putExtra("type", "home");
                startActivity(intent);
                finish();
//            }
//        }

    }

    class MyListPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mModuleList.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            DataDemoView view = new DataDemoView(HomeActivity.this);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }


        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    private class MyPagerAdapter extends PagerAdapter {
        private int[] images = {R.drawable.img_1,
                R.drawable.img_2,
                R.drawable.img_3,
                R.drawable.img_4,
                R.drawable.img_5,
                R.drawable.img_6,
                R.drawable.img_7,
                R.drawable.img_8,
                R.drawable.img_9,
                R.drawable.img_10,
                R.drawable.img_11,
                R.drawable.img_12,
                R.drawable.img_13,
                R.drawable.img_14,
                R.drawable.img_15,
                R.drawable.img_16,
                R.drawable.img_17,
                R.drawable.img_18,
        };

        public Integer getItem(int i) {
            return images[i-1];
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView view = new ImageView(HomeActivity.this);
            Log.i("position", "position" + position);
            view.setImageResource(getItem(Integer.parseInt(mFinalModuleList.get(position).getOrderid())));
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return mFinalModuleList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return (view == object);
        }
    }


    private class BottomPagerAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            View view = LayoutInflater.from(HomeActivity.this).inflate(R.layout.item_cover, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.image_cover);
            TextView label = (TextView) view.findViewById(R.id.label);
            Picasso.with(HomeActivity.this).load(mHotModuleList.get(position).getPicpath()).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            label.setText(mHotModuleList.get(position).getTitle());
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return mHotModuleList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return (view == object);
        }
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
                                new Intent(HomeActivity.this, SearchActivity.class);
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

    }

}
