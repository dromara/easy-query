package com.easy.query.api4j.select;

import com.easy.query.api4j.select.extension.queryable7.SQLAggregatable7;
import com.easy.query.api4j.select.extension.queryable7.SQLFilterable7;
import com.easy.query.api4j.select.extension.queryable7.SQLGroupable7;
import com.easy.query.api4j.select.extension.queryable7.SQLHavingable7;
import com.easy.query.api4j.select.extension.queryable7.SQLJoinable7;
import com.easy.query.api4j.select.extension.queryable7.SQLOrderable7;
import com.easy.query.api4j.select.extension.queryable7.SQLSelectable7;
import com.easy.query.api4j.select.extension.queryable7.override.OverrideQueryable7;

/**
 * @author xuejiaming
 * @FileName: Queryable4.java
 * @Description: 文件说明
 * @Date: 2023/3/9 12:10
 */
public interface Queryable7<T1, T2, T3, T4, T5, T6,T7> extends OverrideQueryable7<T1, T2, T3, T4, T5, T6,T7>,
        SQLAggregatable7<T1, T2, T3, T4, T5, T6,T7>,
        SQLFilterable7<T1, T2, T3, T4, T5, T6,T7>,
        SQLGroupable7<T1, T2, T3, T4, T5, T6,T7>,
        SQLHavingable7<T1, T2, T3, T4, T5, T6,T7>,
        SQLOrderable7<T1, T2, T3, T4, T5, T6,T7>,
        SQLJoinable7<T1, T2, T3, T4, T5, T6,T7>,
        SQLSelectable7<T1, T2, T3, T4, T5, T6,T7> {
}
