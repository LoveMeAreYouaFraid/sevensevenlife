package com.sevensevenlife.model.httpmodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/9 0009.
 */

public class TopIcMode implements Serializable {


    /**
     * community_id : 1
     * title : 测试标题
     * content : 测试内容
     * vote : {"option_type":1,"end_time":"2017-08-10","options":[{"option_name":"同意"},{"option_name":"不同意"}]}
     */


    private String community_id;
    private String title;
    private String content;
    private String picture;
    private VoteBean vote;

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public VoteBean getVote() {
        return vote;
    }

    public void setVote(VoteBean vote) {
        this.vote = vote;
    }

    public static class VoteBean {
        /**
         * option_type : 1
         * end_time : 2017-08-10
         * options : [{"option_name":"同意"},{"option_name":"不同意"}]
         */

        private String option_type;
        private String end_time;
        private List<OptionsBean> options = new ArrayList<>();

        public String getOption_type() {
            return option_type;
        }

        public void setOption_type(String option_type) {
            this.option_type = option_type;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public List<OptionsBean> getOptions() {
            if (options == null) {
                options = new ArrayList<>();
            }
            return options;
        }

        public void setOptions(List<OptionsBean> options) {
            this.options = options;
        }


    }
}
