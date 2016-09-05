package com.eeyuva.base;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hari on 05/09/16.
 */
public class ApiError {
    @SerializedName("status_info")
    @Expose
    private String statusInfo;

    /**
     *
     * @return
     * The statusInfo
     */
    public String getStatusInfo() {
        return statusInfo;
    }

    /**
     *
     * @param statusInfo
     * The status_info
     */
    public void setStatusInfo(String statusInfo) {
        this.statusInfo = statusInfo;
    }


}
