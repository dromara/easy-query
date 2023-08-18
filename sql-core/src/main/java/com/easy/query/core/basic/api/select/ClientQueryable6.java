package com.easy.query.core.basic.api.select;

import com.easy.query.core.basic.api.select.extension.queryable6.Aggregatable6;
import com.easy.query.core.basic.api.select.extension.queryable6.Filterable6;
import com.easy.query.core.basic.api.select.extension.queryable6.Groupable6;
import com.easy.query.core.basic.api.select.extension.queryable6.Havingable6;
import com.easy.query.core.basic.api.select.extension.queryable6.Joinable6;
import com.easy.query.core.basic.api.select.extension.queryable6.Orderable6;
import com.easy.query.core.basic.api.select.extension.queryable6.Selectable6;
import com.easy.query.core.basic.api.select.extension.queryable6.override.ClientOverrideQueryable6;
import com.easy.query.core.basic.api.select.provider.SQLExpressionProvider;

/**
 * create time 2023/8/16 14:02
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ClientQueryable6<T1, T2, T3, T4, T5, T6> extends ClientOverrideQueryable6<T1, T2, T3, T4, T5, T6>,
        Aggregatable6<T1, T2, T3, T4, T5, T6>,
        Filterable6<T1, T2, T3, T4, T5, T6>,
        Selectable6<T1, T2, T3, T4, T5, T6>,
        Groupable6<T1, T2, T3, T4, T5, T6>,
        Havingable6<T1, T2, T3, T4, T5, T6>,
        Orderable6<T1, T2, T3, T4, T5, T6>,
        Joinable6<T1, T2, T3, T4, T5, T6> {
    Class<T2> queryClass2();
    Class<T3> queryClass3();
    Class<T4> queryClass4();
    Class<T5> queryClass5();
    Class<T6> queryClass6();
    SQLExpressionProvider<T2> getSQLExpressionProvider2();

    SQLExpressionProvider<T3> getSQLExpressionProvider3();

    SQLExpressionProvider<T4> getSQLExpressionProvider4();

    SQLExpressionProvider<T5> getSQLExpressionProvider5();
    SQLExpressionProvider<T6> getSQLExpressionProvider6();
}
