package com.easy.query.core.basic.api.select.provider;

import com.easy.query.core.expression.parser.abstraction.*;
import com.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import com.easy.query.core.expression.parser.abstraction.*;

/**
 * @FileName: EasyQueryLambdaBuilder.java
 * @Description: 文件说明
 * @Date: 2023/2/12 10:02
 * @author xuejiaming
 */
public interface EasyQuerySqlBuilderProvider4<T1,T2,T3,T4> extends EasyQuerySqlBuilderProvider3<T1,T2,T3> {
    SqlGroupByColumnSelector<T4> getSqlGroupColumnSelector4();
    SqlColumnSelector<T4> getSqlOrderColumnSelector4(boolean asc);
    SqlPredicate<T4> getSqlWherePredicate4();
    SqlAggregatePredicate<T4> getSqlAggregatePredicate4();
    SqlPredicate<T4> getSqlOnPredicate4();

    SqlColumnSelector<T4> getSqlColumnSelector4(SqlBuilderSegment sqlSegment0Builder);
    <TR> SqlColumnAsSelector<T4,TR> getSqlColumnAsSelector4(SqlBuilderSegment sqlSegment0Builder,Class<TR> resultClass);
//    <TR> SqlColumnAsSelector<T4,TR> getAutoSqlColumnAsSelector4(SqlBuilderSegment sqlSegment0Builder,Class<TR> resultClass);
    <TR> SqlColumnResultSelector<T4,TR> getSqlColumnResultSelector4(SqlBuilderSegment sqlSegment0Builder);
}
