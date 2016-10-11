package com.eeyuva.screens.DetailPage;

import android.content.Intent;
import android.util.Log;

import com.eeyuva.base.BaseView;
import com.eeyuva.base.LoadListener;
import com.eeyuva.interactor.ApiInteractor;
import com.eeyuva.screens.DetailPage.model.CommentListResponse;
import com.eeyuva.screens.DetailPage.model.CommentPostResponse;
import com.eeyuva.screens.DetailPage.model.LikeDislikeResponse;
import com.eeyuva.screens.home.ResponseList;
import com.eeyuva.utils.preferences.PrefsManager;

import java.util.List;

/**
 * Created by hari on 14/09/16.
 */
public class DetailPresenterImpl implements DetailContract.Presenter {

    private PrefsManager mPrefsManager;
    private ApiInteractor mApiInteractor;
    private DetailContract.View mView;

    public DetailPresenterImpl(ApiInteractor apiInteractor, PrefsManager prefsManager) {
        this.mApiInteractor = apiInteractor;
        this.mPrefsManager = prefsManager;
    }

    @Override
    public void setView(BaseView view) {
        mView = (DetailContract.View) view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void getArticlesDetails(String mModuleId, String mArticleId) {
        mApiInteractor.getArticlesDetails(mView, "http://mobile.eeyuva.com/getarticleinfo.php?moduleid=" + mModuleId + "&arid=" + mArticleId, mArticleListener);

    }

    @Override
    public void getOtherArticlesDetails(String mModuleId, String mArticleId) {
        mApiInteractor.getOtherArticlesDetails(mView, "http://mobile.eeyuva.com/getrelatedarticles.php?articleid=" + mArticleId + "&modid=" + mModuleId, mOtherArticleListener);
    }

    @Override
    public List<ResponseList> getModules() {
        return mPrefsManager.getModules().getResponse();
    }

    @Override
    public void setLikeOrDislike(String article_id, String type, String module_id, String uid) {
        Log.i("api", "apilll" + "http://eeyuva.com/mlike_dislike/?article_id=" + article_id + "&type=" + type + "&module_id=" + module_id + "&uid=" + mPrefsManager.getUserDetails().getUserid());
        mApiInteractor.setLikeorDislike(mView, "http://eeyuva.com/mlike_dislike/?article_id=" + article_id + "&type=" + type + "&module_id=" + module_id + "&uid=" + mPrefsManager.getUserDetails().getUserid(), mDislikeListener);
    }

    @Override
    public void getViewComments(String mModuleId, String articleid) {
        mApiInteractor.getViewComments(mView, "http://mobile.eeyuva.com/fetchusercomments.php?modid=" + mModuleId + "&eid=" + articleid, mCommentListArticleListener);
    }

    @Override
    public void setPostComments(String trim, String mModuleId, String articleid) {
        mApiInteractor.getPostComments(mView, "http://mobile.eeyuva.com/postcomments.php?uid=" + mPrefsManager.getUserDetails().getUserid() + "&modid="
                + mModuleId + "&eid=" + articleid + "&msg=" + trim, mCommentPostListener);

    }

    @Override
    public void getArticlesDetails(String mArticleId) {
        mApiInteractor.getArticlesDetails(mView, "http://mobile.eeyuva.com/getmodulewiseusernewsdetails.php?arid=" + mArticleId, mArticleListener);

    }

    @Override
    public void getOtherArticlesDetails(String mModuleId, String mArticleId, String mEntityId) {
        mApiInteractor.getOtherArticlesDetails(mView, "http://mobile.eeyuva.com/getrelatedarticles.php?articleid=" + mArticleId + "&modid=" + mModuleId + "&ufl=" + mEntityId, mOtherArticleListener);
    }

    @Override
    public void getArticlesNewsDetails(String mArticleId) {
        mApiInteractor.getArticlesDetails(mView, "http://mobile.eeyuva.com/getusernewsdetails.php?uid=" + mPrefsManager.getUserDetails().getUserid() + "&arid=" + mArticleId, mArticleListener);

    }


    LoadListener<ArticleDetailResponse> mArticleListener = new LoadListener<ArticleDetailResponse>() {
        @Override
        public void onSuccess(ArticleDetailResponse responseBody) {
            if (responseBody.getResponse().size() != 0)
                mView.setArticleDetails(responseBody.getResponse().get(0));
        }

        @Override
        public void onFailure(Throwable t) {

        }

        @Override
        public void onError(Object error) {

        }
    };
    LoadListener<ArticleDetailResponse> mOtherArticleListener = new LoadListener<ArticleDetailResponse>() {
        @Override
        public void onSuccess(ArticleDetailResponse responseBody) {
            mView.setOtherArticleDetails(responseBody.getResponse());
        }

        @Override
        public void onFailure(Throwable t) {

        }

        @Override
        public void onError(Object error) {

        }
    };
    LoadListener<CommentListResponse> mCommentListArticleListener = new LoadListener<CommentListResponse>() {
        @Override
        public void onSuccess(CommentListResponse responseBody) {
            mView.setCommentsListToAdapter(responseBody.getResponse());
        }

        @Override
        public void onFailure(Throwable t) {

        }

        @Override
        public void onError(Object error) {

        }
    };

    LoadListener<CommentPostResponse> mCommentPostListener = new LoadListener<CommentPostResponse>() {
        @Override
        public void onSuccess(CommentPostResponse responseBody) {
            mView.showErrorDialog(responseBody.getStatusInfo());
        }

        @Override
        public void onFailure(Throwable t) {

        }

        @Override
        public void onError(Object error) {

        }
    };

    LoadListener<LikeDislikeResponse> mDislikeListener = new LoadListener<LikeDislikeResponse>() {
        @Override
        public void onSuccess(LikeDislikeResponse responseBody) {
            mView.showErrorDialog(responseBody.getStatusInfo());
        }

        @Override
        public void onFailure(Throwable t) {

        }

        @Override
        public void onError(Object error) {

        }
    };
}
