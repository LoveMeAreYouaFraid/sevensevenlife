package com.sevensevenlife.model.httpmodel;

import java.io.Serializable;

public class CdyeeNewsRows implements Serializable {
    /**
     * title : 林志玲漏背装
     * publish_time : 2017-04-12 15:37:33
     * image : http://img6.cache.netease.com/photo/0003/2017-04-12/CHQB1RQV00AJ0003.jpg
     * url : http://ent.163.com/photoview/00AJ0003/628857.html#p=CHQB1RQV00AJ0003&from=tj_review
     */

    private String title;
    private String publish_time;
    private String image;
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublish_time() {
        return publish_time;
    }

    public void setPublish_time(String publish_time) {
        this.publish_time = publish_time;
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
}
