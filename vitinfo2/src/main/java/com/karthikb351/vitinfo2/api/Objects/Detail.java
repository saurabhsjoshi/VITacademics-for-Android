package com.karthikb351.vitinfo2.api.Objects;


import com.google.gson.annotations.Expose;


public class Detail {

    @Expose
    private String date;
    @Expose
    private String status;
    @Expose
    private String reason;
    @Expose
    private String sl;

    /**
     * 
     * @return
     *     The date
     */
    public String getDate() {
        return date;
    }

    /**
     * 
     * @param date
     *     The date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * 
     * @return
     *     The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 
     * @return
     *     The reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * 
     * @param reason
     *     The reason
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * 
     * @return
     *     The sl
     */
    public String getSl() {
        return sl;
    }

    /**
     * 
     * @param sl
     *     The sl
     */
    public void setSl(String sl) {
        this.sl = sl;
    }

}
