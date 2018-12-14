package com.sevensevenlife.model.httpmodel;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/12/30 0030.
 */

public class Balance extends PublicMode {
    mdata rows;

    public mdata getRows() {
        return rows;
    }

    public void setRows(mdata rows) {
        this.rows = rows;
    }

    public class mdata implements Serializable {
        String mobile;
        String balance;
        String deposit;

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getDeposit() {
            return deposit;
        }

        public void setDeposit(String deposit) {
            this.deposit = deposit;
        }
    }
}
