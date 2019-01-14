package com.sevensevenlife.model.httpmodel;

/**
 * Created by Administrator on 2017/6/20 0020.
 */

public class VoteDetail extends PublicMode {


    /**
     * rows : {"id":1,"title":"关于小区内露天泳池要不要加装护栏的问题，请业主们投票","description":"加吧，影响美观；不加吧，有安全隐患；请投票决定","option_type":1,"end_time":"2017-06-30 16:06:55","status":1,"options":[{"id":1,"option_name":" 同意"}],"myChoice":[],"results":""}
     */

    private VoteDetailItem rows;

    public VoteDetailItem getRows() {
        return rows;
    }

    public void setRows(VoteDetailItem rows) {
        this.rows = rows;
    }


}
