package com.sevensevenlife.model.httpmodel;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/12/30 0030.
 */

public class bikeListmode implements Serializable {
    String sitename;
    String bikesn;
    String bikestate;
    String losedate;

    public String getSitename() {
        return sitename;
    }

    public void setSitename(String sitename) {
        this.sitename = sitename;
    }

    public String getBikesn() {
        return bikesn;
    }

    public void setBikesn(String bikesn) {
        this.bikesn = bikesn;
    }

    public String getBikestate() {
        return bikestate;
    }

    public void setBikestate(String bikestate) {
        this.bikestate = bikestate;
    }

    public String getLosedate() {
        return losedate;
    }

    public void setLosedate(String losedate) {
        this.losedate = losedate;
    }
}