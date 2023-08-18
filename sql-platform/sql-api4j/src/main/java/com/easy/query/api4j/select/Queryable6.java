package com.easy.query.api4j.select;

import com.easy.query.api4j.select.extension.queryable6.SQLAggregatable6;
import com.easy.query.api4j.select.extension.queryable6.SQLFilterable6;
import com.easy.query.api4j.select.extension.queryable6.SQLGroupable6;
import com.easy.query.api4j.select.extension.queryable6.SQLHavingable6;
import com.easy.query.api4j.select.extension.queryable6.SQLJoinable6;
import com.easy.query.api4j.select.extension.queryable6.SQLOrderable6;
import com.easy.query.api4j.select.extension.queryable6.SQLSelectable6;
import com.easy.query.api4j.select.extension.queryable6.override.OverrideQueryable6;

/**
 * @author xuejiaming
 * @FileName: Queryable4.java
 * @Description: 文件说明
 * @Date: 2023/3/9 12:10
 */
public interface Queryable6<T1, T2, T3, T4,T5,T6> extends OverrideQueryable6<T1, T2, T3, T4,T5,T6>,
        SQLAggregatable6<T1, T2, T3, T4,T5,T6>,
        SQLFilterable6<T1, T2, T3, T4,T5,T6>,
        SQLGroupable6<T1, T2, T3, T4,T5,T6>,
        SQLHavingable6<T1, T2, T3, T4,T5,T6>,
        SQLOrderable6<T1, T2, T3, T4,T5,T6>,
        SQLJoinable6<T1, T2, T3, T4,T5,T6>,
        SQLSelectable6<T1, T2, T3, T4,T5,T6> {
}
