package com.sevensevenlife.model.httpmodel;

import java.io.Serializable;

public  class BbsDetailModeRowsBean implements Serializable {
    /**
     * topic : {"id":2,"community_id":"","vote_id":15,"user_id":32,"title":"测试标题","content":"测试内容","picture":"","create_time":"2017-08-08 14:01:00","praise_count":1,"reply_count":1,"replyList":"","vote":"","nick_name":"156****7225","head_pic":"http://111.8.133.24:7777/yxsh-api/image/default_user_head.png","isPraised":1}
     * vote : {"id":15,"community_id":"","title":"测试标题","description":"测试内容","option_type":1,"end_time":"2017-08-10 08:00:00","status":1,"options":[{"id":33,"vote_id":"","option_name":"同意"},{"id":34,"vote_id":"","option_name":"不同意"}],"myChoice":[],"results":[]}
     */

    private TopicBean topic;
    private VoteBean vote;

    public TopicBean getTopic() {
        return topic;
    }

    public void setTopic(TopicBean topic) {
        this.topic = topic;
    }

    public VoteBean getVote() {
        return vote;
    }

    public void setVote(VoteBean vote) {
        this.vote = vote;
    }


}
