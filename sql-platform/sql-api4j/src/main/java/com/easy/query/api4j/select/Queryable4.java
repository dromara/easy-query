package com.easy.query.api4j.select;

import com.easy.query.api4j.select.extension.queryable4.SQLAggregatable4;
import com.easy.query.api4j.select.extension.queryable4.SQLFilterable4;
import com.easy.query.api4j.select.extension.queryable4.SQLGroupable4;
import com.easy.query.api4j.select.extension.queryable4.SQLHavingable4;
import com.easy.query.api4j.select.extension.queryable4.SQLJoinable4;
import com.easy.query.api4j.select.extension.queryable4.SQLOrderable4;
import com.easy.query.api4j.select.extension.queryable4.SQLSelectable4;
import com.easy.query.api4j.select.extension.queryable4.override.OverrideQueryable4;

/**
 * @author xuejiaming
 * @FileName: Queryable4.java
 * @Description: 文件说明
 * @Date: 2023/3/9 12:10
 */
public interface Queryable4<T1, T2, T3, T4> extends OverrideQueryable4<T1, T2, T3, T4>,
        SQLAggregatable4<T1, T2, T3, T4>,
        SQLFilterable4<T1, T2, T3, T4>,
        SQLGroupable4<T1, T2, T3, T4>,
        SQLHavingable4<T1, T2, T3, T4>,
        SQLOrderable4<T1, T2, T3, T4>,
        SQLJoinable4<T1, T2, T3, T4>,
        SQLSelectable4<T1, T2, T3, T4> {
}
