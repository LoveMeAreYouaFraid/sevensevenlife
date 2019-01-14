package com.sevensevenlife.model.httpmodel;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/2/22 0022.
 */

public class UserCouponMode extends PublicMode {
    mrows rows;

    public mrows getRows() {
        return rows;
    }

    public void setRows(mrows rows) {
        this.rows = rows;
    }

    public class mrows implements Serializable {
        String couponMoney;
        String couponId;

        public String getCouponMoney() {
            return couponMoney;
        }

        public void setCouponMoney(String couponMoney) {
            this.couponMoney = couponMoney;
        }

        public String getCouponId() {
            return couponId;
        }

        public void setCouponId(String couponId) {
            this.couponId = couponId;
        }
    }
}
