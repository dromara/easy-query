package com.easy.query.core.basic.api.select.provider;

import com.easy.query.core.expression.parser.core.SqlWherePredicate;
import com.easy.query.core.expression.parser.core.SqlGroupBySelector;
import com.easy.query.core.expression.parser.core.SqlColumnSelector;
import com.easy.query.core.expression.parser.core.SqlColumnAsSelector;
import com.easy.query.core.expression.parser.core.SqlAggregatePredicate;
import com.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import com.easy.query.core.expression.parser.core.SqlColumnResultSelector;

/**
 * @FileName: EasyQueryLambdaBuilder.java
 * @Description: 文件说明
 * @Date: 2023/2/12 10:02
 * @author xuejiaming
 */
public interface EasyQuerySqlBuilderProvider<T1> {
    SqlGroupBySelector<T1> getSqlGroupColumnSelector1();
    SqlColumnSelector<T1> getSqlOrderColumnSelector1(boolean asc);
    SqlWherePredicate<T1> getSqlWherePredicate1();
    SqlAggregatePredicate<T1> getSqlAggregatePredicate1();
    SqlWherePredicate<T1> getSqlOnPredicate1();

    SqlColumnSelector<T1> getSqlColumnSelector1(SqlBuilderSegment sqlSegment0Builder);
    <TR> SqlColumnAsSelector<T1,TR> getSqlColumnAsSelector1(SqlBuilderSegment sqlSegment0Builder, Class<TR> resultClass);
    <TR> SqlColumnAsSelector<T1,TR> getSqlAutoColumnAsSelector1(SqlBuilderSegment sqlSegment0Builder, Class<TR> resultClass);
    <TR> SqlColumnResultSelector<T1,TR> getSqlColumnResultSelector1(SqlBuilderSegment sqlSegment0Builder);
}
