package com.easy.query.core.basic.api.select;

import com.easy.query.core.basic.api.select.extension.queryable10.Aggregatable10;
import com.easy.query.core.basic.api.select.extension.queryable10.Filterable10;
import com.easy.query.core.basic.api.select.extension.queryable10.Groupable10;
import com.easy.query.core.basic.api.select.extension.queryable10.Havingable10;
import com.easy.query.core.basic.api.select.extension.queryable10.Orderable10;
import com.easy.query.core.basic.api.select.extension.queryable10.Selectable10;
import com.easy.query.core.basic.api.select.extension.queryable10.override.ClientOverrideQueryable10;
import com.easy.query.core.basic.api.select.provider.SQLExpressionProvider;

/**
 * create time 2023/8/16 14:02
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ClientQueryable10<T1, T2, T3, T4, T5, T6,T7,T8,T9,T10> extends ClientOverrideQueryable10<T1, T2, T3, T4, T5, T6,T7,T8,T9,T10>,
        Aggregatable10<T1, T2, T3, T4, T5, T6,T7,T8,T9,T10>,
        Filterable10<T1, T2, T3, T4, T5, T6,T7,T8,T9,T10>,
        Selectable10<T1, T2, T3, T4, T5, T6,T7,T8,T9,T10>,
        Groupable10<T1, T2, T3, T4, T5, T6,T7,T8,T9,T10>,
        Havingable10<T1, T2, T3, T4, T5, T6,T7,T8,T9,T10>,
        Orderable10<T1, T2, T3, T4, T5, T6,T7,T8,T9,T10> {
    SQLExpressionProvider<T2> getSQLExpressionProvider2();

    SQLExpressionProvider<T3> getSQLExpressionProvider3();

    SQLExpressionProvider<T4> getSQLExpressionProvider4();

    SQLExpressionProvider<T5> getSQLExpressionProvider5();
    SQLExpressionProvider<T6> getSQLExpressionProvider6();
    SQLExpressionProvider<T7> getSQLExpressionProvider7();
    SQLExpressionProvider<T8> getSQLExpressionProvider8();
    SQLExpressionProvider<T9> getSQLExpressionProvider9();
    SQLExpressionProvider<T10> getSQLExpressionProvider10();
}
