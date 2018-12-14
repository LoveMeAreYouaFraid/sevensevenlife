package com.sevensevenlife.model.httpmodel;

import java.util.List;

/**
 * Created by Administrator on 2017/6/21 0021.
 */

public class SecurityList extends PublicMode {


    private List<SecurityInfo> rows;

    public List<SecurityInfo> getRows() {
        return rows;
    }

    public void setRows(List<SecurityInfo> rows) {
        this.rows = rows;
    }


}
