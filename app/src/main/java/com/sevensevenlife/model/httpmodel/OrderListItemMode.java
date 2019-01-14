package com.sevensevenlife.model.httpmodel;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/1/10 0010.
 */

public class OrderListItemMode implements Serializable {

    String id;
    String order_no;
    String cust_id;
    String cust_name;
    String service_id;
    String service_pic;
    String service_name;
    String service_phone;
    String project_id;
    String project_name;
    String deliver_address;
    String contact_phone;
    String pick_address;
    String pick_phpne;
    String description;
    String photo;
    String prepayment;
    String pre_pay_status;
    String pre_pay_type;
    String pre_pay_time;
    String remain_money;
    String rem_pay_status;
    String rem_pay_type;
    String rem_pay_time;
    String finish_status;
    String finish_time;
    String miss_reason;
    String create_date;
    String pre_notify_Url;
    String rem_notify_Url;
    String reserve_time;
    String order_type;
    String active_flag;
    String distance;
    String couponId;
    String couponMoney;
    String pre_pay_tradeno;
    String rem_order_no;
    String orderString;
    String orgi_price;
    String price;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOrgi_price() {
        return orgi_price;
    }

    public void setOrgi_price(String orgi_price) {
        this.orgi_price = orgi_price;
    }

    public String getOrderString() {
        return orderString;
    }

    public void setOrderString(String orderString) {
        this.orderString = orderString;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getCust_id() {
        return cust_id;
    }

    public void setCust_id(String cust_id) {
        this.cust_id = cust_id;
    }

    public String getCust_name() {
        return cust_name;
    }

    public void setCust_name(String cust_name) {
        this.cust_name = cust_name;
    }

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getService_pic() {
        return service_pic;
    }

    public void setService_pic(String service_pic) {
        this.service_pic = service_pic;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getService_phone() {
        return service_phone;
    }

    public void setService_phone(String service_phone) {
        this.service_phone = service_phone;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getDeliver_address() {
        return deliver_address;
    }

    public void setDeliver_address(String deliver_address) {
        this.deliver_address = deliver_address;
    }

    public String getContact_phone() {
        return contact_phone;
    }

    public void setContact_phone(String contact_phone) {
        this.contact_phone = contact_phone;
    }

    public String getPick_address() {
        return pick_address;
    }

    public void setPick_address(String pick_address) {
        this.pick_address = pick_address;
    }

    public String getPick_phpne() {
        return pick_phpne;
    }

    public void setPick_phpne(String pick_phpne) {
        this.pick_phpne = pick_phpne;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPrepayment() {
        return prepayment;
    }

    public void setPrepayment(String prepayment) {
        this.prepayment = prepayment;
    }

    public String getPre_pay_status() {
        return pre_pay_status;
    }

    public void setPre_pay_status(String pre_pay_status) {
        this.pre_pay_status = pre_pay_status;
    }

    public String getPre_pay_type() {
        return pre_pay_type;
    }

    public void setPre_pay_type(String pre_pay_type) {
        this.pre_pay_type = pre_pay_type;
    }

    public String getPre_pay_time() {
        return pre_pay_time;
    }

    public void setPre_pay_time(String pre_pay_time) {
        this.pre_pay_time = pre_pay_time;
    }

    public String getRemain_money() {
        return remain_money;
    }

    public void setRemain_money(String remain_money) {
        this.remain_money = remain_money;
    }

    public String getRem_pay_status() {
        return rem_pay_status;
    }

    public void setRem_pay_status(String rem_pay_status) {
        this.rem_pay_status = rem_pay_status;
    }

    public String getRem_pay_type() {
        return rem_pay_type;
    }

    public void setRem_pay_type(String rem_pay_type) {
        this.rem_pay_type = rem_pay_type;
    }

    public String getRem_pay_time() {
        return rem_pay_time;
    }

    public void setRem_pay_time(String rem_pay_time) {
        this.rem_pay_time = rem_pay_time;
    }

    public String getFinish_status() {
        return finish_status;
    }

    public void setFinish_status(String finish_status) {
        this.finish_status = finish_status;
    }

    public String getFinish_time() {
        return finish_time;
    }

    public void setFinish_time(String finish_time) {
        this.finish_time = finish_time;
    }

    public String getMiss_reason() {
        return miss_reason;
    }

    public void setMiss_reason(String miss_reason) {
        this.miss_reason = miss_reason;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getPre_notify_Url() {
        return pre_notify_Url;
    }

    public void setPre_notify_Url(String pre_notify_Url) {
        this.pre_notify_Url = pre_notify_Url;
    }

    public String getRem_notify_Url() {
        return rem_notify_Url;
    }

    public void setRem_notify_Url(String rem_notify_Url) {
        this.rem_notify_Url = rem_notify_Url;
    }

    public String getReserve_time() {
        return reserve_time;
    }

    public void setReserve_time(String reserve_time) {
        this.reserve_time = reserve_time;
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    public String getActive_flag() {
        return active_flag;
    }

    public void setActive_flag(String active_flag) {
        this.active_flag = active_flag;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getCouponMoney() {
        return couponMoney;
    }

    public void setCouponMoney(String couponMoney) {
        this.couponMoney = couponMoney;
    }

    public String getPre_pay_tradeno() {
        return pre_pay_tradeno;
    }

    public void setPre_pay_tradeno(String pre_pay_tradeno) {
        this.pre_pay_tradeno = pre_pay_tradeno;
    }

    public String getRem_order_no() {
        return rem_order_no;
    }

    public void setRem_order_no(String rem_order_no) {
        this.rem_order_no = rem_order_no;
    }
}
