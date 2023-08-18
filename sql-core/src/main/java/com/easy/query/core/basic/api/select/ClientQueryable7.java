package com.easy.query.core.basic.api.select;

import com.easy.query.core.basic.api.select.extension.queryable7.Aggregatable7;
import com.easy.query.core.basic.api.select.extension.queryable7.Filterable7;
import com.easy.query.core.basic.api.select.extension.queryable7.Groupable7;
import com.easy.query.core.basic.api.select.extension.queryable7.Havingable7;
import com.easy.query.core.basic.api.select.extension.queryable7.Joinable7;
import com.easy.query.core.basic.api.select.extension.queryable7.Orderable7;
import com.easy.query.core.basic.api.select.extension.queryable7.Selectable7;
import com.easy.query.core.basic.api.select.extension.queryable7.override.ClientOverrideQueryable7;
import com.easy.query.core.basic.api.select.provider.SQLExpressionProvider;

/**
 * create time 2023/8/16 14:02
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ClientQueryable7<T1, T2, T3, T4, T5, T6,T7> extends ClientOverrideQueryable7<T1, T2, T3, T4, T5, T6,T7>,
        Aggregatable7<T1, T2, T3, T4, T5, T6,T7>,
        Filterable7<T1, T2, T3, T4, T5, T6,T7>,
        Selectable7<T1, T2, T3, T4, T5, T6,T7>,
        Groupable7<T1, T2, T3, T4, T5, T6,T7>,
        Havingable7<T1, T2, T3, T4, T5, T6,T7>,
        Orderable7<T1, T2, T3, T4, T5, T6,T7>,
        Joinable7<T1, T2, T3, T4, T5, T6,T7> {
    Class<T2> queryClass2();
    Class<T3> queryClass3();
    Class<T4> queryClass4();
    Class<T5> queryClass5();
    Class<T6> queryClass6();
    Class<T7> queryClass7();
    SQLExpressionProvider<T2> getSQLExpressionProvider2();

    SQLExpressionProvider<T3> getSQLExpressionProvider3();

    SQLExpressionProvider<T4> getSQLExpressionProvider4();

    SQLExpressionProvider<T5> getSQLExpressionProvider5();
    SQLExpressionProvider<T6> getSQLExpressionProvider6();
    SQLExpressionProvider<T7> getSQLExpressionProvider7();
}
