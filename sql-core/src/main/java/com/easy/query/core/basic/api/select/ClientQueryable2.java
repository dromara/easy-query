package com.easy.query.core.basic.api.select;

import com.easy.query.core.basic.api.select.extension.queryable2.Aggregatable2;
import com.easy.query.core.basic.api.select.extension.queryable2.Groupable2;
import com.easy.query.core.basic.api.select.extension.queryable2.Havingable2;
import com.easy.query.core.basic.api.select.extension.queryable2.Joinable2;
import com.easy.query.core.basic.api.select.extension.queryable2.SubQueryToGroupJoinable2;
import com.easy.query.core.basic.api.select.extension.queryable2.Orderable2;
import com.easy.query.core.basic.api.select.extension.queryable2.Selectable2;
import com.easy.query.core.basic.api.select.extension.queryable2.Filterable2;
import com.easy.query.core.basic.api.select.extension.queryable2.override.ClientOverrideQueryable2;
import com.easy.query.core.basic.api.select.provider.SQLExpressionProvider;


/**
 * @author xuejiaming
 * @FileName: Select2.java
 * @Description: 文件说明
 * create time 2023/2/6 22:42
 */
public interface ClientQueryable2<T1, T2> extends ClientOverrideQueryable2<T1,T2>,
        Aggregatable2<T1, T2>,
        Joinable2<T1,T2>,
        SubQueryToGroupJoinable2<T1,T2>,
        Filterable2<T1,T2>,
        Selectable2<T1,T2>,
        Groupable2<T1,T2>,
        Havingable2<T1,T2>,
        Orderable2<T1,T2> {


    //endregion

    Class<T2> queryClass2();
    SQLExpressionProvider<T2> getSQLExpressionProvider2();
}
