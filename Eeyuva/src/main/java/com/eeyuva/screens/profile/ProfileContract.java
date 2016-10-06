package com.eeyuva.screens.profile;

import com.eeyuva.base.BasePresenter;
import com.eeyuva.base.BaseView;
import com.eeyuva.screens.home.ResponseList;

import java.util.List;

/**
 * Created by hari on 01/10/16.
 */

public interface ProfileContract {
    interface View extends BaseView {

    }

    public interface Presenter extends BasePresenter {
        List<ResponseList> getModules();
    }
}
