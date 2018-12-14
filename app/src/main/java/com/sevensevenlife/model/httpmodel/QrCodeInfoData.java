package com.sevensevenlife.model.httpmodel;

import java.io.Serializable;

public  class QrCodeInfoData implements Serializable {
    String clientImage;
    String serviceImage;

    public String getClientImage() {
        return clientImage;
    }

    public void setClientImage(String clientImage) {
        this.clientImage = clientImage;
    }

    public String getServiceImage() {
        return serviceImage;
    }

    public void setServiceImage(String serviceImage) {
        this.serviceImage = serviceImage;
    }
}
