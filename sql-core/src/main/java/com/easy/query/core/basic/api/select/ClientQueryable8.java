package com.easy.query.core.basic.api.select;

import com.easy.query.core.basic.api.select.extension.queryable8.Aggregatable8;
import com.easy.query.core.basic.api.select.extension.queryable8.Filterable8;
import com.easy.query.core.basic.api.select.extension.queryable8.Groupable8;
import com.easy.query.core.basic.api.select.extension.queryable8.Havingable8;
import com.easy.query.core.basic.api.select.extension.queryable8.Joinable8;
import com.easy.query.core.basic.api.select.extension.queryable8.Orderable8;
import com.easy.query.core.basic.api.select.extension.queryable8.Selectable8;
import com.easy.query.core.basic.api.select.extension.queryable8.override.ClientOverrideQueryable8;
import com.easy.query.core.basic.api.select.provider.SQLExpressionProvider;

/**
 * create time 2023/8/16 14:02
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ClientQueryable8<T1, T2, T3, T4, T5, T6,T7,T8> extends ClientOverrideQueryable8<T1, T2, T3, T4, T5, T6,T7,T8>,
        Aggregatable8<T1, T2, T3, T4, T5, T6,T7,T8>,
        Filterable8<T1, T2, T3, T4, T5, T6,T7,T8>,
        Selectable8<T1, T2, T3, T4, T5, T6,T7,T8>,
        Groupable8<T1, T2, T3, T4, T5, T6,T7,T8>,
        Havingable8<T1, T2, T3, T4, T5, T6,T7,T8>,
        Orderable8<T1, T2, T3, T4, T5, T6,T7,T8>,
        Joinable8<T1, T2, T3, T4, T5, T6,T7,T8> {
    Class<T2> queryClass2();
    Class<T3> queryClass3();
    Class<T4> queryClass4();
    Class<T5> queryClass5();
    Class<T6> queryClass6();
    Class<T7> queryClass7();
    Class<T8> queryClass8();
    SQLExpressionProvider<T2> getSQLExpressionProvider2();

    SQLExpressionProvider<T3> getSQLExpressionProvider3();

    SQLExpressionProvider<T4> getSQLExpressionProvider4();

    SQLExpressionProvider<T5> getSQLExpressionProvider5();
    SQLExpressionProvider<T6> getSQLExpressionProvider6();
    SQLExpressionProvider<T7> getSQLExpressionProvider7();
    SQLExpressionProvider<T8> getSQLExpressionProvider8();
}
