package com.eeyuva.interactor;



import com.eeyuva.apiservice.Api;
import com.eeyuva.base.BaseView;
import com.eeyuva.base.LoadListener;
import com.eeyuva.base.UiCallback;
import com.eeyuva.screens.authentication.LoginResponse;
import com.eeyuva.utils.preferences.PrefsManager;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;


/**
 * Created by hari on 22/6/16.
 */
public class DriverInteractorImpl implements DriverInteractor {

    PrefsManager mPrefsManager;
    Api mApi;

    public DriverInteractorImpl(Api remoteService, PrefsManager mPrefsManager) {
        this.mApi = remoteService;
        this.mPrefsManager = mPrefsManager;
    }

    @Override
    public void getLoginResponse(BaseView mView, String name, String pass, LoadListener<LoginResponse> mLoginListener) {
                UiCallback<LoginResponse> appConfigUiCallback = new UiCallback(mView, mLoginListener,true);
        Call<LoginResponse> appConfigCall = mApi.getAuthentication(name,pass);
        appConfigUiCallback.start(appConfigCall);
    }

//    @Override
//    public void getAppConfig(BaseView baseView, LoadListener<AppConfig> appConfigLoadListener, String channel) {
//        UiCallback<AppConfig> appConfigUiCallback = new UiCallback(baseView, appConfigLoadListener,false);
//        Call<com.fetchr.delivrex.models.response.config.AppConfig> appConfigCall = mApi.getAppConfig(BuildConfig.MW_BASE_URL + "force_update/update/", channel, "oauth " + BuildConfig.clientQAAuthorizationToken);
//        appConfigUiCallback.start(appConfigCall);
//    }
//
//    @Override
//    public void uploadImgToS3(BaseView mView, LoadListener<PodResponse> uploadListener, String driver, String driver_id, String awbNo, MultipartBody.Part img) {
//        UiCallback<PodResponse> s3UploadCallback = new UiCallback(mView, uploadListener, true);
//        Call<PodResponse> s3UploadCall = mApi.upload(BuildConfig.S3_URL, RequestBody.create(MediaType.parse("text/plain"),
//                driver), RequestBody.create(MediaType.parse("text/plain"),
//                driver_id), RequestBody.create(MediaType.parse("text/plain"),
//                awbNo), img);
//        s3UploadCallback.start(s3UploadCall);
//    }
//
//    @Override
//    public void updateOrderStatus(BaseView mView, LoadListener<OrderStatus> updateListener,String status, OrderStatusUpdate updateBody) {
//        UiCallback<OrderStatus> updateCallback = new UiCallback(mView, updateListener, true);
//        Call<OrderStatus> updateOrderStatus = mApi.updateOrderStatus(BuildConfig.BASE_URL+BuildConfig.PORT_8001+"api/"+status+"/",updateBody );
//        updateCallback.start(updateOrderStatus);
//    }
//
//    @Override
//    public void updateDriverApproaching(BaseView mView, LoadListener<OrderStatus> updateListener, OrderStatusUpdate statusBody) {
//        UiCallback<OrderStatus> updateCallback = new UiCallback(mView, updateListener, true);
//        Call<OrderStatus> updateOrderStatus = mApi.updateDriverApproaching(BuildConfig.BASE_URL+BuildConfig.PORT_8001+"api/notify/",statusBody );
//        updateCallback.start(updateOrderStatus);
//    }
//
//    @Override
//    public void getToken(BaseView baseView, Login loginBody, LoadListener<Token> tokenLoadListener) {
//        mPrefsManager.setAccessToken("");
//        UiCallback<Token> tokenUiCallback = new UiCallback(baseView, tokenLoadListener, true);
//        Call<Token> tokenCall = mApi.sendLogin(loginBody);
//        tokenUiCallback.start(tokenCall);
//    }
//
//    @Override
//    public void getOrderList(BaseView mView, LoadListener<OrderListResponse> mListLoadListner, String orderType, boolean state) {
//        UiCallback<OrderListResponse> orderListResponseUiCallback = new UiCallback(mView, mListLoadListner, state);
//        Call<OrderListResponse> listResponseCall = mApi.getOrderListResponseCall(mPrefsManager.getDriverName(), orderType, true);
//        orderListResponseUiCallback.start(listResponseCall);
//    }
//
//    @Override
//    public void getTodaysOrderList(BaseView mView, LoadListener<OrderListResponse> mListLoadListner, String orderType, String liveStatus, boolean state) {
//        UiCallback<OrderListResponse> orderListResponseUiCallback = new UiCallback(mView, mListLoadListner, state);
//        Call<OrderListResponse> listResponseCall = mApi.getworkfordriverList(mPrefsManager.getDriverName(), orderType,liveStatus, true);
//        orderListResponseUiCallback.start(listResponseCall);
//    }
//
//    @Override
//    public void getPickSheet(BaseView mView, LoadListener<PicksheetsResponse> loadListener, boolean state) {
//        UiCallback<PicksheetsResponse> uiCallback = new UiCallback(mView, loadListener, state);
//        Call<PicksheetsResponse> call = mApi.getPickSheetList(mPrefsManager.getDriverName(),false);
//        uiCallback.start(call);
//    }
//
//    @Override
//    public void updatePicksheetItemPicked(BaseView mView, LoadListener<PickupResponse> loadListener, String psid, PickSheetPickup sheetPickup) {
//        UiCallback<PickupResponse> uiCallback = new UiCallback(mView, loadListener,true);
//        Call<PickupResponse> call = mApi.updatePicksheetItemPicked(psid,sheetPickup);
//        uiCallback.start(call);
//    }
//
//    @Override
//    public void getReasonList(BaseView mView, LoadListener<com.fetchr.delivrex.models.response.reasons.Reasons> reasonsLoadListener) {
//        UiCallback<com.fetchr.delivrex.models.response.reasons.Reasons> uiCallback = new UiCallback<>(mView,reasonsLoadListener, true);
//        Call<com.fetchr.delivrex.models.response.reasons.Reasons> call = mApi.getReasonList("DT");
//        uiCallback.start(call);
//    }
//
//    @Override
//    public void updateCustomerProofs(BaseView mView, LoadListener<com.fetchr.delivrex.models.response.updateproofs.KYCResponse> kycResponseLoadListener, PODRequest request) {
//        UiCallback<com.fetchr.delivrex.models.response.updateproofs.KYCResponse> uiCallback = new UiCallback<>(mView,kycResponseLoadListener, true);
//        Call<com.fetchr.delivrex.models.response.updateproofs.KYCResponse> call = mApi.updateCustomerProofs(request);
//        uiCallback.start(call);
//    }
//
//    @Override
//    public void updateFCmToken(LoadListener<FCMRegisterResponse> fcmRegisterResponseLoadListener, FCMRegisterRequest fcmRegisterRequest) {
//        UiCallback<FCMRegisterResponse> uiCallback = new UiCallback<>(null,fcmRegisterResponseLoadListener,false);
//        Call<FCMRegisterResponse> call = mApi.updateFCMToken(fcmRegisterRequest);
//        uiCallback.start(call);
//
//    }
//
//    @Override
//    public void removeFCmToken(BaseView mView,LoadListener<FCMRegisterResponse> fcmRegisterResponseLoadListener) {
//        UiCallback<FCMRegisterResponse> uiCallback = new UiCallback<>(mView,fcmRegisterResponseLoadListener,false);
//        Call<FCMRegisterResponse> call = mApi.removeFCMToken();
//        uiCallback.start(call);
//
//    }
//
//    @Override
//    public void uploadRTCPRoofs(BaseView mView, LoadListener<KYCResponse> rtcListener, RTCProofRequest RTCProofRequest) {
//        UiCallback<KYCResponse> uiCallback = new UiCallback<>(mView,rtcListener, true);
//        Call<KYCResponse> call = mApi.updateRTCCustomerProofs(RTCProofRequest);
//        uiCallback.start(call);
//    }
//
//    @Override
//    public void uploadBPUProofs(BaseView mView, LoadListener<KYCResponse> bpuListener, BPUProofRequest bpuProofRequest) {
//        UiCallback<KYCResponse> uiCallback = new UiCallback<>(mView,bpuListener, true);
//        Call<KYCResponse> call = mApi.updateBPUCustomerProofs(bpuProofRequest);
//        uiCallback.start(call);
//    }
}
