package com.sevensevenlife.model.httpmodel;

import java.io.Serializable;

public  class resultsInfo implements Serializable{

    /**
     * vote_id :
     * user_id :
     * option_id : 1
     * option_name :  同意
     * voteNumber : 1
     */

    private String vote_id;
    private String user_id;
    private String option_id;
    private String option_name;
    private String voteNumber;

    public String getVote_id() {
        return vote_id;
    }

    public void setVote_id(String vote_id) {
        this.vote_id = vote_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getOption_id() {
        return option_id;
    }

    public void setOption_id(String option_id) {
        this.option_id = option_id;
    }

    public String getOption_name() {
        return option_name;
    }

    public void setOption_name(String option_name) {
        this.option_name = option_name;
    }

    public String getVoteNumber() {
        return voteNumber;
    }

    public void setVoteNumber(String voteNumber) {
        this.voteNumber = voteNumber;
    }
}
