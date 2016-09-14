package com.eeyuva.screens.DetailPage;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.eeyuva.ButterAppCompatActivity;
import com.eeyuva.R;
import com.eeyuva.di.component.DaggerDetailComponent;
import com.eeyuva.di.component.DetailComponent;
import com.eeyuva.di.module.DetailModule;
import com.eeyuva.screens.DetailPage.infiniteOtherCoverFlow.InfiniteOtherPagerAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

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

    private String mModuleId;
    private String mArticleId;
    private String mModuleName;
    private String mOrderId;
    public static List<ArticleDetail> mHotModuleList = new ArrayList<ArticleDetail>();

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
}
