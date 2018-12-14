package com.sevensevenlife.model.httpmodel;

import android.text.TextUtils;

import java.io.Serializable;

public class BbsReplyListRowsBean implements Serializable {
    /**
     * id : 1
     * topic_id :
     * user_id : 32
     * reply_message : 好，支持
     * create_time : 2017-08-08 15:06:35
     * praise_count : 1
     */

    private String id;
    private String topic_id;
    private String user_id;
    private String reply_message;
    private String create_time;
    private String praise_count;
    private String nick_name;
    private String head_pic;
    private String isPraised;

    public String getIsPraised() {

        if (TextUtils.isEmpty(isPraised)) {
            isPraised = "0";
        }
        return isPraised;
    }

    public void setIsPraised(String isPraised) {
        this.isPraised = isPraised;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(String topic_id) {
        this.topic_id = topic_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getReply_message() {
        return reply_message;
    }

    public void setReply_message(String reply_message) {
        this.reply_message = reply_message;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getPraise_count() {
        return praise_count;
    }

    public void setPraise_count(String praise_count) {
        this.praise_count = praise_count;
    }
}
