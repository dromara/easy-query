package com.easy.query.core.basic.api.select;

import com.easy.query.core.basic.api.select.extension.queryable4.Aggregatable4;
import com.easy.query.core.basic.api.select.extension.queryable4.Groupable4;
import com.easy.query.core.basic.api.select.extension.queryable4.Havingable4;
import com.easy.query.core.basic.api.select.extension.queryable4.Joinable4;
import com.easy.query.core.basic.api.select.extension.queryable4.Orderable4;
import com.easy.query.core.basic.api.select.extension.queryable4.Selectable4;
import com.easy.query.core.basic.api.select.extension.queryable4.Filterable4;
import com.easy.query.core.basic.api.select.extension.queryable4.override.ClientOverrideQueryable4;
import com.easy.query.core.basic.api.select.provider.SQLExpressionProvider;

/**
 * @author xuejiaming
 * @FileName: Queryable4.java
 * @Description: 文件说明
 * create time 2023/3/9 12:10
 */
public interface ClientQueryable4<T1, T2, T3, T4> extends ClientOverrideQueryable4<T1,T2,T3,T4>,
        Aggregatable4<T1, T2, T3, T4>,
        Joinable4<T1, T2, T3, T4>,
        Filterable4<T1, T2, T3, T4>,
        Selectable4<T1, T2, T3, T4>,
        Groupable4<T1, T2, T3, T4>,
        Havingable4<T1, T2, T3, T4>,
        Orderable4<T1, T2, T3, T4> {

    Class<T2> queryClass2();
    Class<T3> queryClass3();
    Class<T4> queryClass4();
    SQLExpressionProvider<T2> getSQLExpressionProvider2();

    SQLExpressionProvider<T3> getSQLExpressionProvider3();

    SQLExpressionProvider<T4> getSQLExpressionProvider4();
}
