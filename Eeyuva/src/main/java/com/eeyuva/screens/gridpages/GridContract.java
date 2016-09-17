package com.eeyuva.screens.gridpages;

import com.eeyuva.base.BasePresenter;
import com.eeyuva.base.BaseView;
import com.eeyuva.screens.home.ResponseList;

import java.util.List;

/**
 * Created by hari on 17/09/16.
 */
public interface GridContract {

    interface View extends BaseView {

    }

    interface Presenter extends BasePresenter {
        List<ResponseList> getModules();
    }
}
