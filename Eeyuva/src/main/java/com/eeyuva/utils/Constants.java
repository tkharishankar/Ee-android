package com.eeyuva.utils;

import com.eeyuva.BuildConfig;

/**
 * Created by hari on 22/6/16.
 */
public class Constants {
    public static final String BASE_URL = BuildConfig.BASE_URL;
    public static final String AUTHORIZATION = "Authorization";

    public static String RTD_ORDERS = "RTD";// forward logistic -pick from warehouse to customer delivery.-Ready to dispatch
    public static String DSP_ORDERS = "DSP";// forward logistic -pick from warehouse to customer delivery.-dispatched.
    public static String RTC_ORDERS = "RTC";// reverse logistic -pick from warehouse to client return.
    public static String RQST_ORDERS = "RQST";// reverse logistic -pick from customer to warehouse.

    public static String BPU = "BPU";// forward logistic -pick from client.
    public static String BCD = "BCD";// forward logistic -pick from warehouse to customer delivery.
    public static String BCR = "BCR";// reverse logistic -pick from warehouse to client return.
    public static String BCP = "BCP";// reverse logistic -pick from customer to warehouse.
    public static String LIV = "LIV";

    public static final int REQUEST_CAPTURE_PHOTO = 5;
    public static final int REQUEST_GALLERY_PHOTO = 6;

    public static final int REQUEST_CODE_CLOSE = 100;


    public static enum UPDATE_FLAGS {
        NO_UPDATE(1),
        MANDATORY(2),
        OPTIONAL(3),
        MAINTENANCE(4);

        private final int value;

        UPDATE_FLAGS(final int newValue) {
            value = newValue;
        }

        public int getValue() {
            return value;
        }
    }


    public static enum GET_IMAGE {
        PROOF_1(0),
        PROOF_2(1),
        SIGNATURE(2),
        NAME(3);

        private final int value;

        GET_IMAGE(final int newValue) {
            value = newValue;
        }

        public int getValue() {
            return value;
        }
    }


    public static enum UPDATE_TYPE {
        POPUP(1),
        WEBVIEW(2);

        private final int value;

        UPDATE_TYPE(final int newValue) {
            value = newValue;
        }

        public int getValue() {
            return value;
        }
    }

    public static enum ORDER_TYPE_ENUM {
        BPU(0),
        RTC(1);

        private final int value;

        ORDER_TYPE_ENUM(final int newValue) {
            value = newValue;
        }

        public int getValue() {
            return value;
        }
    }

    public static String BUNDLE = "bunlde";
    public static String MESSAGE_DATA = "message";
    public static String MESSAGE_TAG = "tag";
    public static String MESSAGE_PICK_SHEET_TAG = "picksheet";
    public static String MESSAGE_DELIVERY_TAG = "delivery";
    public static String MESSAGE_CANCELLED_TAG = "cancelled";
    public static String UPDATE_URL = "update_msg_url";
    public static String UPDATE_FLAG = "update_flag";
    public static String PROOF_1 = "proof1";
    public static String PROOF_2 = "proof2";
    public static String NAME = "name";
    public static String SIGNATURE = "signature";
    public static String AWB = "awb";
    public static String AMOUNT = "amount";
    public static String INSTRUCTIONS = "instructions";
    public static String ORDER_TYPE = "orderType";
    public static String ORDER_NUMBER = "orderNumber";

    //from
    public static int FOR_BPU_PICKUP = 111;
    public static int FOR_EMER_PICKUP = 112;

    /* --- Order Status -- */

    public static String STATUS_DISPATCH = "dispatch";
    public static String STATUS_PICK = "pick";
    public static String STATUS_NOT_DELIVERED = "not-deliver";
    public static String STATUS_DELIVERED = "deliver";
    public static String STATUS_NOT_PICKED = "not-pick";
    public static String STATUS_RETURN_TO_CLIENT = "return-to-client";

    public static String REM_CONFIG_DELIVERY_WARING = "delivery_error_fine_amt";

    public static final String DISPLAY_MESSAGE_ACTION = "com.fetchr.delivrEx.fcm.DISPLAY_MESSAGE";

}
