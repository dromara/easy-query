package com.easy.query.api4j.select;

import com.easy.query.api4j.select.extension.queryable2.SQLAggregatable2;
import com.easy.query.api4j.select.extension.queryable2.SQLFilterable2;
import com.easy.query.api4j.select.extension.queryable2.SQLGroupable2;
import com.easy.query.api4j.select.extension.queryable2.SQLHavingable2;
import com.easy.query.api4j.select.extension.queryable2.SQLJoinable2;
import com.easy.query.api4j.select.extension.queryable2.SQLOrderable2;
import com.easy.query.api4j.select.extension.queryable2.SQLSelectable2;
import com.easy.query.api4j.select.extension.queryable2.override.OverrideQueryable2;


/**
 * @author xuejiaming
 * @FileName: Select2.java
 * @Description: 文件说明
 * @Date: 2023/2/6 22:42
 */
public interface Queryable2<T1, T2> extends OverrideQueryable2<T1, T2>,
        SQLFilterable2<T1, T2>,
        SQLSelectable2<T1, T2>,
        SQLAggregatable2<T1, T2>,
        SQLGroupable2<T1, T2>,
        SQLHavingable2<T1, T2>,
        SQLOrderable2<T1, T2>,
        SQLJoinable2<T1, T2> {
}
