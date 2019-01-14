package com.sevensevenlife.model.httpmodel;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/12/30 0030.
 */

public class CheckUserInfo implements Serializable {
    String state;
    String Message;
    boolean isTrue;
    String Message1;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public boolean isTrue() {
        return isTrue;
    }

    public void setTrue(boolean aTrue) {
        isTrue = aTrue;
    }

    public String getMessage1() {
        return Message1;
    }

    public void setMessage1(String message1) {
        Message1 = message1;
    }
}
