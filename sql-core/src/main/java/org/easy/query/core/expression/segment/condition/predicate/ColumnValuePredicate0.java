package org.easy.query.core.expression.segment.condition.predicate;

import org.easy.query.core.basic.jdbc.parameter.ConstSQLParameter;
import org.easy.query.core.enums.SqlPredicateCompare;
import org.easy.query.core.query.SqlEntityExpression;
import org.easy.query.core.query.SqlEntityQueryExpression;
import org.easy.query.core.query.SqlEntityTableExpression;

/**
 * @FileName: ColumnValuePredicate.java
 * @Description: colum和具体值的断言
 * @Date: 2023/2/14 23:34
 * @Created by xuejiaming
 */
public class ColumnValuePredicate0 implements Predicate {
    private final SqlEntityTableExpression table;
    private final String propertyName;
    private final SqlPredicateCompare compare;
    private final SqlEntityExpression sqlEntityExpression;

    public ColumnValuePredicate0(SqlEntityTableExpression table, String propertyName, Object val, SqlPredicateCompare compare, SqlEntityExpression sqlEntityExpression) {
        this.table = table;
        this.propertyName = propertyName;
        this.compare = compare;
        this.sqlEntityExpression = sqlEntityExpression;
        sqlEntityExpression.addParameter(new ConstSQLParameter(table,propertyName,val));
    }

    @Override
    public String toSql() {
        String sqlColumnSegment = sqlEntityExpression.getSqlOwnerColumn(table,propertyName);
        return sqlColumnSegment + " " + compare.getSql() + " ?";
    }
}
