package com.easy.query.core.basic.api.select;

import com.easy.query.core.basic.api.select.extension.queryable3.Aggregatable3;
import com.easy.query.core.basic.api.select.extension.queryable3.Groupable3;
import com.easy.query.core.basic.api.select.extension.queryable3.Havingable3;
import com.easy.query.core.basic.api.select.extension.queryable3.Joinable3;
import com.easy.query.core.basic.api.select.extension.queryable3.SubQueryToGroupJoinable3;
import com.easy.query.core.basic.api.select.extension.queryable3.Orderable3;
import com.easy.query.core.basic.api.select.extension.queryable3.Selectable3;
import com.easy.query.core.basic.api.select.extension.queryable3.Filterable3;
import com.easy.query.core.basic.api.select.extension.queryable3.override.ClientOverrideQueryable3;
import com.easy.query.core.basic.api.select.provider.SQLExpressionProvider;


/**
 * @author xuejiaming
 * @FileName: Select3.java
 * @Description: 文件说明
 * create time 2023/2/6 22:44
 */
public interface ClientQueryable3<T1, T2, T3> extends ClientOverrideQueryable3<T1,T2,T3> ,
        Aggregatable3<T1, T2, T3>,
        Joinable3<T1, T2, T3>,
        SubQueryToGroupJoinable3<T1, T2, T3>,
        Filterable3<T1, T2, T3>,
        Selectable3<T1, T2, T3>,
        Groupable3<T1, T2, T3>,
        Havingable3<T1, T2, T3>,
        Orderable3<T1, T2, T3> {

    Class<T2> queryClass2();
    Class<T3> queryClass3();
    SQLExpressionProvider<T2> getSQLExpressionProvider2();

    SQLExpressionProvider<T3> getSQLExpressionProvider3();
}
