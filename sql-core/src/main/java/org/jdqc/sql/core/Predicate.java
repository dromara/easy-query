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
public interface Predicate<Children> extends Serializable {
    default <R> Children eq(Property<R, ?> column, Object val) {
        return eq(true, column, val);
    }
    <R> Children eq(boolean condition, Property<R, ?> column, Object val);
}
