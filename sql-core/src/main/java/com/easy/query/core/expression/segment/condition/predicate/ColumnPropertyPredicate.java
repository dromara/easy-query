package com.easy.query.core.expression.segment.condition.predicate;

import com.easy.query.core.basic.jdbc.parameter.PropertySQLParameter;
import com.easy.query.core.expression.sql.SqlEntityExpression;
import com.easy.query.core.expression.sql.SqlEntityTableExpression;

/**
 * @FileName: ColumnPropertyPredicate.java
 * @Description: column和某个bean的值的断言节点
 * @Date: 2023/2/13 15:18
 * @author xuejiaming
 */
public class ColumnPropertyPredicate implements Predicate {
    protected final SqlEntityTableExpression table;
    protected final String propertyName;
    protected final SqlEntityExpression sqlEntityExpression;

    public ColumnPropertyPredicate(SqlEntityTableExpression table, String propertyName, SqlEntityExpression sqlEntityExpression){
        this.table = table;
        this.propertyName = propertyName;
        this.sqlEntityExpression = sqlEntityExpression;
    }

    @Override
    public String toSql() {
        sqlEntityExpression.addParameter(new PropertySQLParameter(table,propertyName));
        String sqlColumnSegment = sqlEntityExpression.getSqlOwnerColumn(table,propertyName);
        return sqlColumnSegment + " = ?";
    }

    @Override
    public SqlEntityTableExpression getTable() {
        return table;
    }

    public String getPropertyName() {
        return propertyName;
    }


}
