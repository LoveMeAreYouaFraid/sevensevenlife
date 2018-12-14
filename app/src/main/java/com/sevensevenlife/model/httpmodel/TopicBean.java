package com.sevensevenlife.model.httpmodel;

import java.io.Serializable;

public  class TopicBean implements Serializable {
    /**
     * id : 2
     * community_id :
     * vote_id : 15
     * user_id : 32
     * title : 测试标题
     * content : 测试内容
     * picture :
     * create_time : 2017-08-08 14:01:00
     * praise_count : 1
     * reply_count : 1
     * replyList :
     * vote :
     * nick_name : 156****7225
     * head_pic : http://111.8.133.24:7777/yxsh-api/image/default_user_head.png
     * isPraised : 1
     */

    private int id;
    private String community_id;
    private int vote_id;
    private int user_id;
    private String title;
    private String content;
    private String picture;
    private String create_time;
    private int praise_count;
    private int reply_count;
    private String replyList;
    private String vote;
    private String nick_name;
    private String head_pic;
    private int isPraised;

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

    public int getVote_id() {
        return vote_id;
    }

    public void setVote_id(int vote_id) {
        this.vote_id = vote_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getPraise_count() {
        return praise_count;
    }

    public void setPraise_count(int praise_count) {
        this.praise_count = praise_count;
    }

    public int getReply_count() {
        return reply_count;
    }

    public void setReply_count(int reply_count) {
        this.reply_count = reply_count;
    }

    public String getReplyList() {
        return replyList;
    }

    public void setReplyList(String replyList) {
        this.replyList = replyList;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getHead_pic() {
        return head_pic;
    }

    public void setHead_pic(String head_pic) {
        this.head_pic = head_pic;
    }

    public int getIsPraised() {
        return isPraised;
    }

    public void setIsPraised(int isPraised) {
        this.isPraised = isPraised;
    }
}
