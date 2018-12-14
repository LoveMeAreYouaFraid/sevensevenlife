package com.sevensevenlife.model.httpmodel;

import java.io.Serializable;

/**
 *
 * 公共自行车 验证tokon
 * Created by Administrator on 2016/12/29 0029.
 */

public class TokonMode implements Serializable {
    String state;
    String Message;
    String Message1;
    boolean isTrue;

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

    public String getMessage1() {
        return Message1;
    }

    public void setMessage1(String message1) {
        Message1 = message1;
    }

    public boolean isTrue() {
        return isTrue;
    }

    public void setTrue(boolean aTrue) {
        isTrue = aTrue;
    }
}
