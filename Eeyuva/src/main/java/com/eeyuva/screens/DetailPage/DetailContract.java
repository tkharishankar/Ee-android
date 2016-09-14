package com.eeyuva.screens.DetailPage;

import com.eeyuva.base.BasePresenter;
import com.eeyuva.base.BaseView;

import java.util.List;

/**
 * Created by hari on 14/09/16.
 */
public interface DetailContract {
    interface View extends BaseView {

        void setArticleDetails(ArticleDetail articleDetail);

        void setOtherArticleDetails(List<ArticleDetail> response);
    }

    interface Presenter extends BasePresenter {

        void getArticlesDetails(String mModuleId, String mArticleId);

        void getOtherArticlesDetails(String mModuleId, String mArticleId);
    }
}
