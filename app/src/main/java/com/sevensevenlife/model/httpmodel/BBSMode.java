package com.sevensevenlife.model.httpmodel;

import java.util.List;

/**
 * Created by Administrator on 2017/8/10 0010.
 */

public class BBSMode extends PublicMode {


    /**
     * rows : [{"id":2,"community_id":"","vote_id":15,"user_id":32,"title":"测试标题","content":"测试内容","create_time":"2017-08-08 14:01:00","praise_count":1,"replyList":"","vote":""},{"id":1,"community_id":"","vote_id":0,"user_id":32,"title":"测试标题","content":"测试内容","create_time":"2017-08-08 11:25:28","praise_count":0,"replyList":"","vote":""}]
     * total : 2
     */

    private List<BBSModeRowsBean> rows;

    public List<BBSModeRowsBean> getRows() {
        return rows;
    }

    public void setRows(List<BBSModeRowsBean> rows) {
        this.rows = rows;
    }


}
