package org.jdqc.sql.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) 2021.biaodian.All Rights Reserved
 *
 * @FileName: SelectBuilder.java
 * @Description: 文件说明
 * @Date: 2023/2/5 15:32
 * @Created by xuejiaming
 */
public class SelectBuilder<T1,TR> {
    private final Select<T1> mainSelect;
    private final Class<TR> trClass;
    private final List<Class<?>> joinTables;
    private final List<OnFunction<SqlPredicate<T1, ?>>> joinInvokes;

    public SelectBuilder(Select<T1> mainSelect, Class<TR> trClass){

        this.mainSelect = mainSelect;
        this.trClass = trClass;
        this.joinTables=new ArrayList<>();
        this.joinInvokes=new ArrayList<>();
    }
    public void addJoinInvoke(OnFunction<SqlPredicate<T1, ?>> joinInvoke)
    {
        joinInvokes.add(joinInvoke);
    }
    public SelectLeftJoinOnBuilder<T1,TR> leftJoin(Class<?> joinClass){
        joinTables.add(joinClass);
        return new SelectLeftJoinOnBuilder<>(this,joinClass);
    }
}
