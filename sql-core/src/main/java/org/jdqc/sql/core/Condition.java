package org.jdqc.sql.core;

import java.io.Serializable;

/**
 * Copyright (c) 2021.biaodian.All Rights Reserved
 *
 * @FileName: Condition.java
 * @Description: 文件说明
 * @Date: 2023/2/4 22:36
 * @Created by xuejiaming
 */
public interface Condition<T,R> extends Serializable {
    default  T eq(Property<R,?> column, Object val){
        return eq(true,column,val);
    }
    T eq(boolean condition, Property<R,?> column, Object val);
}
