package com.sevensevenlife.model.httpmodel;

import java.io.Serializable;

public class ProjectByParentLevelRows implements Serializable {
    /**
     * id : 4
     * typeName : 代买
     * sortno : 1
     * typecode : 0101
     * price : 15.0
     * typeDesc :
     */

    String id;
    String typeName;
    String sortno;
    String typecode;
    String price;
    String typeDesc;

    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getSortno() {
        return sortno;
    }

    public void setSortno(String sortno) {
        this.sortno = sortno;
    }

    public String getTypecode() {
        return typecode;
    }

    public void setTypecode(String typecode) {
        this.typecode = typecode;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
