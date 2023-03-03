package org.easy.query.core.expression.segment.condition.predicate;

import org.easy.query.core.expression.context.SqlContext;
import org.easy.query.core.enums.SqlPredicateCompare;
import org.easy.query.core.query.SqlEntityExpressionSegment;
import org.easy.query.core.query.SqlEntityTableExpressionSegment;
import org.easy.query.core.query.builder.SqlTableInfo;

/**
 * @FileName: ColumnValuePredicate.java
 * @Description: 文件说明
 * @Date: 2023/2/14 23:34
 * @Created by xuejiaming
 */
public class ColumnPredicate0 implements Predicate {
    private final SqlEntityTableExpressionSegment table;
    private final String propertyName;
    private final SqlPredicateCompare compare;
    private final SqlEntityExpressionSegment sqlEntityExpressionSegment;

    public ColumnPredicate0(SqlEntityTableExpressionSegment table, String propertyName, SqlPredicateCompare compare,SqlEntityExpressionSegment sqlEntityExpressionSegment) {
        this.table = table;
        this.propertyName = propertyName;
        this.compare = compare;
        this.sqlEntityExpressionSegment = sqlEntityExpressionSegment;
    }

    @Override
    public String toSql() {
        String sqlColumnSegment =sqlEntityExpressionSegment.getSqlColumnSegment(table,propertyName);
        return sqlColumnSegment +" "+ compare.getSql();
    }
}
