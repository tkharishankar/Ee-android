package com.eeyuva.screens.profile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

import com.eeyuva.base.BaseView;
import com.eeyuva.base.LoadListener;
import com.eeyuva.interactor.ApiInteractor;
import com.eeyuva.screens.DetailPage.model.CommentListResponse;
import com.eeyuva.screens.home.ResponseList;
import com.eeyuva.screens.profile.model.AlertResponse;
import com.eeyuva.screens.profile.model.CommentResponse;
import com.eeyuva.screens.profile.model.EditResponse;
import com.eeyuva.screens.profile.model.NewsResponse;
import com.eeyuva.screens.profile.model.NotificationResponse;
import com.eeyuva.screens.profile.model.ProfileResponse;
import com.eeyuva.screens.profile.userdetails.interactor.ImageProcessingListener;
import com.eeyuva.screens.profile.userdetails.interactor.PackageInfoInteractor;
import com.eeyuva.screens.profile.userdetails.interactor.PermissionGrantedListener;
import com.eeyuva.utils.Constants;
import com.eeyuva.utils.preferences.PrefsManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by hari on 01/10/16.
 */

public class ProfilePresenterImpl implements ProfileContract.Presenter {

    private ApiInteractor mApiInteractor;
    private PrefsManager mPrefsManager;
    private ProfileContract.View mView;
    private PackageInfoInteractor mPackageInfoInteractor;
    private Bitmap bitmapPhoto;


    public ProfilePresenterImpl(ApiInteractor apiInteractor, PrefsManager prefsManager, PackageInfoInteractor packageInfoInteractor) {
        this.mApiInteractor = apiInteractor;
        this.mPrefsManager = prefsManager;
        this.mPackageInfoInteractor = packageInfoInteractor;
    }

    @Override
    public void setView(BaseView view) {
        mView = (ProfileContract.View) view;
    }

    @Override
    public List<ResponseList> getModules() {
        return mPrefsManager.getModules().getResponse();
    }

    @Override
    public void getProfile() {
//        mApiInteractor.getProfile(mView, "http://mobile.eeyuva.com/getuserinfo.php?uid=" + mPrefsManager.getUserDetails().getUserid(), mProfileListener);
        mApiInteractor.getProfile(mView, "http://mobile.eeyuva.com/getuserinfo.php?uid=3939", mProfileListener);
    }

    @Override
    public void setUpdateProfile(String fname, String lastname, String mobile, String gender, String dob, String about) {

//        mApiInteractor.getEditProfile(mView, "http://mobile.eeyuva.com/edituserinfo.php?uid=" + mPrefsManager.getUserDetails().getUserid() +
        mApiInteractor.getEditProfile(mView, "http://mobile.eeyuva.com/edituserinfo.php?uid=3939"+
                "&fname=" + fname +
                "&lname=" + lastname +
                "&mob=" + mobile + "" +
                "&gen=" + gender +
                "&dob=" + dob +
                "&aboutme=" + about, mEditProfileListener);

    }

    @Override
    public void getUserAlerts() {
//        mApiInteractor.getUserAlerts(mView, "http://mobile.eeyuva.com/fetchalerts.php?uid=" + mPrefsManager.getUserDetails().getUserid(), mAlertListner);
        mApiInteractor.getUserAlerts(mView, "http://mobile.eeyuva.com/fetchalerts.php?uid=3939", mAlertListner);
    }

    @Override
    public void getComments() {
        mApiInteractor.getStuffComments(mView, "http://mobile.eeyuva.com/getusercommentsinfo.php?uid=3939", mCommentListener);

    }

    @Override
    public void getNews() {
//        mApiInteractor.getStuffNews(mView, "http://mobile.eeyuva.com/getusernews.php/?uid=" + mPrefsManager.getUserDetails().getUserid(), mNewsListener);
        mApiInteractor.getStuffNews(mView, "http://mobile.eeyuva.com/getusernews.php/?uid=3939", mNewsListener);

    }

    @Override
    public void getNotification() {
        mApiInteractor.getNotificationComments(mView, "http://mobile.eeyuva.com/fetchuserpush.php/?uid=3939", mNotificationListener);

    }

    LoadListener<ProfileResponse> mProfileListener = new LoadListener<ProfileResponse>() {
        @Override
        public void onSuccess(ProfileResponse responseBody) {
            mView.setImage(responseBody.getmProfileList().get(0).getProfilePic());
            mView.setUserDetails(responseBody);
        }

        @Override
        public void onFailure(Throwable t) {

        }

        @Override
        public void onError(Object error) {

        }
    };

    LoadListener<EditResponse> mEditProfileListener = new LoadListener<EditResponse>() {
        @Override
        public void onSuccess(EditResponse responseBody) {
            mView.showErrorDialog(responseBody.getSTATUSINFO());
        }

        @Override
        public void onFailure(Throwable t) {

        }

        @Override
        public void onError(Object error) {

        }
    };

