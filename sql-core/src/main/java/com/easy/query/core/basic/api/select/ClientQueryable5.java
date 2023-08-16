package com.easy.query.core.basic.api.select;

import com.easy.query.core.basic.api.select.extension.queryable5.SQLAggregate5Extension;
import com.easy.query.core.basic.api.select.extension.queryable5.SQLGroup5Extension;
import com.easy.query.core.basic.api.select.extension.queryable5.SQLHaving5Extension;
import com.easy.query.core.basic.api.select.extension.queryable5.SQLOrderBy5Extension;
import com.easy.query.core.basic.api.select.extension.queryable5.SQLSelect5Extension;
import com.easy.query.core.basic.api.select.extension.queryable5.SQLWhere5Extension;
import com.easy.query.core.basic.api.select.extension.queryable5.override.ClientOverrideQueryable5;
import com.easy.query.core.basic.api.select.provider.SQLExpressionProvider;

/**
 * create time 2023/8/16 14:02
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ClientQueryable5<T1, T2, T3, T4, T5> extends ClientOverrideQueryable5<T1, T2, T3, T4, T5>,
        SQLAggregate5Extension<T1, T2, T3, T4, T5>,
        SQLWhere5Extension<T1, T2, T3, T4, T5>,
        SQLSelect5Extension<T1, T2, T3, T4, T5>,
        SQLGroup5Extension<T1, T2, T3, T4, T5>,
        SQLHaving5Extension<T1, T2, T3, T4, T5>,
        SQLOrderBy5Extension<T1, T2, T3, T4, T5> {
    SQLExpressionProvider<T2> getSQLExpressionProvider2();

    SQLExpressionProvider<T3> getSQLExpressionProvider3();

    SQLExpressionProvider<T4> getSQLExpressionProvider4();

    SQLExpressionProvider<T5> getSQLExpressionProvider5();
}
