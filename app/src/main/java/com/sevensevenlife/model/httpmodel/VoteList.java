package com.sevensevenlife.model.httpmodel;

import java.util.List;

/**
 * Created by Administrator on 2017/6/20 0020.
 */

public class VoteList extends PublicMode {

    private List<VoteInfo> rows;

    public List<VoteInfo> getRows() {
        return rows;
    }

    public void setRows(List<VoteInfo> rows) {
        this.rows = rows;
    }


}
