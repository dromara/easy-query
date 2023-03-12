package com.easy.query.core.expression.segment.condition.predicate;

import com.easy.query.core.basic.jdbc.parameter.PropertySQLParameter;
import com.easy.query.core.expression.segment.SqlEntitySegment;
import com.easy.query.core.query.SqlEntityExpression;
import com.easy.query.core.query.SqlEntityTableExpression;

/**
 * @FileName: ColumnPropertyPredicate.java
 * @Description: column和某个bean的值的断言节点
 * @Date: 2023/2/13 15:18
 * @Created by xuejiaming
 */
public class ColumnPropertyPredicate implements SqlEntitySegment {
    private final SqlEntityTableExpression table;
    private final String propertyName;
    private final SqlEntityExpression sqlEntityExpression;

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

    @Override
    public String getAlias() {
        return null;
    }

}
