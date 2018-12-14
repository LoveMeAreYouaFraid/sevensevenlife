package com.sevensevenlife.model.httpmodel;

import java.io.Serializable;

public  class AddMessageRows implements Serializable {
    /**
     * id : 1
     * user_id :
     * title : 优享生活优惠券
     * content : 免费体验一次（内部测试）
     * image : https://p1.meituan.net/deal/5ff2613a2b140a8d7d62e4bf102ca9bb605889.jpg
     * url : http://218.75.134.187:7777/yxsh-api/html/coupon.html?id=1&stationId=1&sessionId=A1DA2A20ECAF408AC93588600B379C75
     * station_id :
     */

    private int id;
    private String user_id;
    private String title;
    private String content;
    private String image;
    private String url;
    private String station_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStation_id() {
        return station_id;
    }

    public void setStation_id(String station_id) {
        this.station_id = station_id;
    }
}
