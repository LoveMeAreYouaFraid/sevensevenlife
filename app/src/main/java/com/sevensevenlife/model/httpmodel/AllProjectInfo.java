package com.sevensevenlife.model.httpmodel;

import java.util.List;

/**
 * Created by Administrator on 2017/2/9 0009.
 */

public class AllProjectInfo extends PublicMode {

    List<FirstLevelProjecMode> rows;

    public List<FirstLevelProjecMode> getRows() {
        return rows;
    }

    public void setRows(List<FirstLevelProjecMode> rows) {
        this.rows = rows;
    }
}
