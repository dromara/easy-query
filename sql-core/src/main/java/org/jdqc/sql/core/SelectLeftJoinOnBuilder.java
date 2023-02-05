package org.jdqc.sql.core;

/**
 * Copyright (c) 2021.biaodian.All Rights Reserved
 *
 * @FileName: SelectLeftJoinOnBuilder.java
 * @Description: 文件说明
 * @Date: 2023/2/5 15:41
 * @Created by xuejiaming
 */
public class SelectLeftJoinOnBuilder<T1,TR> extends SelectOnBuilder<T1,TR> {
    public SelectLeftJoinOnBuilder(SelectBuilder<T1, TR> selectBuilder, Class<?> joinClass) {
        super(selectBuilder, joinClass);
    }

    @Override
    public SelectBuilder<T1, TR> on(OnFunction<SqlPredicate<T1, ?>> onPredicate) {
        Class<?> joinClass = getJoinClass();
        OnFunction<SqlPredicate<T1, ?>> join=on->{
            SqlPredicate<T1, ?> apply = onPredicate.apply(on);
            //软删除 多租户
            return apply;
        };
        SelectBuilder<T1, TR> selectBuilder = getSelectBuilder();
        //添加join
        selectBuilder.addJoinInvoke(join);
        return selectBuilder;
    }
}
