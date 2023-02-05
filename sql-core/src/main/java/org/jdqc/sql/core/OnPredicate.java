package org.jdqc.sql.core;

import java.io.Serializable;

/**
 * Copyright (c) 2021.biaodian.All Rights Reserved
 *
 * @FileName: OnPredicate.java
 * @Description: 文件说明
 * @Date: 2023/2/5 09:10
 * @Created by xuejiaming
 */
public interface OnPredicate<Children> extends Serializable {
    /**
     * ignore
     */
    default <R, S> Children eq(Property<R, ?> column, Property<S, ?> val) {
        return eq(true, column, val);
    }

    /**
     * 等于 =
     *
     * @param condition 执行条件
     * @param column    字段
     * @param val       值
     * @return children
     */
    <R, S> Children eq(boolean condition, Property<R, ?> column, Property<S, ?> val);
}
