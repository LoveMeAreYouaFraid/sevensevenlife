package com.sevensevenlife.model.httpmodel;

import java.io.Serializable;

public  class MessageListBen implements Serializable {
    /**
     * content : {"header":{"code":"002","message":"支付余款"},"rows":{"text":"服务已完成，请立即付款。","value":"503566297895"}}
     * id : 72196
     * ymd : 2017-03-08
     * hms : 08:22:01
     */

    private String content;
    private int id;
    private String ymd;
    private String hms;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getYmd() {
        return ymd;
    }

    public void setYmd(String ymd) {
        this.ymd = ymd;
    }

    public String getHms() {
        return hms;
    }

    public void setHms(String hms) {
        this.hms = hms;
    }
}
