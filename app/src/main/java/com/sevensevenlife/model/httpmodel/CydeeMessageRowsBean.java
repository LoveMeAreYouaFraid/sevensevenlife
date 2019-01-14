package com.sevensevenlife.model.httpmodel;

import java.io.Serializable;

public  class CydeeMessageRowsBean implements Serializable {
    /**
     * title : test222
     * create_time : 2017-04-12 16:07:53
     * url :
     */

    private String title;
    private String create_time;
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
