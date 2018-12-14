package com.sevensevenlife.model.httpmodel;

import java.util.List;

/**
 * Created by Administrator on 2017/6/19 0019.
 */

public class AllCommunityInfoList extends PublicMode {
    List<CommunityInfo> rows;

    public List<CommunityInfo> getRows() {
        return rows;
    }

    public void setRows(List<CommunityInfo> rows) {
        this.rows = rows;
    }
}

