package com.example.farm.myfarms.Model;

/**
 * Created by cotam on 13.11.2018.
 */

public class Field {

    private String name;
    private String status;
    private String siew;
    private String imgURL;

    public Field(String name, String status, String siew, String imgURL) {
        this.name = name;
        this.status = status;
        this.siew = siew;
        this.imgURL = imgURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSiew() {
        return siew;
    }

    public void setSiew(String siew) {
        this.siew = siew;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }
}
