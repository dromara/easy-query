package org.jdqc.sql.core.abstraction.sql.base;

import java.io.Serializable;

/**
 * Copyright (c) 2021.biaodian.All Rights Reserved
 *
 * @FileName: OnPredicate.java
 * @Description: 文件说明
 * @Date: 2023/2/5 09:10
 * @Created by xuejiaming
 */
public interface OnPredicate2<T1,Children> extends Serializable {
    /**
     * ignore
     */
    default Children eq(Property<T1, ?> column, Object val) {
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
     Children eq(boolean condition, Property<T1, ?> column, Object val);
   default  <T2,TChildren> Children eq(Property<T1, ?> column, OnPredicate2<T2,TChildren> join, Property<T2,?>column2){
       return eq(true,column,column2);
   }
    <T2,TChildren> Children eq(boolean condition, Property<T1, ?> column, Property<OnPredicate2<T2,TChildren>,Property<T2,?>> column2);
    <T2,TChildren> OnPredicate2<T2,TChildren> and(OnPredicate2<T2,TChildren> join);
}
