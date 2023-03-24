package com.easy.query.core.expression.segment.condition.predicate;

import com.easy.query.core.basic.jdbc.parameter.ConstSQLParameter;
import com.easy.query.core.enums.SqlPredicateCompare;
import com.easy.query.core.expression.segment.SqlEntitySegment;
import com.easy.query.core.query.SqlEntityExpression;
import com.easy.query.core.query.SqlEntityTableExpression;

/**
 * @FileName: ColumnValuePredicate.java
 * @Description: colum和具体值的断言
 * @Date: 2023/2/14 23:34
 * @author xuejiaming
 */
public class ColumnValuePredicate0 implements Predicate {
    private final SqlEntityTableExpression table;
    private final String propertyName;
    private final Object val;
    private final SqlPredicateCompare compare;
    private final SqlEntityExpression sqlEntityExpression;

    public ColumnValuePredicate0(SqlEntityTableExpression table, String propertyName, Object val, SqlPredicateCompare compare, SqlEntityExpression sqlEntityExpression) {
        this.table = table;
        this.propertyName = propertyName;
        this.val = val;
        this.compare = compare;
        this.sqlEntityExpression = sqlEntityExpression;
    }

    @Override
    public String toSql() {
        sqlEntityExpression.addParameter(new ConstSQLParameter(table,propertyName,val));
        String sqlColumnSegment = sqlEntityExpression.getSqlOwnerColumn(table,propertyName);
        return sqlColumnSegment + " " + compare.getSql() + " ?";
    }

    @Override
    public SqlEntityTableExpression getTable() {
        return table;
    }

    @Override
    public String getPropertyName() {
        return propertyName;
    }

}
