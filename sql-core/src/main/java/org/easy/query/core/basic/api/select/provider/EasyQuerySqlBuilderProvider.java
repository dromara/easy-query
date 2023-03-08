package org.easy.query.core.basic.api.select.provider;

import org.easy.query.core.expression.parser.abstraction.SqlAggregatePredicate;
import org.easy.query.core.expression.parser.abstraction.SqlColumnAsSelector;
import org.easy.query.core.expression.parser.abstraction.SqlColumnSelector;
import org.easy.query.core.expression.parser.abstraction.SqlPredicate;
import org.easy.query.core.expression.parser.abstraction.SqlColumnResultSelector;
import org.easy.query.core.expression.segment.builder.SqlBuilderSegment;

/**
 * @FileName: EasyQueryLambdaBuilder.java
 * @Description: 文件说明
 * @Date: 2023/2/12 10:02
 * @Created by xuejiaming
 */
public interface EasyQuerySqlBuilderProvider<T1> {
    SqlColumnSelector<T1> getSqlGroupColumnSelector1();
    SqlColumnSelector<T1> getSqlOrderColumnSelector1(boolean asc);
    SqlPredicate<T1> getSqlWherePredicate1();
    SqlAggregatePredicate<T1> getSqlAggregatePredicate1();
    SqlPredicate<T1> getSqlOnPredicate1();

    SqlColumnSelector<T1> getSqlColumnSelector1(SqlBuilderSegment sqlSegment0Builder);
    <TR> SqlColumnAsSelector<T1,TR> getSqlColumnAsSelector1(SqlBuilderSegment sqlSegment0Builder);
    <TR> SqlColumnResultSelector<T1,TR> getSqlColumnResultSelector1(SqlBuilderSegment sqlSegment0Builder);
}
