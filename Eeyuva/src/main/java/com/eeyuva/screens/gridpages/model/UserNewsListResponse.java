package com.eeyuva.screens.gridpages.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hari on 18/09/16.
 */
public class UserNewsListResponse {


    @SerializedName("STATUS_CODE")
    @Expose
    private Integer statusCode;
    @SerializedName("STATUS_INFO")
    @Expose
    private String statusInfo;
    @SerializedName("RESPONSE")
    @Expose
    private List<UserNewsList> response = new ArrayList<UserNewsList>();

    /**
     * @return The statusCode
     */
    public Integer getStatusCode() {
        return statusCode;
    }

    /**
     * @param statusCode The status_code
     */
    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * @return The statusInfo
     */
    public String getStatusInfo() {
        return statusInfo;
    }

    /**
     * @param statusInfo The status_info
     */
    public void setStatusInfo(String statusInfo) {
        this.statusInfo = statusInfo;
    }

    /**
     * @return The response
     */
    public List<UserNewsList> getResponse() {
        return response;
    }

    /**
     * @param response The response
     */
    public void setResponse(List<UserNewsList> response) {
        this.response = response;
    }
}
