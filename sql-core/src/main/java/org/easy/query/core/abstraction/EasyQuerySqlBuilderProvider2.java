package org.easy.query.core.abstraction;

import org.easy.query.core.abstraction.sql.base.SqlAggregatePredicate;
import org.easy.query.core.abstraction.sql.base.SqlColumnAsSelector;
import org.easy.query.core.abstraction.sql.base.SqlColumnSelector;
import org.easy.query.core.abstraction.sql.base.SqlPredicate;

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

    SqlColumnSelector<T2> getSqlColumnSelector2(SqlSegment0Builder sqlSegment0Builder);
    <TR> SqlColumnAsSelector<T2,TR> getSqlColumnAsSelector2(SqlSegment0Builder sqlSegment0Builder);
}
