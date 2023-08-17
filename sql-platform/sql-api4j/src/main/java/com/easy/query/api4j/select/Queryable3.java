package com.easy.query.api4j.select;

import com.easy.query.api4j.select.extension.queryable3.SQLAggregatable3;
import com.easy.query.api4j.select.extension.queryable3.SQLFilterable3;
import com.easy.query.api4j.select.extension.queryable3.SQLGroupable3;
import com.easy.query.api4j.select.extension.queryable3.SQLHavingable3;
import com.easy.query.api4j.select.extension.queryable3.SQLJoinable3;
import com.easy.query.api4j.select.extension.queryable3.SQLOrderable3;
import com.easy.query.api4j.select.extension.queryable3.SQLSelectable3;
import com.easy.query.api4j.select.extension.queryable3.override.OverrideQueryable3;


/**
 * @author xuejiaming
 * @FileName: Select3.java
 * @Description: 文件说明
 * @Date: 2023/2/6 22:44
 */
public interface Queryable3<T1, T2, T3> extends OverrideQueryable3<T1, T2, T3>,
        SQLFilterable3<T1, T2, T3>,
        SQLAggregatable3<T1, T2, T3>,
        SQLGroupable3<T1, T2, T3>,
        SQLHavingable3<T1, T2, T3>,
        SQLJoinable3<T1, T2, T3>,
        SQLOrderable3<T1, T2, T3> ,
        SQLSelectable3<T1, T2, T3> {
}
