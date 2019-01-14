package com.sevensevenlife.model.httpmodel;

import java.io.Serializable;

public class headers implements Serializable {
    String code;
    String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
