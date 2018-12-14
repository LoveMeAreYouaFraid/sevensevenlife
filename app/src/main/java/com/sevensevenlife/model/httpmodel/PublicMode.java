package com.sevensevenlife.model.httpmodel;


import java.io.Serializable;

/**
 * Created by 10237 on 2016/11/12.
 */

public class PublicMode implements Serializable {
    
     String total;

     headers header;

    public headers getHeader() {
        return header;
    }

    public void setHeader(headers header) {
        this.header = header;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }



}
