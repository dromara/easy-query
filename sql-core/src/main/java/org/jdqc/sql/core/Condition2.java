package org.jdqc.sql.core;

/**
 * Copyright (c) 2021.biaodian.All Rights Reserved
 *
 * @FileName: Condition2.java
 * @Description: 文件说明
 * @Date: 2023/2/4 22:56
 * @Created by xuejiaming
 */
public interface Condition2 {
    default <T>  Condition2 eq(Property<T, ?> column, Object val){
        return eq(true,column,val);
    }
    <T>  Condition2 eq(boolean condition, Property<T, ?> column, Object val);

}
