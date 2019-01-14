package com.sevensevenlife.model.httpmodel;

/**
 * Created by Administrator on 2017/8/22 0022.
 */

public class AddMessageMode extends PublicMode {

    /**
     * rows : {"id":1,"user_id":"","title":"优享生活优惠券","content":"免费体验一次（内部测试）","image":"https://p1.meituan.net/deal/5ff2613a2b140a8d7d62e4bf102ca9bb605889.jpg","url":"http://218.75.134.187:7777/yxsh-api/html/coupon.html?id=1&stationId=1&sessionId=A1DA2A20ECAF408AC93588600B379C75","station_id":""}
     */

    private AddMessageRows rows;

    public AddMessageRows getRows() {
        return rows;
    }

    public void setRows(AddMessageRows rows) {
        this.rows = rows;
    }


}
