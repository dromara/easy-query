package com.easy.query.api4j.select;

import com.easy.query.api4j.select.extension.queryable10.SQLAggregatable10;
import com.easy.query.api4j.select.extension.queryable10.SQLFilterable10;
import com.easy.query.api4j.select.extension.queryable10.SQLGroupable10;
import com.easy.query.api4j.select.extension.queryable10.SQLHavingable10;
import com.easy.query.api4j.select.extension.queryable10.SQLOrderable10;
import com.easy.query.api4j.select.extension.queryable10.SQLSelectable10;
import com.easy.query.api4j.select.extension.queryable10.override.OverrideQueryable10;

/**
 * @author xuejiaming
 * @FileName: Queryable4.java
 * @Description: 文件说明
 * @Date: 2023/3/9 12:10
 */
public interface Queryable10<T1, T2, T3, T4, T5, T6,T7,T8,T9,T10> extends OverrideQueryable10<T1, T2, T3, T4, T5, T6,T7,T8,T9,T10>,
        SQLAggregatable10<T1, T2, T3, T4, T5, T6,T7,T8,T9,T10>,
        SQLFilterable10<T1, T2, T3, T4, T5, T6,T7,T8,T9,T10>,
        SQLGroupable10<T1, T2, T3, T4, T5, T6,T7,T8,T9,T10>,
        SQLHavingable10<T1, T2, T3, T4, T5, T6,T7,T8,T9,T10>,
        SQLOrderable10<T1, T2, T3, T4, T5, T6,T7,T8,T9,T10>,
        SQLSelectable10<T1, T2, T3, T4, T5, T6,T7,T8,T9,T10> {
}
