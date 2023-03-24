package com.easy.query.core.expression.segment.condition.predicate;

import com.easy.query.core.enums.SqlPredicateCompare;
import com.easy.query.core.expression.segment.SqlEntitySegment;
import com.easy.query.core.query.SqlEntityExpression;
import com.easy.query.core.query.SqlEntityTableExpression;

/**
 * @FileName: ColumnValuePredicate.java
 * @Description: 文件说明
 * @Date: 2023/2/14 23:34
 * @author xuejiaming
 */
public class ColumnWithColumnPredicate0 implements Predicate {
    private final SqlEntityTableExpression leftTable;
    private final String leftPropertyName;
    private final SqlEntityTableExpression rightTable;
    private final String rightPropertyName;
    private final SqlPredicateCompare compare;
    private final SqlEntityExpression sqlEntityExpression;

    public ColumnWithColumnPredicate0(SqlEntityTableExpression leftTable, String leftPropertyName, SqlEntityTableExpression rightTable, String rightPropertyName, SqlPredicateCompare compare, SqlEntityExpression sqlEntityExpression) {
        this.leftTable = leftTable;
        this.leftPropertyName = leftPropertyName;
        this.rightTable = rightTable;
        this.rightPropertyName = rightPropertyName;
        this.compare = compare;
        this.sqlEntityExpression = sqlEntityExpression;
    }

    @Override
    public String toSql() {
        String sqlColumnSegment1 = sqlEntityExpression.getSqlOwnerColumn(leftTable,leftPropertyName);
        String sqlColumnSegment2 = sqlEntityExpression.getSqlOwnerColumn(rightTable,rightPropertyName);
        return sqlColumnSegment1 +" "+ compare.getSql() + " "+sqlColumnSegment2;
    }

    @Override
    public SqlEntityTableExpression getTable() {
        return leftTable;
    }

    @Override
    public String getPropertyName() {
        return leftPropertyName;
    }

}
