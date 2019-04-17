package com.example.henry.marathon.javabean;

import com.google.gson.annotations.SerializedName;

import java.math.BigInteger;

public class Obj {
    @SerializedName("obj")
    protected String name;
    protected Double latlngx;
    protected Double latlngy;
    protected String locationdesc;
    @SerializedName("telephone")
    protected String person_tel;
    @SerializedName("timedesc")
    protected BigInteger date;
    @SerializedName("objdesc")
    protected String describe;
    @SerializedName("realname")
    protected String person_name;
    @SerializedName("timestamp")
    protected int post_data;
    public int getPost_data() {
        return post_data;
    }
    public void setPost_data(int post_data) {
        this.post_data = post_data;
    }
    public Double getLatlngx() {
        return latlngx;
    }
    public void setLatlngx(Double latlngx) {
        this.latlngx = latlngx;
    }
    public Double getLatlngy() {
        return latlngy;
    }
    public void setLatlngy(Double latlngy) {
        this.latlngy = latlngy;
    }
    public String getPerson_name() {
        return person_name;
    }
    public void setPerson_name(String person_name) {
        this.person_name = person_name;
    }
    public String getPerson_tel() {
        return person_tel;
    }
    public void setPerson_tel(String person_tel) {
        this.person_tel = person_tel;
    }
    public BigInteger getDate() {
        return date;
    }
    public void setDate(BigInteger date) {
        this.date = date;
    }
    public String getDescribe() {
        return describe;
    }
    public void setDescribe(String describe) {
        this.describe = describe;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLocationdesc() {
        return locationdesc;
    }
    public void setLocationdesc(String locationdesc) {
        this.locationdesc = locationdesc;
    }
}
