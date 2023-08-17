package com.easy.query.core.basic.api.select;

import com.easy.query.core.basic.api.select.extension.queryable9.Aggregatable9;
import com.easy.query.core.basic.api.select.extension.queryable9.Filterable9;
import com.easy.query.core.basic.api.select.extension.queryable9.Groupable9;
import com.easy.query.core.basic.api.select.extension.queryable9.Havingable9;
import com.easy.query.core.basic.api.select.extension.queryable9.Joinable9;
import com.easy.query.core.basic.api.select.extension.queryable9.Orderable9;
import com.easy.query.core.basic.api.select.extension.queryable9.Selectable9;
import com.easy.query.core.basic.api.select.extension.queryable9.override.ClientOverrideQueryable9;
import com.easy.query.core.basic.api.select.provider.SQLExpressionProvider;

/**
 * create time 2023/8/16 14:02
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ClientQueryable9<T1, T2, T3, T4, T5, T6,T7,T8,T9> extends ClientOverrideQueryable9<T1, T2, T3, T4, T5, T6,T7,T8,T9>,
        Aggregatable9<T1, T2, T3, T4, T5, T6,T7,T8,T9>,
        Filterable9<T1, T2, T3, T4, T5, T6,T7,T8,T9>,
        Selectable9<T1, T2, T3, T4, T5, T6,T7,T8,T9>,
        Groupable9<T1, T2, T3, T4, T5, T6,T7,T8,T9>,
        Havingable9<T1, T2, T3, T4, T5, T6,T7,T8,T9>,
        Orderable9<T1, T2, T3, T4, T5, T6,T7,T8,T9> ,
        Joinable9<T1, T2, T3, T4, T5, T6,T7,T8,T9> {
    SQLExpressionProvider<T2> getSQLExpressionProvider2();

    SQLExpressionProvider<T3> getSQLExpressionProvider3();

    SQLExpressionProvider<T4> getSQLExpressionProvider4();

    SQLExpressionProvider<T5> getSQLExpressionProvider5();
    SQLExpressionProvider<T6> getSQLExpressionProvider6();
    SQLExpressionProvider<T7> getSQLExpressionProvider7();
    SQLExpressionProvider<T8> getSQLExpressionProvider8();
    SQLExpressionProvider<T9> getSQLExpressionProvider9();
}
