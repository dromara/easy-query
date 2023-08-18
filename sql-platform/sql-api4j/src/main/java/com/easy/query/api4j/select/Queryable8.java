package com.easy.query.api4j.select;

import com.easy.query.api4j.select.extension.queryable8.SQLAggregatable8;
import com.easy.query.api4j.select.extension.queryable8.SQLFilterable8;
import com.easy.query.api4j.select.extension.queryable8.SQLGroupable8;
import com.easy.query.api4j.select.extension.queryable8.SQLHavingable8;
import com.easy.query.api4j.select.extension.queryable8.SQLJoinable8;
import com.easy.query.api4j.select.extension.queryable8.SQLOrderable8;
import com.easy.query.api4j.select.extension.queryable8.SQLSelectable8;
import com.easy.query.api4j.select.extension.queryable8.override.OverrideQueryable8;

/**
 * @author xuejiaming
 * @FileName: Queryable4.java
 * @Description: 文件说明
 * @Date: 2023/3/9 12:10
 */
public interface Queryable8<T1, T2, T3, T4, T5, T6,T7,T8> extends OverrideQueryable8<T1, T2, T3, T4, T5, T6,T7,T8>,
        SQLAggregatable8<T1, T2, T3, T4, T5, T6,T7,T8>,
        SQLFilterable8<T1, T2, T3, T4, T5, T6,T7,T8>,
        SQLGroupable8<T1, T2, T3, T4, T5, T6,T7,T8>,
        SQLHavingable8<T1, T2, T3, T4, T5, T6,T7,T8>,
        SQLOrderable8<T1, T2, T3, T4, T5, T6,T7,T8>,
        SQLJoinable8<T1, T2, T3, T4, T5, T6,T7,T8>,
        SQLSelectable8<T1, T2, T3, T4, T5, T6,T7,T8> {
}
