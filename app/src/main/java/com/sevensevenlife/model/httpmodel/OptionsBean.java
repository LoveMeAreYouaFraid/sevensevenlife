package com.sevensevenlife.model.httpmodel;

import java.io.Serializable;

public class OptionsBean implements Serializable {
    /**
     * option_name : 同意
     */

    private String option_name;

    public String getOption_name() {
        return option_name;
    }

    public void setOption_name(String option_name) {
        this.option_name = option_name;
    }
}
