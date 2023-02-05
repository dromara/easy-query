package org.jdqc.sql.core;

/**
 * Copyright (c) 2021.biaodian.All Rights Reserved
 *
 * @FileName: SelectLeftJoinImpl.java
 * @Description: 文件说明
 * @Date: 2023/2/5 21:55
 * @Created by xuejiaming
 */
public class SelectLeftJoinImpl<T1,TR> extends SelectJoinImpl<T1,TR>{

    public SelectLeftJoinImpl(Select<T1,TR> selectMain, Class<?> joinClass) {
        super(selectMain, joinClass);
    }

    @Override
    public Select<T1,TR> on(OnFunction<SqlPredicate<T1, ?>> onPredicate) {
        Class<?> joinClass = getJoinClass();
        OnFunction<SqlPredicate<T1, ?>> join=on->{
            SqlPredicate<T1, ?> apply = onPredicate.apply(on);
            //软删除 多租户
            return apply;
        };
        return getSelectMain();
    }
}
