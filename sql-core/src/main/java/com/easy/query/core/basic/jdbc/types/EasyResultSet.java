package com.easy.query.core.basic.jdbc.types;

import java.beans.PropertyDescriptor;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

/**
 * @FileName: EasyResultSet.java
 * @Description: 文件说明
 * @Date: 2023/2/17 13:11
 * @author xuejiaming
 */
public class EasyResultSet {
    private int index;
    private  Class<?> propertyType;
    private final ResultSet rs;

    public EasyResultSet(ResultSet rs){

        this.rs = rs;
    }

    public int getIndex() {
        return index+1;
    }
    public void setIndex(int index) {
        this.index = index;
    }

    public Class<?> getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(Class<?> propertyType) {
        this.propertyType = propertyType;
    }

    public ResultSet getRs() {
        return rs;
    }

    public boolean isPrimitive() {
        return propertyType != null && propertyType.isPrimitive();
    }
}
