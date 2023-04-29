package com.easy.query.core.expression.segment.condition.predicate;

import com.easy.query.core.basic.jdbc.parameter.SqlParameterCollector;
import com.easy.query.core.enums.SqlPredicateCompare;
import com.easy.query.core.expression.segment.SqlEntitySegment;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;

/**
 * @FileName: ColumnValuePredicate.java
 * @Description: 文件说明
 * @Date: 2023/2/14 23:34
 * @author xuejiaming
 */
public class ColumnWithColumnPredicate implements Predicate {
    private final EntityTableExpressionBuilder leftTable;
    private final String leftPropertyName;
    private final EntityTableExpressionBuilder rightTable;
    private final String rightPropertyName;
    private final SqlPredicateCompare compare;
    private final EntityExpressionBuilder sqlEntityExpression;

    public ColumnWithColumnPredicate(EntityTableExpressionBuilder leftTable, String leftPropertyName, EntityTableExpressionBuilder rightTable, String rightPropertyName, SqlPredicateCompare compare, EntityExpressionBuilder sqlEntityExpression) {
        this.leftTable = leftTable;
        this.leftPropertyName = leftPropertyName;
        this.rightTable = rightTable;
        this.rightPropertyName = rightPropertyName;
        this.compare = compare;
        this.sqlEntityExpression = sqlEntityExpression;
    }

    @Override
    public String toSql(SqlParameterCollector sqlParameterCollector) {
        String sqlColumnSegment1 = sqlEntityExpression.getSqlOwnerColumn(leftTable,leftPropertyName);
        String sqlColumnSegment2 = sqlEntityExpression.getSqlOwnerColumn(rightTable,rightPropertyName);
        return sqlColumnSegment1 +" "+ compare.getSql() + " "+sqlColumnSegment2;
    }

    @Override
    public EntityTableExpressionBuilder getTable() {
        return leftTable;
    }

    @Override
    public String getPropertyName() {
        return leftPropertyName;
    }

    @Override
    public SqlEntitySegment cloneSqlEntitySegment() {
        throw new UnsupportedOperationException();
    }

    @Override
    public SqlPredicateCompare getOperator() {
        return compare;
    }
}
