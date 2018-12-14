package com.sevensevenlife.model.httpmodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/3/16 0016.
 */

public class CommentListMode extends PublicMode {


    private List<RowsBean> rows;

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * id : 2
         * serviceId : 1
         * custId : 35
         * custPic : http
         * custName : 刘德华
         * orderId : 1
         * attitude : 3
         * profession : 3
         * punctual : 3
         * total : 3
         * content : hen hao bucuo
         * isAnonymity : 1
         * createDate : 2016-07-15 08:40:52.0
         * replyContent : 感谢
         * replyTime : 2016-07-29 11:11:31.0
         */

        private String id;
        private String serviceId;
        private String custId;
        private String custPic;
        private String custName;
        private String orderId;
        private String attitude;
        private String profession;
        private String punctual;
        @SerializedName("total")
        private String totalX;
        private String content;
        private String isAnonymity;
        private String createDate;
        private String replyContent;
        private String replyTime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getServiceId() {
            return serviceId;
        }

        public void setServiceId(String serviceId) {
            this.serviceId = serviceId;
        }

        public String getCustId() {
            return custId;
        }

        public void setCustId(String custId) {
            this.custId = custId;
        }

        public String getCustPic() {
            return custPic;
        }

        public void setCustPic(String custPic) {
            this.custPic = custPic;
        }

        public String getCustName() {
            return custName;
        }

        public void setCustName(String custName) {
            this.custName = custName;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getAttitude() {
            return attitude;
        }

        public void setAttitude(String attitude) {
            this.attitude = attitude;
        }

        public String getProfession() {
            return profession;
        }

        public void setProfession(String profession) {
            this.profession = profession;
        }

        public String getPunctual() {
            return punctual;
        }

        public void setPunctual(String punctual) {
            this.punctual = punctual;
        }

        public String getTotalX() {
            return totalX;
        }

        public void setTotalX(String totalX) {
            this.totalX = totalX;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getIsAnonymity() {
            return isAnonymity;
        }

        public void setIsAnonymity(String isAnonymity) {
            this.isAnonymity = isAnonymity;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getReplyContent() {
            return replyContent;
        }

        public void setReplyContent(String replyContent) {
            this.replyContent = replyContent;
        }

        public String getReplyTime() {
            return replyTime;
        }

        public void setReplyTime(String replyTime) {
            this.replyTime = replyTime;
        }
    }
}
