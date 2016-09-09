package com.eeyuva.screens.home;

import com.eeyuva.base.BasePresenter;
import com.eeyuva.base.BaseView;
import com.eeyuva.screens.authentication.LoginResponse;

import java.util.List;

/**
 * Created by hari on 05/09/16.
 */
public interface HomeContract {
    interface View extends BaseView {

        void setDataToAdapter(List<ResponseList> response);

        void setArticleAdapterNotify(List<ResponseItem> responseItem);
    }

    interface Presenter extends BasePresenter {

        void getHomeModule();

        void getArticles(String moduleid);

        List<ResponseList> getModules();

        List<ModuleList> getHotModules();

        LoginResponse getUserdetails();
    }
}
