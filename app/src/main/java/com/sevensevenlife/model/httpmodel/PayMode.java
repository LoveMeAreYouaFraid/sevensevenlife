package com.sevensevenlife.model.httpmodel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/4/12 0012.
 */

public class PayMode extends PublicMode {

    /**
     * rows : {"timestamp":"1491980481","sign":"51A90E170316BDF2955C209250AAB718","partnerid":"1456757902","noncestr":"2b346a0aa375a07f5a90a344a61416c4","prepayid":"wx2017041215012762948df9a40759608157","package":"Sign=WXPay","appid":"wx7f2af5ccf8ea9b4c"}
     */

    private RowsBean rows;

    public RowsBean getRows() {
        return rows;
    }

    public void setRows(RowsBean rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * timestamp : 1491980481
         * sign : 51A90E170316BDF2955C209250AAB718
         * partnerid : 1456757902
         * noncestr : 2b346a0aa375a07f5a90a344a61416c4
         * prepayid : wx2017041215012762948df9a40759608157
         * package : Sign=WXPay
         * appid : wx7f2af5ccf8ea9b4c
         */

        private String timestamp;
        private String sign;
        private String partnerid;
        private String noncestr;
        private String prepayid;
        @SerializedName("package")
        private String packageX;
        private String appid;

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }
    }
}
