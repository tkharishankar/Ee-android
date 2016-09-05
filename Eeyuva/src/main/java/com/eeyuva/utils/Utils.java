package com.eeyuva.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.gson.Gson;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by kavi on 19/07/16.
 */
public class Utils {

    public static String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z", Locale.US);
        String d = sdf.format(new Date());
        return d;
    }


    public static void printJson(Object o) {
        try {
            String str = ((new Gson()).toJson(o));
            Log.i("Request", "Request---- > " + str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static void hideSoftKeyBoard(Activity a) {
        View view = a.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)
                    a.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void startCallIntent(FragmentActivity activity, String phoneNo) {
        try {
            phoneNo = "+" + formatPhoneNo(phoneNo);
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + phoneNo));
            activity.startActivity(callIntent);
        } catch (Exception ex) {
            Log.i("log", "log" + ex.toString());
        }
    }

    public static String formatPhoneNo(String phoneNumber) throws NumberParseException {

        return formatPhoneNo(getCountryISO(phoneNumber), phoneNumber);
    }

    public static String getCountryISO(String phoneNumber) {
        try {
            if (!phoneNumber.contains("+"))
                phoneNumber = "+" + phoneNumber;

            PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
            // phone must begin with '+'
            Phonenumber.PhoneNumber numberProto = phoneUtil.parse(phoneNumber, "");
            String countryISO = phoneUtil.getRegionCodeForNumber(numberProto);
            return countryISO;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String formatPhoneNo(String countryISO, String phoneNumber) {
        try {
            String mCountryID = countryISO.toUpperCase();

            if (mCountryID == null || mCountryID.equals(""))
                return phoneNumber;

            PhoneNumberUtil p = PhoneNumberUtil.getInstance();
            Phonenumber.PhoneNumber pp;
            pp = p.parse(phoneNumber, mCountryID);
            Log.i("phone", "contact countryISO" + "" + pp.getCountryCode());
            Log.i("phone", "contact countryISO" + "" + pp.getNationalNumber());
            phoneNumber = "" + pp.getCountryCode() + pp.getNationalNumber();


        } catch (Exception e) {
            e.printStackTrace();
        }

        return phoneNumber;
    }
}
