package com.eeyuva.screens.home;

import com.eeyuva.base.BasePresenter;
import com.eeyuva.base.BaseView;
import com.eeyuva.screens.authentication.LoginResponse;
import com.eeyuva.screens.searchpage.model.SearchResponse;

import java.util.List;

/**
 * Created by hari on 05/09/16.
 */
public interface HomeContract {
    interface View extends BaseView {

        void setDataToAdapter(List<ResponseList> response);

        void setArticleAdapterNotify(List<ResponseItem> responseItem);

        void setLoadMoredata(GetArticleResponse responseBody);

        void setLoadMoredata(SearchResponse responseBody);
    }

    interface Presenter extends BasePresenter {

        void getHomeModule();

        void getArticles(String moduleid, int index, int i);

        List<ResponseList> getModules();

        List<ModuleList> getHotModules();

        LoginResponse getUserdetails();

        void getArticles(String moduleid);

        void getSearchResponse(String key);
    }

    interface AdapterCallBack
    {

        void onItemClick(String articleid);
    }
}
