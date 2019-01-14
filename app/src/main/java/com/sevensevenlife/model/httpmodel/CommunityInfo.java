package com.sevensevenlife.model.httpmodel;

import java.io.Serializable;

public class CommunityInfo implements Serializable {
    String id;
    String community_name;
    String community_address;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCommunity_name() {
        return community_name;
    }

    public void setCommunity_name(String community_name) {
        this.community_name = community_name;
    }

    public String getCommunity_address() {
        return community_address;
    }

    public void setCommunity_address(String community_address) {
        this.community_address = community_address;
    }
}
