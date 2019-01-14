package com.sevensevenlife.model.httpmodel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/12/30 0030.
 */

public class LostBikeMode extends PublicMode {
    mData rows;

    public mData getRows() {
        return rows;
    }

    public void setRows(mData rows) {
        this.rows = rows;
    }

    public class mData implements Serializable {
        List<bikeListmode> bikeList;
        String pic;

        public List<bikeListmode> getBikeList() {
            return bikeList;
        }

        public void setBikeList(List<bikeListmode> bikeList) {
            this.bikeList = bikeList;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }
    }


}
