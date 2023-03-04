package org.easy.query.core.expression.segment.condition.predicate;

import org.easy.query.core.enums.SqlPredicateCompare;
import org.easy.query.core.query.SqlEntityExpression;
import org.easy.query.core.query.SqlEntityTableExpression;

/**
 * @FileName: ColumnValuePredicate.java
 * @Description: 文件说明
 * @Date: 2023/2/14 23:34
 * @Created by xuejiaming
 */
public class ColumnWithColumnPredicate0 implements Predicate {
    private final SqlEntityTableExpression leftTable;
    private final String leftPropertyName;
    private final SqlEntityTableExpression rightTable;
    private final String rightPropertyName;
    private final SqlPredicateCompare compare;
    private final SqlEntityExpression sqlEntityExpressionSegment;

    public ColumnWithColumnPredicate0(SqlEntityTableExpression leftTable, String leftPropertyName, SqlEntityTableExpression rightTable, String rightPropertyName, SqlPredicateCompare compare, SqlEntityExpression sqlEntityExpressionSegment) {
        this.leftTable = leftTable;
        this.leftPropertyName = leftPropertyName;
        this.rightTable = rightTable;
        this.rightPropertyName = rightPropertyName;
        this.compare = compare;
        this.sqlEntityExpressionSegment = sqlEntityExpressionSegment;
    }

    @Override
    public String toSql() {
        String sqlColumnSegment1 = sqlEntityExpressionSegment.getSqlColumnSegment(leftTable,leftPropertyName);
        String sqlColumnSegment2 = sqlEntityExpressionSegment.getSqlColumnSegment(rightTable,rightPropertyName);
        return sqlColumnSegment1 +" "+ compare.getSql() + " "+sqlColumnSegment2;
    }
}
