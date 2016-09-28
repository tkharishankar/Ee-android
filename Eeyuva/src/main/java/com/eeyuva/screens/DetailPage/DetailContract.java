package com.eeyuva.screens.DetailPage;

import com.eeyuva.base.BasePresenter;
import com.eeyuva.base.BaseView;
import com.eeyuva.screens.DetailPage.model.CommentsList;
import com.eeyuva.screens.home.ResponseList;

import java.util.List;

/**
 * Created by hari on 14/09/16.
 */
public interface DetailContract {
    interface View extends BaseView {

        void setArticleDetails(ArticleDetail articleDetail);

        void setOtherArticleDetails(List<ArticleDetail> response);

        void setCommentsListToAdapter(List<CommentsList> response);
    }

    interface Presenter extends BasePresenter {

        void getArticlesDetails(String mModuleId, String mArticleId);

        void getOtherArticlesDetails(String mModuleId, String mArticleId);

        List<ResponseList> getModules();

        void setLikeOrDislike(String article_id, String type, String module_id, String uid);

        void getViewComments(String mModuleId);

        void setPostComments(String trim, String mModuleId, String articleid);
    }

    public interface AdapterCallBack {
    }
}
