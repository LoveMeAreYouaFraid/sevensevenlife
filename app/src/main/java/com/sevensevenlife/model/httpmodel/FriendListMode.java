package com.sevensevenlife.model.httpmodel;

import java.util.List;

/**
 * Created by Administrator on 2017/3/14 0014.
 */

public class FriendListMode extends PublicMode {

    private List<FriendListRows> rows;

    public List<FriendListRows> getRows() {
        return rows;
    }

    public void setRows(List<FriendListRows> rows) {
        this.rows = rows;
    }


}
