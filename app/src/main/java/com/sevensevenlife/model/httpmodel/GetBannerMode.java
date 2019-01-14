package com.sevensevenlife.model.httpmodel;

import java.util.List;

/**
 * Created by Administrator on 2017/1/5 0005.
 */

public class GetBannerMode extends PublicMode {
    List<BannerInfo> rows;

    public List<BannerInfo> getRows() {
        return rows;
    }

    public void setRows(List<BannerInfo> rows) {
        this.rows = rows;
    }

}
