package com.easy.query.api4j.select;

import com.easy.query.api4j.select.extension.queryable5.SQLAggregatable5;
import com.easy.query.api4j.select.extension.queryable5.SQLFilterable5;
import com.easy.query.api4j.select.extension.queryable5.SQLGroupable5;
import com.easy.query.api4j.select.extension.queryable5.SQLHavingable5;
import com.easy.query.api4j.select.extension.queryable5.SQLJoinable5;
import com.easy.query.api4j.select.extension.queryable5.SQLOrderable5;
import com.easy.query.api4j.select.extension.queryable5.SQLSelectable5;
import com.easy.query.api4j.select.extension.queryable5.override.OverrideQueryable5;

/**
 * @author xuejiaming
 * @FileName: Queryable4.java
 * @Description: 文件说明
 * @Date: 2023/3/9 12:10
 */
public interface Queryable5<T1, T2, T3, T4,T5> extends OverrideQueryable5<T1, T2, T3, T4,T5>,
        SQLAggregatable5<T1, T2, T3, T4, T5>,
        SQLFilterable5<T1, T2, T3, T4, T5>,
        SQLGroupable5<T1, T2, T3, T4, T5>,
        SQLHavingable5<T1, T2, T3, T4, T5>,
        SQLOrderable5<T1, T2, T3, T4, T5>,
        SQLJoinable5<T1, T2, T3, T4, T5>,
        SQLSelectable5<T1, T2, T3, T4, T5> {
}
