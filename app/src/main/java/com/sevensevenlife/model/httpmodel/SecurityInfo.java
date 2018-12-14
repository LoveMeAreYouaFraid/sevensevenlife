package com.sevensevenlife.model.httpmodel;

import java.io.Serializable;

public  class SecurityInfo implements Serializable {
    /**
     * id : 1
     * name : 刘保全
     * head_pic : http://111.8.133.24:7777/yxsh-api/static/images/201611/e3df3991-1d7a-4ae6-8195-963dcc20ae03.jpg
     * phone : 15618881231
     */

    private String id;
    private String name;
    private String head_pic;
    private String phone;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHead_pic() {
        return head_pic;
    }

    public void setHead_pic(String head_pic) {
        this.head_pic = head_pic;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
