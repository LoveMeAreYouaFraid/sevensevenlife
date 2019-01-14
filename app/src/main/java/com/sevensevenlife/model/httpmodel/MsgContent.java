package com.sevensevenlife.model.httpmodel;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/22 0022.
 */

public class MsgContent implements Serializable {


    /**
     * header : {"code":"002","message":"支付余款"}
     * rows : {"text":"服务已完成，请立即付款。","value":"503566297895"}
     */

    private HeaderBean header;
    private RowsBean rows;

    public HeaderBean getHeader() {
        return header;
    }

    public void setHeader(HeaderBean header) {
        this.header = header;
    }

    public RowsBean getRows() {
        return rows;
    }

    public void setRows(RowsBean rows) {
        this.rows = rows;
    }

    public static class HeaderBean {
        /**
         * code : 002
         * message : 支付余款
         */

        private String code;
        private String message;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public static class RowsBean {
        /**
         * text : 服务已完成，请立即付款。
         * value : 503566297895
         */

        private String text;
        private String value;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