    LoadListener<AlertResponse> mAlertListner = new LoadListener<AlertResponse>() {
        @Override
        public void onSuccess(AlertResponse responseBody) {
            mView.setAdapter(responseBody.getAlertList());
        }

        @Override
        public void onFailure(Throwable t) {

        }

        @Override
        public void onError(Object error) {

        }
    };
    LoadListener<CommentResponse> mCommentListener = new LoadListener<CommentResponse>() {
        @Override
        public void onSuccess(CommentResponse responseBody) {
            mView.setCommentAdapter(responseBody);
        }

        @Override
        public void onFailure(Throwable t) {

        }

        @Override
        public void onError(Object error) {

        }
    };
    LoadListener<NotificationResponse> mNotificationListener = new LoadListener<NotificationResponse>() {
        @Override
        public void onSuccess(NotificationResponse responseBody) {
            mView.setNotificationAdapter(responseBody);
        }

        @Override
        public void onFailure(Throwable t) {

        }

        @Override
        public void onError(Object error) {

        }
    };
    LoadListener<NewsResponse> mNewsListener = new LoadListener<NewsResponse>() {
        @Override
        public void onSuccess(NewsResponse responseBody) {
            mView.setNewsAdapter(responseBody);
        }

        @Override
        public void onFailure(Throwable t) {

        }

        @Override
        public void onError(Object error) {

        }
    };

    @Override
    public void snapPhotoClick() {
        if (mPackageInfoInteractor.checkForCameraPermission()) {
            capturePhotoFromCamera();
        } else {
            mPackageInfoInteractor.getPermissions(cameraPermissionGrantedListener);
        }
    }

    private void capturePhotoFromCamera() {
        mView.openActivityForResult(mPackageInfoInteractor.getPhotoCaptureIntent(),
                Constants.REQUEST_CAPTURE_PHOTO);
    }

    @Override
    public void pickFromGalleryClick() {
        if (mPackageInfoInteractor.checkForCameraPermission()) {
            selectPictureFromGallery();
        } else {
            mPackageInfoInteractor.getPermissions(galleryPermissionGrantedListener);
        }
    }

    private void selectPictureFromGallery() {
        mView.openActivityForResult(Intent.createChooser(mPackageInfoInteractor
                .getPhotoGalleryIntent(), "Choose photo"), Constants.REQUEST_GALLERY_PHOTO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("onActivityResult", "onActivityResult" + data.getData());
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Constants.REQUEST_GALLERY_PHOTO) {
                mPackageInfoInteractor.handleGalleryPhoto(data.getData(), imageProcessingListener);
            } else if (requestCode == Constants.REQUEST_CAPTURE_PHOTO) {
                mPackageInfoInteractor.handleCapturedPhoto(imageProcessingListener);
            } else if (requestCode == Constants.REQUEST_CODE_CLOSE) {
                closeActivityOnResult(data);
            }
        }
    }

    public String getBitmapImg() {
        // get the base 64 string
        String imgString = Base64.encodeToString(getBytesFromBitmap(bitmapPhoto),
                Base64.NO_WRAP);
        return imgString;
    }

    // convert from bitmap to byte array
    public byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        return stream.toByteArray();
    }

    @Override
    public void uploadImage(File photoFile) {
        InputStream inputStream = null;//You can get an inputStream using any IO API
        try {
            inputStream = new FileInputStream(photoFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        byte[] bytes;
        byte[] buffer = new byte[8192];
        int bytesRead;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        bytes = output.toByteArray();
        String encodedString = Base64.encodeToString(bytes, Base64.DEFAULT);

        mApiInteractor.uploadImage(mView, "http://mobile.eeyuva.com/editprofilepic.php", "3939", encodedString, mEditProfileListener);
    }

    @Override
    public void setChangePassword(String oldpass, String newpass, String conpass) {
        mApiInteractor.changePassword(mView, "http://eeyuva.com/user_mchangepassword/?oldpwd=" + oldpass + "&newpwd=" + newpass + "&cpwd=" + conpass + "&uid=3939", mEditProfileListener);

    }

    @Override
    public void uploadImageOrVideo(File photoFile, String modulename, String title, String desc) {
//

        InputStream inputStream = null;//You can get an inputStream using any IO API
        try {
            inputStream = new FileInputStream(photoFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        byte[] bytes;
        byte[] buffer = new byte[8192];
        int bytesRead;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        bytes = output.toByteArray();
        String encodedString = Base64.encodeToString(bytes, Base64.DEFAULT);

        mApiInteractor.uploadImageVideo(mView, "http://mobile.eeyuva.com/postusernews.php?mid=4&catid=Cat_6395ebd0f&title=" + title + "&desc=" + desc + "&uid=3939", encodedString, mEditProfileListener);
    }

    @Override
    public void getViewComments(String mModuleId, String articleid) {
        mApiInteractor.getViewComments(mView, "http://mobile.eeyuva.com/fetchusercomments.php?modid=" + mModuleId + "&eid=" + articleid, mCommentListArticleListener);
    }

    private void closeActivityOnResult(Intent data) {
        mView.setResultAndCloseActivity(data);
    }


    private ImageProcessingListener imageProcessingListener = new ImageProcessingListener() {
        @Override
        public void onProcessingStart() {
            mView.showProgress();
        }

        @Override
        public void onProcessingComplete() {
            mView.hideProgress();
            mView.setPhoto(mPackageInfoInteractor.getPhotoFile());

        }
    };


    private PermissionGrantedListener cameraPermissionGrantedListener =
            new PermissionGrantedListener() {
                @Override
                public void onPermissionDenied() {

                }

                @Override
                public void onPermissionGranted() {
                    capturePhotoFromCamera();
                }
            };

    private PermissionGrantedListener galleryPermissionGrantedListener =
            new PermissionGrantedListener() {
                @Override
                public void onPermissionDenied() {

                }

                @Override
                public void onPermissionGranted() {
                    selectPictureFromGallery();
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
}
