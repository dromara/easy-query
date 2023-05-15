package com.easy.query.core.basic.jdbc.types;

import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;

/**
 * @FileName: EasyResultSet.java
 * @Description: 文件说明
 * @Date: 2023/2/17 13:11
 * @author xuejiaming
 */
public class EasyResultSet {
    private int index;
    private  Class<?> propertyType;
    private final StreamResultSet streamResult;

    public EasyResultSet(StreamResultSet streamResult){

        this.streamResult = streamResult;
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

    public StreamResultSet getStreamResult() {
        return streamResult;
    }

    public boolean isPrimitive() {
        return propertyType != null && propertyType.isPrimitive();
    }
}
