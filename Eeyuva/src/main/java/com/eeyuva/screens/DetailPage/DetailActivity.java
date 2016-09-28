package com.eeyuva.screens.DetailPage;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eeyuva.ButterAppCompatActivity;
import com.eeyuva.R;
import com.eeyuva.di.component.DaggerDetailComponent;
import com.eeyuva.di.component.DetailComponent;
import com.eeyuva.di.module.DetailModule;
import com.eeyuva.screens.DetailPage.infiniteOtherCoverFlow.InfiniteOtherPagerAdapter;
import com.eeyuva.screens.DetailPage.model.CommentsList;
import com.eeyuva.screens.gridpages.GridHomeActivity;
import com.eeyuva.screens.gridpages.PhotoListAdapter;
import com.eeyuva.screens.home.HomeActivity;
import com.eeyuva.screens.home.loadmore.ArticlesLoadAdapter;
import com.eeyuva.screens.navigation.FragmentDrawer;
import com.eeyuva.screens.searchpage.SearchActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by hari on 14/09/16.
 */
public class DetailActivity extends ButterAppCompatActivity implements DetailContract.View {
    @Inject
    DetailContract.Presenter mPresenter;

    DetailComponent mComponent;

    @Bind(R.id.tool_bar)
    Toolbar mToolbar;

    @Bind(R.id.mTxtModuleName)
    TextView mTxtModuleName;

    @Bind(R.id.mTxtArticleTitle)
    TextView mTxtArticleTitle;

    @Bind(R.id.mTxtTimeInfo)
    TextView mTxtTimeInfo;

    @Bind(R.id.mTxtDetailInfo)
    WebView mTxtDetailInfo;

    @Bind(R.id.mImgModuleImg)
    ImageView mImgModuleImg;

    @Bind(R.id.mImgArticleImg)
    ImageView mImgArticleImg;

    private FragmentDrawer drawerFragment;

    private String mModuleId;
    private String mArticleId;
    private String mModuleName;
    private String mOrderId;
    public static List<ArticleDetail> mHotModuleList = new ArrayList<ArticleDetail>();


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

    @Bind(R.id.mBtnLike)
    TextView mBtnLike;

    @Bind(R.id.mBtnComment)
    TextView mBtnComment;

    @Bind(R.id.mBtnDislike)
    TextView mBtnDislike;

    @Bind(R.id.mBtnViewComment)
    TextView mBtnViewComment;

    @Bind(R.id.mBtnShare)
    TextView mBtnShare;

    ArticleDetail mArticleDetail;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        initComponent();
        mPresenter.setView(this);

        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawerFragment = (FragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);

        mModuleId = getIntent().getExtras().getString("module_id");
        mOrderId = getIntent().getExtras().getString("order_id");
        mArticleId = getIntent().getExtras().getString("article_id");
        mModuleName = getIntent().getExtras().getString("module_name");

