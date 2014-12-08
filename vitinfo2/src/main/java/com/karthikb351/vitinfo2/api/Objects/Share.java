package com.karthikb351.vitinfo2.api.Objects;


import com.google.gson.annotations.Expose;
public class Share {

    @Expose
    private String issued;
    @Expose
    private String token;
    @Expose
    private Integer validity;

    /**
     * 
     * @return
     *     The issued
     */
    public String getIssued() {
        return issued;
    }

    /**
     * 
     * @param issued
     *     The issued
     */
    public void setIssued(String issued) {
        this.issued = issued;
    }

    /**
     * 
     * @return
     *     The token
     */
    public String getToken() {
        return token;
    }

    /**
     * 
     * @param token
     *     The token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * 
     * @return
     *     The validity
     */
    public Integer getValidity() {
        return validity;
    }

    /**
     * 
     * @param validity
     *     The validity
     */
    public void setValidity(Integer validity) {
        this.validity = validity;
    }

}
