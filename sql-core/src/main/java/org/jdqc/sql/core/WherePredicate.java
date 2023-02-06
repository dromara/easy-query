package org.jdqc.sql.core;

import java.io.Serializable;

/**
 * Copyright (c) 2021.biaodian.All Rights Reserved
 *
 * @FileName: Predicate.java
 * @Description: 文件说明
 * @Date: 2023/2/5 09:09
 * @Created by xuejiaming
 */
public interface WherePredicate<Children,T1> extends Serializable {
    default  Children eq(Property<T1, ?> column, Object val) {
        return eq(true, column, val);
    }
     Children eq(boolean condition, Property<T1, ?> column, Object val);
}
