package com.sevensevenlife.model.httpmodel;

import java.io.Serializable;
import java.util.List;

public  class VoteBean implements Serializable{
    /**
     * id : 15
     * community_id :
     * title : 测试标题
     * description : 测试内容
     * option_type : 1
     * end_time : 2017-08-10 08:00:00
     * status : 1
     * options : [{"id":33,"vote_id":"","option_name":"同意"},{"id":34,"vote_id":"","option_name":"不同意"}]
     * myChoice : []
     * results : []
     */

    private int id;
    private String community_id;
    private String title;
    private String description;
    private int option_type;
    private String end_time;
    private int status;
    private List<OptionsBean> options;
    private List<?> myChoice;
    private List<?> results;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCommunity_id() {
        return community_id;
    }

    public void setCommunity_id(String community_id) {
        this.community_id = community_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOption_type() {
        return option_type;
    }

    public void setOption_type(int option_type) {
        this.option_type = option_type;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<OptionsBean> getOptions() {
        return options;
    }

    public void setOptions(List<OptionsBean> options) {
        this.options = options;
    }

    public List<?> getMyChoice() {
        return myChoice;
    }

    public void setMyChoice(List<?> myChoice) {
        this.myChoice = myChoice;
    }

    public List<?> getResults() {
        return results;
    }

    public void setResults(List<?> results) {
        this.results = results;
    }

    public static class OptionsBean {
        /**
         * id : 33
         * vote_id :
         * option_name : 同意
         */

        private int id;
        private String vote_id;
        private String option_name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getVote_id() {
            return vote_id;
        }

        public void setVote_id(String vote_id) {
            this.vote_id = vote_id;
        }

        public String getOption_name() {
            return option_name;
        }

        public void setOption_name(String option_name) {
            this.option_name = option_name;
        }
    }
}
