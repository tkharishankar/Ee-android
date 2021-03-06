package com.eeyuva.screens.profile.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hari on 08/10/16.
 */

public class EditResponse {

    @SerializedName("STATUS_CODE")
    private Integer sTATUSCODE;
    @SerializedName("STATUS_INFO")
    private String sTATUSINFO;

    public String getmResponse() {
        return mResponse;
    }

    public void setmResponse(String mResponse) {
        this.mResponse = mResponse;
    }

    @SerializedName("RESPONSE")
    private String mResponse;
    /**
     * @return The sTATUSCODE
     */
    public Integer getSTATUSCODE() {
        return sTATUSCODE;
    }

    /**
     * @param sTATUSCODE The STATUS_CODE
     */
    public void setSTATUSCODE(Integer sTATUSCODE) {
        this.sTATUSCODE = sTATUSCODE;
    }

    /**
     * @return The sTATUSINFO
     */
    public String getSTATUSINFO() {
        return sTATUSINFO;
    }

    /**
     * @param sTATUSINFO The STATUS_INFO
     */
    public void setSTATUSINFO(String sTATUSINFO) {
        this.sTATUSINFO = sTATUSINFO;
    }

}
