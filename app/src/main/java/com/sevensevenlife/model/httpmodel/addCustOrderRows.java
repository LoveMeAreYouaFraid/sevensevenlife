package com.sevensevenlife.model.httpmodel;

import java.io.Serializable;

public class addCustOrderRows implements Serializable {
    String orderNo;
    String projectName;
    String price;
    String orgi_price;
    String notifyUrl;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOrgi_price() {
        return orgi_price;
    }

    public void setOrgi_price(String orgi_price) {
        this.orgi_price = orgi_price;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }
}
