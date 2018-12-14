package com.sevensevenlife.model.httpmodel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/3/20 0020.
 */

public class MyCouponListMode {


    private List<RowsBean> rows;

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean implements Serializable {


        private int id;
        private String coupon_name;
        private String coupon_pic;
        private String coupon_desc;
        private String coupon_value;
        private String original_price;
        private String amount;
        private String start_time;
        private String end_time;
        private String instructions;
        private String tips;
        private String shop_name;
        private String shop_logo;
        private String coupon_code;
        private String station_id;
        private String is_used;

        public String getIs_used() {
            return is_used;
        }

        public void setIs_used(String is_used) {
            this.is_used = is_used;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCoupon_name() {
            return coupon_name;
        }

        public void setCoupon_name(String coupon_name) {
            this.coupon_name = coupon_name;
        }

        public String getCoupon_pic() {
            return coupon_pic;
        }

        public void setCoupon_pic(String coupon_pic) {
            this.coupon_pic = coupon_pic;
        }

        public String getCoupon_desc() {
            return coupon_desc;
        }

        public void setCoupon_desc(String coupon_desc) {
            this.coupon_desc = coupon_desc;
        }

        public String getCoupon_value() {
            return coupon_value;
        }

        public void setCoupon_value(String coupon_value) {
            this.coupon_value = coupon_value;
        }

        public String getOriginal_price() {
            return original_price;
        }

        public void setOriginal_price(String original_price) {
            this.original_price = original_price;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getInstructions() {
            return instructions;
        }

        public void setInstructions(String instructions) {
            this.instructions = instructions;
        }

        public String getTips() {
            return tips;
        }

        public void setTips(String tips) {
            this.tips = tips;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public String getShop_logo() {
            return shop_logo;
        }

        public void setShop_logo(String shop_logo) {
            this.shop_logo = shop_logo;
        }

        public String getCoupon_code() {
            return coupon_code;
        }

        public void setCoupon_code(String coupon_code) {
            this.coupon_code = coupon_code;
        }

        public String getStation_id() {
            return station_id;
        }

        public void setStation_id(String station_id) {
            this.station_id = station_id;
        }
    }
}
