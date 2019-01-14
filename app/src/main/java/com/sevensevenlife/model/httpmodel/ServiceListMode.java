package com.sevensevenlife.model.httpmodel;

import java.util.List;

/**
 * Created by Administrator on 2017/3/6 0006.
 */

public class ServiceListMode extends PublicMode {

    private List<ServiceListRows> rows;

    public List<ServiceListRows> getRows() {
        return rows;
    }

    public void setRows(List<ServiceListRows> rows) {
        this.rows = rows;
    }


}
