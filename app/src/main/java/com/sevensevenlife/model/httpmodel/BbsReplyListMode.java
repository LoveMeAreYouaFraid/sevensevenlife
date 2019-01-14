package com.sevensevenlife.model.httpmodel;

import java.util.List;

/**
 * Created by Administrator on 2017/8/11 0011.
 */

public class BbsReplyListMode extends PublicMode {

    private List<BbsReplyListRowsBean> rows;

    public List<BbsReplyListRowsBean> getRows() {
        return rows;
    }

    public void setRows(List<BbsReplyListRowsBean> rows) {
        this.rows = rows;
    }


}
