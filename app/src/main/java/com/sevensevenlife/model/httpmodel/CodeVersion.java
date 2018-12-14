package com.sevensevenlife.model.httpmodel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/12/29 0029.
 */

public class CodeVersion extends PublicMode {
    List<code> rows;

    public List<code> getRows() {
        return rows;
    }

    public void setRows(List<code> rows) {
        this.rows = rows;
    }

    public class code implements Serializable {
        String createtime;
        String userWorkStatus;
        String verinfo;
        String verlink;
        String vername;
        String vertype;
        String verid;
        boolean forced_update;

        public boolean isForced_update() {
            return forced_update;
        }

        public void setForced_update(boolean forced_update) {
            this.forced_update = forced_update;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getUserWorkStatus() {
            return userWorkStatus;
        }

        public void setUserWorkStatus(String userWorkStatus) {
            this.userWorkStatus = userWorkStatus;
        }

        public String getVerinfo() {
            return verinfo;
        }

        public void setVerinfo(String verinfo) {
            this.verinfo = verinfo;
        }

        public String getVerlink() {
            return verlink;
        }

        public void setVerlink(String verlink) {
            this.verlink = verlink;
        }

        public String getVername() {
            return vername;
        }

        public void setVername(String vername) {
            this.vername = vername;
        }

        public String getVertype() {
            return vertype;
        }

        public void setVertype(String vertype) {
            this.vertype = vertype;
        }

        public String getVerid() {
            return verid;
        }

        public void setVerid(String verid) {
            this.verid = verid;
        }
    }
}
