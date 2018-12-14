package com.sevensevenlife.model.httpmodel;

import java.io.Serializable;

public class OptionsItem implements Serializable {
    /**
     * id : 1
     * option_name :  同意
     */

    private int id;
    private String option_name;
    private String voteNumber;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOption_name() {
        return option_name;
    }

    public String getVoteNumber() {
        return voteNumber;
    }

    public void setVoteNumber(String voteNumber) {
        this.voteNumber = voteNumber;
    }

    public void setOption_name(String option_name) {
        this.option_name = option_name;
    }
}
