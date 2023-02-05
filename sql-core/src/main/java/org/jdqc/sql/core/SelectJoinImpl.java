package org.jdqc.sql.core;

/**
 * Copyright (c) 2021.biaodian.All Rights Reserved
 *
 * @FileName: SelectJoinImpl.java
 * @Description: 文件说明
 * @Date: 2023/2/5 21:55
 * @Created by xuejiaming
 */
public abstract class SelectJoinImpl<T1,TR> implements SelectLeftJoin<T1,TR>{
    public Select<T1,TR> getSelectMain() {
        return selectMain;
    }

    public Class<?> getJoinClass() {
        return joinClass;
    }

    private final Select<T1,TR> selectMain;
    private final Class<?> joinClass;

    public SelectJoinImpl(Select<T1,TR> selectMain,Class<?> joinClass){

        this.selectMain = selectMain;
        this.joinClass = joinClass;
    }
    public abstract Select<T1,TR> on(OnFunction<SqlPredicate<T1, ?>> onPredicate);
}
