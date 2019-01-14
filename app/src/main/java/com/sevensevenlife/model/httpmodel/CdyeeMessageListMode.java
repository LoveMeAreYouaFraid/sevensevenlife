package com.sevensevenlife.model.httpmodel;

import java.util.List;

/**
 * Created by Administrator on 2017/4/17 0017.
 */

public class CdyeeMessageListMode extends PublicMode {

    public List<CydeeMessageRowsBean> rows;

    public List<CydeeMessageRowsBean> getRows() {
        return rows;
    }

    public void setRows(List<CydeeMessageRowsBean> rows) {
        this.rows = rows;
    }


}
