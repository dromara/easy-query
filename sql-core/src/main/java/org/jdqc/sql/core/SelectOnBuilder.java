package org.jdqc.sql.core;

/**
 * Copyright (c) 2021.biaodian.All Rights Reserved
 *
 * @FileName: SelectOnBuilder.java
 * @Description: 文件说明
 * @Date: 2023/2/5 15:35
 * @Created by xuejiaming
 */
public abstract class SelectOnBuilder<T1,TR> {
    public SelectBuilder<T1, TR> getSelectBuilder() {
        return selectBuilder;
    }

    public Class<?> getJoinClass() {
        return joinClass;
    }

    private final SelectBuilder<T1, TR> selectBuilder;
    private final Class<?> joinClass;

    public SelectOnBuilder(SelectBuilder<T1,TR> selectBuilder, Class<?> joinClass){

        this.selectBuilder = selectBuilder;
        this.joinClass = joinClass;
    }
    public abstract SelectBuilder<T1, TR> on(OnFunction<SqlPredicate<T1,?>> onPredicate);
}
