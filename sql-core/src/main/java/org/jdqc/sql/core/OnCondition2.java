package org.jdqc.sql.core;

/**
 * Copyright (c) 2021.biaodian.All Rights Reserved
 *
 * @FileName: Condition.java
 * @Description: 文件说明
 * @Date: 2023/2/4 22:32
 * @Created by xuejiaming
 */
public interface OnCondition2 {
    default <L,R> OnCondition2 eq(Property<L, ?> leftColumn, Property<R, ?> rightColumn){
        return eq(true,leftColumn,rightColumn);
    }
    <L,R> OnCondition2 eq(boolean condition, Property<L, ?> leftColumn, Property<R, ?> rightColumn);

}
