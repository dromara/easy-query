package org.easy.query.core.expression.segment.condition.predicate;

import org.easy.query.core.enums.SqlPredicateCompare;
import org.easy.query.core.query.SqlEntityExpression;
import org.easy.query.core.query.SqlEntityQueryExpression;
import org.easy.query.core.query.SqlEntityTableExpression;

/**
 * @FileName: ColumnValuePredicate.java
 * @Description: 文件说明
 * @Date: 2023/2/14 23:34
 * @Created by xuejiaming
 */
public class ColumnPredicate0 implements Predicate {
    private final SqlEntityTableExpression table;
    private final String propertyName;
    private final SqlPredicateCompare compare;
    private final SqlEntityExpression sqlEntityExpression;

    public ColumnPredicate0(SqlEntityTableExpression table, String propertyName, SqlPredicateCompare compare, SqlEntityExpression sqlEntityExpression) {
        this.table = table;
        this.propertyName = propertyName;
        this.compare = compare;
        this.sqlEntityExpression = sqlEntityExpression;
    }

    @Override
    public String toSql() {
        String sqlColumnSegment = sqlEntityExpression.getSqlOwnerColumn(table,propertyName);
        return sqlColumnSegment +" "+ compare.getSql();
    }
}
