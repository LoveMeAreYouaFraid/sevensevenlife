package com.sevensevenlife.model.httpmodel;

import java.util.List;

/**
 * Created by Administrator on 2017/6/19 0019.
 */

public class HomeMsgList extends PublicMode {
    List<HomeMsgInfo> rows;

    public List<HomeMsgInfo> getRows() {
        return rows;
    }

    public void setRows(List<HomeMsgInfo> rows) {
        this.rows = rows;
    }
}
