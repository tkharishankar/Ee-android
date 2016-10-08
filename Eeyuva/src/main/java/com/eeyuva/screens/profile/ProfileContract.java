package com.eeyuva.screens.profile;

import com.eeyuva.base.BasePresenter;
import com.eeyuva.base.BaseView;
import com.eeyuva.screens.home.ResponseList;
import com.eeyuva.screens.profile.model.AlertList;
import com.eeyuva.screens.profile.model.ProfileResponse;

import java.util.List;

/**
 * Created by hari on 01/10/16.
 */

public interface ProfileContract {
    interface View extends BaseView {
        void setImage(String url);

        void setUserDetails(ProfileResponse responseBody);

        void setAdapter(List<AlertList> alertList);
    }

    interface Presenter extends BasePresenter {
        List<ResponseList> getModules();

        void getProfile();

        void setUpdateProfile(String fname, String lastname, String mobile, String gender, String dob, String about);

        void getUserAlerts();
    }

    interface AdapterCallBack {

    }
}
