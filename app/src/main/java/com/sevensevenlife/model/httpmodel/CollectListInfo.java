package com.sevensevenlife.model.httpmodel;

import java.util.List;

/**
 * Created by Administrator on 2017/2/10 0010.
 */

public class CollectListInfo extends PublicMode {
    List<CollectInfo> rows;

    public List<CollectInfo> getRows() {
        return rows;
    }

    public void setRows(List<CollectInfo> rows) {
        this.rows = rows;
    }


}

