package org.easy.query.core.abstraction;

import org.easy.query.core.expression.parser.abstraction.SqlAggregatePredicate;
import org.easy.query.core.expression.parser.abstraction.SqlColumnAsSelector;
import org.easy.query.core.expression.parser.abstraction.SqlColumnSelector;
import org.easy.query.core.expression.parser.abstraction.SqlPredicate;
import org.easy.query.core.expression.builder.SqlSegmentBuilder;

/**
 * @FileName: EasyQueryLambdaBuilder.java
 * @Description: 文件说明
 * @Date: 2023/2/12 10:02
 * @Created by xuejiaming
 */
public interface EasyQuerySqlBuilderProvider2<T1,T2> extends EasyQuerySqlBuilderProvider<T1> {
    SqlColumnSelector<T2> getSqlGroupColumnSelector2();
    SqlColumnSelector<T2> getSqlOrderColumnSelector2(boolean asc);
    SqlPredicate<T2> getSqlWherePredicate2();
    SqlAggregatePredicate<T2> getSqlAggregatePredicate2();
    SqlPredicate<T2> getSqlOnPredicate2();

    SqlColumnSelector<T2> getSqlColumnSelector2(SqlSegmentBuilder sqlSegment0Builder);
    <TR> SqlColumnAsSelector<T2,TR> getSqlColumnAsSelector2(SqlSegmentBuilder sqlSegment0Builder);
}
