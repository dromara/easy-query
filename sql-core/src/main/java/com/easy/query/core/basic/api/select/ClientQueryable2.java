package com.easy.query.core.basic.api.select;

import com.easy.query.core.basic.api.select.extension.queryable2.SQLAggregate2Extension;
import com.easy.query.core.basic.api.select.extension.queryable2.SQLGroup2Extension;
import com.easy.query.core.basic.api.select.extension.queryable2.SQLHaving2Extension;
import com.easy.query.core.basic.api.select.extension.queryable2.SQLJoin2Extension;
import com.easy.query.core.basic.api.select.extension.queryable2.SQLOrderBy2Extension;
import com.easy.query.core.basic.api.select.extension.queryable2.SQLSelect2Extension;
import com.easy.query.core.basic.api.select.extension.queryable2.SQLWhere2Extension;
import com.easy.query.core.basic.api.select.extension.queryable2.override.ClientOverrideQueryable2;
import com.easy.query.core.basic.api.select.provider.SQLExpressionProvider;


/**
 * @author xuejiaming
 * @FileName: Select2.java
 * @Description: 文件说明
 * @Date: 2023/2/6 22:42
 */
public interface ClientQueryable2<T1, T2> extends ClientOverrideQueryable2<T1,T2>,
        SQLAggregate2Extension<T1, T2>,
        SQLJoin2Extension<T1,T2>,
        SQLWhere2Extension<T1,T2>,
        SQLSelect2Extension<T1,T2>,
        SQLGroup2Extension<T1,T2>,
        SQLHaving2Extension<T1,T2>,
        SQLOrderBy2Extension<T1,T2> {


    //endregion

    SQLExpressionProvider<T2> getSQLExpressionProvider2();
}
