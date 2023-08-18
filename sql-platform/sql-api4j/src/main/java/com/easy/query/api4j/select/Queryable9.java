package com.easy.query.api4j.select;

import com.easy.query.api4j.select.extension.queryable9.SQLAggregatable9;
import com.easy.query.api4j.select.extension.queryable9.SQLFilterable9;
import com.easy.query.api4j.select.extension.queryable9.SQLGroupable9;
import com.easy.query.api4j.select.extension.queryable9.SQLHavingable9;
import com.easy.query.api4j.select.extension.queryable9.SQLJoinable9;
import com.easy.query.api4j.select.extension.queryable9.SQLOrderable9;
import com.easy.query.api4j.select.extension.queryable9.SQLSelectable9;
import com.easy.query.api4j.select.extension.queryable9.override.OverrideQueryable9;

/**
 * @author xuejiaming
 * @FileName: Queryable4.java
 * @Description: 文件说明
 * @Date: 2023/3/9 12:10
 */
public interface Queryable9<T1, T2, T3, T4, T5, T6,T7,T8,T9> extends OverrideQueryable9<T1, T2, T3, T4, T5, T6,T7,T8,T9>,
        SQLAggregatable9<T1, T2, T3, T4, T5, T6,T7,T8,T9>,
        SQLFilterable9<T1, T2, T3, T4, T5, T6,T7,T8,T9>,
        SQLGroupable9<T1, T2, T3, T4, T5, T6,T7,T8,T9>,
        SQLHavingable9<T1, T2, T3, T4, T5, T6,T7,T8,T9>,
        SQLOrderable9<T1, T2, T3, T4, T5, T6,T7,T8,T9>,
        SQLJoinable9<T1, T2, T3, T4, T5, T6,T7,T8,T9>,
        SQLSelectable9<T1, T2, T3, T4, T5, T6,T7,T8,T9> {
}
