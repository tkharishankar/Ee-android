package com.eeyuva.interactor;


import com.eeyuva.base.BaseView;
import com.eeyuva.base.LoadListener;
import com.eeyuva.screens.authentication.LoginContract;
import com.eeyuva.screens.authentication.LoginResponse;
import com.eeyuva.screens.registration.RegistrationContract;
import com.eeyuva.screens.registration.RegistrationResponse;

/**
 * Created by hari on 22/6/16.
 */
public interface DriverInteractor {
    void getLoginResponse(BaseView mView, String name, String pass, LoadListener<LoginResponse> mLoginListener);

    void getRegistrationResponse(BaseView mView, String firstName, String lastName, String email, String password, String confirmPassword, LoadListener<RegistrationResponse> mRegisterListener);


//    void getToken(BaseView view, Login loginBody, LoadListener<Token> tokenLoadListener);
//
//    void getAppConfig(BaseView mView, LoadListener<AppConfig> appConfigLoadListener, String channel);
//
//    void uploadImgToS3(BaseView mView, LoadListener<PodResponse> uploadListener, String driver, String driver_id, String awbNo, MultipartBody.Part img);
//
//    void updateOrderStatus(BaseView mView, LoadListener<OrderStatus> updateListener, String status, OrderStatusUpdate statusBody);
//
//    void updateDriverApproaching(BaseView mView, LoadListener<OrderStatus> updateListener, OrderStatusUpdate statusBody);
//
//    void getOrderList(BaseView mView, LoadListener<OrderListResponse> mListLoadListner, String orderType, boolean state);
//
//    void getTodaysOrderList(BaseView mView, LoadListener<OrderListResponse> mListLoadListner, String orderType, String liveStatus, boolean state);
//
//    void getPickSheet(BaseView mView, LoadListener<PicksheetsResponse> loadListener, boolean state);
//
//    void updatePicksheetItemPicked(BaseView mView, LoadListener<PickupResponse> loadListener, String psid, PickSheetPickup sheetPickup);
//
//    void getReasonList(BaseView mView, LoadListener<Reasons> reasonsLoadListener);
//
//    void updateCustomerProofs(BaseView mView, LoadListener<KYCResponse> kycResponseLoadListener, PODRequest request);
//
//    void updateFCmToken(LoadListener<FCMRegisterResponse> fcmRegisterResponseLoadListener, FCMRegisterRequest fcmRegisterRequest);
//
//    void removeFCmToken(BaseView mView,LoadListener<FCMRegisterResponse> fcmRegisterResponseLoadListener);
//
//    void uploadRTCPRoofs(BaseView mView, LoadListener<KYCResponse> kycResponseLoadListener, RTCProofRequest RTCProofRequest);
//
//    void uploadBPUProofs(BaseView mView, LoadListener<KYCResponse> kycResponseLoadListener, BPUProofRequest rtdProofRequest);
}

