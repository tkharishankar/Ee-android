package com.eeyuva.screens.gridpages;

import com.eeyuva.base.BasePresenter;
import com.eeyuva.base.BaseView;
import com.eeyuva.screens.gridpages.model.PhotoGalleryList;
import com.eeyuva.screens.gridpages.model.PhotoGalleryResponse;
import com.eeyuva.screens.gridpages.model.PhotoList;
import com.eeyuva.screens.gridpages.model.PhotoListResponse;
import com.eeyuva.screens.home.ResponseList;

import java.util.List;

/**
 * Created by hari on 17/09/16.
 */
public interface GridContract {

    interface View extends BaseView {

        void setAdapter(PhotoListResponse responseBody);

        void moveToGalleryView();
    }

    interface Presenter extends BasePresenter {
        List<ResponseList> getModules();

        void getPhotoList(String mModuleId);

        void getPhotoGalleryList(String trid);

        List<PhotoGalleryList> getPhotoGallery();
    }

    interface AdapterCallBack {
        void setSelectItem(ResponseList rl);

        void setSelectItem(PhotoList rl);
    }
}
