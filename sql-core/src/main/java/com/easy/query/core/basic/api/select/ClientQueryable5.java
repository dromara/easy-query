package com.easy.query.core.basic.api.select;

import com.easy.query.core.basic.api.select.extension.queryable5.Aggregatable5;
import com.easy.query.core.basic.api.select.extension.queryable5.Filterable5;
import com.easy.query.core.basic.api.select.extension.queryable5.Groupable5;
import com.easy.query.core.basic.api.select.extension.queryable5.Havingable5;
import com.easy.query.core.basic.api.select.extension.queryable5.Joinable5;
import com.easy.query.core.basic.api.select.extension.queryable5.Orderable5;
import com.easy.query.core.basic.api.select.extension.queryable5.Selectable5;
import com.easy.query.core.basic.api.select.extension.queryable5.override.ClientOverrideQueryable5;
import com.easy.query.core.basic.api.select.provider.SQLExpressionProvider;

/**
 * create time 2023/8/16 14:02
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ClientQueryable5<T1, T2, T3, T4, T5> extends ClientOverrideQueryable5<T1, T2, T3, T4, T5>,
        Aggregatable5<T1, T2, T3, T4, T5>,
        Filterable5<T1, T2, T3, T4, T5>,
        Selectable5<T1, T2, T3, T4, T5>,
        Groupable5<T1, T2, T3, T4, T5>,
        Havingable5<T1, T2, T3, T4, T5>,
        Orderable5<T1, T2, T3, T4, T5>,
        Joinable5<T1, T2, T3, T4, T5> {
    Class<T2> queryClass2();
    Class<T3> queryClass3();
    Class<T4> queryClass4();
    Class<T5> queryClass5();
    SQLExpressionProvider<T2> getSQLExpressionProvider2();

    SQLExpressionProvider<T3> getSQLExpressionProvider3();

    SQLExpressionProvider<T4> getSQLExpressionProvider4();

    SQLExpressionProvider<T5> getSQLExpressionProvider5();
}
