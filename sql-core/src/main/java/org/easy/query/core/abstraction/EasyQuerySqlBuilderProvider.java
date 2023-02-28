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
public interface EasyQuerySqlBuilderProvider<T1> {
    SqlColumnSelector<T1> getSqlGroupColumnSelector1();
    SqlColumnSelector<T1> getSqlOrderColumnSelector1(boolean asc);
    SqlPredicate<T1> getSqlWherePredicate1();
    SqlAggregatePredicate<T1> getSqlAggregatePredicate1();
    SqlPredicate<T1> getSqlOnPredicate1();

    SqlColumnSelector<T1> getSqlColumnSelector1(SqlSegmentBuilder sqlSegment0Builder);
    <TR> SqlColumnAsSelector<T1,TR> getSqlColumnAsSelector1(SqlSegmentBuilder sqlSegment0Builder);
}
