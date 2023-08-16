package com.easy.query.core.basic.api.select;

import com.easy.query.core.basic.api.select.extension.queryable4.SQLAggregate4Extension;
import com.easy.query.core.basic.api.select.extension.queryable4.SQLGroup4Extension;
import com.easy.query.core.basic.api.select.extension.queryable4.SQLHaving4Extension;
import com.easy.query.core.basic.api.select.extension.queryable4.SQLJoin4Extension;
import com.easy.query.core.basic.api.select.extension.queryable4.SQLOrderBy4Extension;
import com.easy.query.core.basic.api.select.extension.queryable4.SQLSelect4Extension;
import com.easy.query.core.basic.api.select.extension.queryable4.SQLWhere4Extension;
import com.easy.query.core.basic.api.select.extension.queryable4.override.ClientOverrideQueryable4;
import com.easy.query.core.basic.api.select.provider.SQLExpressionProvider;

/**
 * @author xuejiaming
 * @FileName: Queryable4.java
 * @Description: 文件说明
 * @Date: 2023/3/9 12:10
 */
public interface ClientQueryable4<T1, T2, T3, T4> extends ClientOverrideQueryable4<T1,T2,T3,T4>,
        SQLAggregate4Extension<T1, T2, T3, T4>,
        SQLJoin4Extension<T1, T2, T3, T4>,
        SQLWhere4Extension<T1, T2, T3, T4>,
        SQLSelect4Extension<T1, T2, T3, T4>,
        SQLGroup4Extension<T1, T2, T3, T4>,
        SQLHaving4Extension<T1, T2, T3, T4>,
        SQLOrderBy4Extension<T1, T2, T3, T4> {

    SQLExpressionProvider<T2> getSQLExpressionProvider2();

    SQLExpressionProvider<T3> getSQLExpressionProvider3();

    SQLExpressionProvider<T4> getSQLExpressionProvider4();
}