        mTxtModuleName.setText(mModuleName);
        mImgModuleImg.setImageResource(getItem(Integer.parseInt(mOrderId)));
        mPresenter.getArticlesDetails(mModuleId, mArticleId);
        mPresenter.getOtherArticlesDetails(mModuleId, mArticleId);


    }

    private void initComponent() {
        mComponent = DaggerDetailComponent.builder()
                .appComponent(getApplicationComponent())
                .detailModule(new DetailModule(this))
                .build();
        mComponent.inject(this);
    }

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
        return images[i - 1];
    }

    public InfiniteOtherPagerAdapter infinitehotPagerAdapter;

    public ViewPager hotpager;

    public static int HotPAGES = 0;
    // You can choose a bigger number for LOOPS, but you know, nobody will fling
    // more than 1000 times just in order to test your "infinite" ViewPager :D
    public static int HotLOOPS = 100;
    public static int HotFIRST_PAGE;

    @Override
    public void setArticleDetails(ArticleDetail articleDetail) {
        try {
            mArticleDetail = articleDetail;
            mTxtArticleTitle.setText(articleDetail.getTitle());
            String posted = "Posted by: ";
            String mOn = " on ";
            String complete = "Posted by: " + articleDetail.getCreatedby() + " on " + articleDetail.getCreateddate();
            SpannableString styledString = new SpannableString("Posted by: " + articleDetail.getCreatedby() + " on " + articleDetail.getCreateddate());
            styledString.setSpan(new ForegroundColorSpan(Color.RED), 0, posted.length(), 0);
            styledString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorAccent)), posted.length(), posted.length() + articleDetail.getCreatedby().length(), 0);
            styledString.setSpan(new ForegroundColorSpan(Color.RED), posted.length() + articleDetail.getCreatedby().length(), complete.length(), 0);
            mTxtTimeInfo.setText(styledString);
            Picasso.with(this).load(articleDetail.getGalleryimg()).into(mImgArticleImg);
            mTxtDetailInfo.getSettings().setJavaScriptEnabled(true);
            mTxtDetailInfo.loadDataWithBaseURL("", articleDetail.getSummary(), "text/html", "UTF-8", "");
            mBtnLike.setText("Like(" + articleDetail.getLikecount() + ")");
            mBtnDislike.setText("Dislike(" + articleDetail.getDislikecount() + ")");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setOtherArticleDetails(List<ArticleDetail> response) {
        mHotModuleList = response;
        HotPAGES = mHotModuleList.size();
        HotFIRST_PAGE = HotPAGES * HotLOOPS / 2;
        hotpager = (ViewPager) findViewById(R.id.infinitehotviewpager);
        infinitehotPagerAdapter = new InfiniteOtherPagerAdapter(this, this.getSupportFragmentManager());
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

    @OnClick(R.id.imgHome)
    public void onHomeClick() {
        Intent intent =
                new Intent(DetailActivity.this, HomeActivity.class);
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
                new Intent(DetailActivity.this, GridHomeActivity.class);
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
                        Intent intent =
                                new Intent(DetailActivity.this, SearchActivity.class);
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

    @OnClick(R.id.mBtnShare)
    public void onShareClick() {
        showShareDialog();
    }

    @OnClick(R.id.mBtnLike)
    public void onLikeClick() {
        showLikeAndDislikeDialog(1);
    }

    @OnClick(R.id.mBtnDislike)
    public void onDislikeClick() {
        showLikeAndDislikeDialog(2);
    }

    @OnClick(R.id.mBtnComment)
    public void onDialogCommentClick() {
        showCommentDialog();
    }

    @OnClick(R.id.mBtnViewComment)
    public void onDialogViewCommentClick() {
        showViewCommentDialog();
    }


    public void showRatingDialog() {
        try {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_rating, null);
            builder.setView(dialogView);

            RelativeLayout LayRating = (RelativeLayout) dialogView.findViewById(R.id.LayRating);
            Button mBtnok = (Button) dialogView.findViewById(R.id.btnOk);
            Button mBtnCancel = (Button) dialogView.findViewById(R.id.btnCancel);
            TextView txtRate = (TextView) dialogView.findViewById(R.id.mTxtRate);
            ImageView imgRate = (ImageView) dialogView.findViewById(R.id.imgRate);
            String mRate = "Rate";
            String start = "Do you want to ";
            String end = " this article";
            String complete = start + mRate + end;
            SpannableString styledString = new SpannableString(complete);
            styledString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorAccent)), start.length(), start.length() + mRate.length(), 0);
            txtRate.setText(styledString);
            mBtnok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            mBtnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();

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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showShareDialog() {
        try {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_share, null);
            builder.setView(dialogView);

            RelativeLayout LayRating = (RelativeLayout) dialogView.findViewById(R.id.LayRating);
            Button mBtnok = (Button) dialogView.findViewById(R.id.btnOk);
            Button mBtnCancel = (Button) dialogView.findViewById(R.id.btnCancel);
            TextView txtRate = (TextView) dialogView.findViewById(R.id.mTxtRate);
            EditText mail = (EditText) dialogView.findViewById(R.id.mEdtMailid);
            String mRate = "Share";
            String start = "Do you want to ";
            String end = " this article";
            String complete = start + mRate + end;
            SpannableString styledString = new SpannableString(complete);
            styledString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorAccent)), start.length(), start.length() + mRate.length(), 0);
            txtRate.setText(styledString);
            mBtnok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();

                }
            });
            mBtnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();

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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showLikeAndDislikeDialog(int x) {
        try {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_like, null);
            builder.setView(dialogView);

            RelativeLayout LayRating = (RelativeLayout) dialogView.findViewById(R.id.LayRating);
            Button mBtnok = (Button) dialogView.findViewById(R.id.btnOk);
            Button mBtnCancel = (Button) dialogView.findViewById(R.id.btnCancel);
            TextView txtRate = (TextView) dialogView.findViewById(R.id.mTxtRate);
            ImageView imgRate = (ImageView) dialogView.findViewById(R.id.imgRate);
            String mRate = "Rate";

            if (x == 1) {
                type = 1;
                mRate = "Like";
                imgRate.setImageResource(R.drawable.like);
                mBtnok.setText("Like");
            } else {
                type = 0;
                mRate = "Dislike";
                mBtnok.setText("Dislike");
                imgRate.setImageResource(R.drawable.dislike);
            }

            String start = "Do you want to ";
            String end = " this article";
            String complete = start + mRate + end;
            SpannableString styledString = new SpannableString(complete);
            styledString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorAccent)), start.length(), start.length() + mRate.length(), 0);
            txtRate.setText(styledString);
            mBtnok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                    mPresenter.setLikeOrDislike(mArticleDetail.getArticleid(), "" + type, mModuleId, mArticleDetail.getCategoryid());
                }
            });
            mBtnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void showCommentDialog() {
        try {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_comment, null);
            builder.setView(dialogView);

            RelativeLayout LayRating = (RelativeLayout) dialogView.findViewById(R.id.LayRating);
            Button mBtnok = (Button) dialogView.findViewById(R.id.btnOk);
            Button mBtnCancel = (Button) dialogView.findViewById(R.id.btnCancel);
            TextView txtRate = (TextView) dialogView.findViewById(R.id.mTxtRate);
            final EditText comments = (EditText) dialogView.findViewById(R.id.mEdtMailid);

            mBtnok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                    mPresenter.setPostComments(comments.getText().toString().trim(), mModuleId, mArticleDetail.getArticleid());
                }
            });
            mBtnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();

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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showViewCommentDialog() {
        try {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_view_comment, null);
            builder.setView(dialogView);

            RelativeLayout LayRating = (RelativeLayout) dialogView.findViewById(R.id.LayRating);
            Button mBtnok = (Button) dialogView.findViewById(R.id.btnOk);
            Button mBtnCancel = (Button) dialogView.findViewById(R.id.btnCancel);
            TextView txtRate = (TextView) dialogView.findViewById(R.id.mTxtRate);
            final EditText comments = (EditText) dialogView.findViewById(R.id.mEdtMailid);

            mlistview = (RecyclerView) dialogView.findViewById(R.id.orderlist);
            mPresenter.getViewComments(mModuleId);

            mBtnok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                    mPresenter.setPostComments(comments.getText().toString().trim(), mModuleId, mArticleDetail.getArticleid());

                }
            });
            mBtnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();

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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    RecyclerView mlistview;
    RecyclerView.LayoutManager mLayoutManager;
    CommentsLoadAdapter mCommentsAdapter;

    @Override
    public void setCommentsListToAdapter(List<CommentsList> response) {
        mlistview.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(this, 1);
        mlistview.setLayoutManager(mLayoutManager);
        mCommentsAdapter = new CommentsLoadAdapter(this, response);
        mlistview.setAdapter(mCommentsAdapter);
        mCommentsAdapter.notifyDataSetChanged();
    }
}
