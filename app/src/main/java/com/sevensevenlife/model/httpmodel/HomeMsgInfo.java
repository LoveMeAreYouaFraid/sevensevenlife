package com.sevensevenlife.model.httpmodel;

import java.io.Serializable;

public class HomeMsgInfo implements Serializable {

    String id;
    String title;
    String content;
    String publish_by;
    String publish_time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getPublish_by() {
        return publish_by;
    }

    public void setPublish_by(String publish_by) {
        this.publish_by = publish_by;
    }

    public String getPublish_time() {
        return publish_time;
    }

    public void setPublish_time(String publish_time) {
        this.publish_time = publish_time;
    }
}
