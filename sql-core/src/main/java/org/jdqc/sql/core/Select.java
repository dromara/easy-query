package org.jdqc.sql.core;

/**
 * Copyright (c) 2021.biaodian.All Rights Reserved
 *
 * @FileName: Select.java
 * @Description: 文件说明
 * @Date: 2023/2/4 23:32
 * @Created by xuejiaming
 */
public interface Select<T1,TR> extends SqlPredicate<T1,Select<T1,TR>>,SqlResult<TR>,SqlFilter<Select<T1,TR>>{
    <T2> Select<T1,TR> leftJoin(Class<T2> t2Class,OnFunction<SqlPredicate<T1, ?>> on);
    Select<T1,TR> selectAll(Class<?> selectClass);
    Select<T1,TR> select(Property<T1,?>... columns);
   <TEntity> Select<T1,TR> selectAs(Property<TEntity,?> column,Property<TR, ?> alias);
}
