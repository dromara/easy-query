package org.easy.query.core.abstraction;

import org.easy.query.core.abstraction.sql.base.SqlColumnAsSelector;
import org.easy.query.core.abstraction.sql.base.SqlColumnSelector;
import org.easy.query.core.abstraction.sql.base.SqlPredicate;

/**
 * @FileName: EasyQueryLambdaBuilder.java
 * @Description: 文件说明
 * @Date: 2023/2/12 10:02
 * @Created by xuejiaming
 */
public interface EasyQuerySqlBuilderProvider<T1> {
    SqlColumnSelector<T1> getSqlColumnSelector1(SqlSegment sqlSegment);
    SqlPredicate<T1> getSqlPredicate1(SqlSegment sqlSegment);
    <TR> SqlColumnAsSelector<T1, TR>getSqlColumnAsSelector1();
}
