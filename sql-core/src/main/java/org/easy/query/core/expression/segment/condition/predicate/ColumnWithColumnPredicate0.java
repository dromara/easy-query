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
public class ColumnWithColumnPredicate0 implements Predicate {
    private final SqlEntityTableExpressionSegment leftTable;
    private final String leftPropertyName;
    private final SqlEntityTableExpressionSegment rightTable;
    private final String rightPropertyName;
    private final SqlPredicateCompare compare;
    private final SqlEntityExpressionSegment sqlEntityExpressionSegment;

    public ColumnWithColumnPredicate0(SqlEntityTableExpressionSegment leftTable, String leftPropertyName, SqlEntityTableExpressionSegment rightTable, String rightPropertyName, SqlPredicateCompare compare,SqlEntityExpressionSegment sqlEntityExpressionSegment) {
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
