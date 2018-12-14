package com.sevensevenlife.model.httpmodel;

import java.util.List;

/**
 * Created by Administrator on 2017/1/10 0010.
 */

public class OrderListMode extends PublicMode {

    List<OrderListItemMode> rows;

    public List<OrderListItemMode> getRows() {
        return rows;
    }

    public void setRows(List<OrderListItemMode> rows) {
        this.rows = rows;
    }
}
