package com.easy.query.core.basic.api.select.provider;

import com.easy.query.core.expression.parser.core.SqlAggregatePredicate;
import com.easy.query.core.expression.parser.core.SqlColumnAsSelector;
import com.easy.query.core.expression.parser.core.SqlColumnResultSelector;
import com.easy.query.core.expression.parser.core.SqlColumnSelector;
import com.easy.query.core.expression.parser.core.SqlPredicate;
import com.easy.query.core.expression.parser.core.SqlGroupBySelector;
import com.easy.query.core.expression.segment.builder.SqlBuilderSegment;

/**
 * @FileName: EasyQueryLambdaBuilder.java
 * @Description: 文件说明
 * @Date: 2023/2/12 10:02
 * @author xuejiaming
 */
public interface EasyQuerySqlBuilderProvider3<T1,T2,T3> extends EasyQuerySqlBuilderProvider2<T1,T2> {
    SqlGroupBySelector<T3> getSqlGroupColumnSelector3();
    SqlColumnSelector<T3> getSqlOrderColumnSelector3(boolean asc);
    SqlPredicate<T3> getSqlWherePredicate3();
    SqlAggregatePredicate<T3> getSqlAggregatePredicate3();
    SqlPredicate<T3> getSqlOnPredicate3();

    SqlColumnSelector<T3> getSqlColumnSelector3(SqlBuilderSegment sqlSegment0Builder);
    <TR> SqlColumnAsSelector<T3,TR> getSqlColumnAsSelector3(SqlBuilderSegment sqlSegment0Builder, Class<TR> resultClass);
//    <TR> SqlColumnAsSelector<T3,TR> getAutoSqlColumnAsSelector3(SqlBuilderSegment sqlSegment0Builder,Class<TR> resultClass);
    <TR> SqlColumnResultSelector<T3,TR> getSqlColumnResultSelector3(SqlBuilderSegment sqlSegment0Builder);
}
