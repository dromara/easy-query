package org.easy.query.core.expression.segment.condition.predicate;

import org.easy.query.core.expression.context.SqlContext;
import org.easy.query.core.basic.jdbc.parameter.PropertySQLParameter;
import org.easy.query.core.expression.segment.SqlSegment;
import org.easy.query.core.query.SqlEntityExpression;
import org.easy.query.core.query.SqlEntityTableExpression;
import org.easy.query.core.query.builder.SqlTableInfo;

/**
 * @FileName: ColumnPropertyPredicate.java
 * @Description: column和某个bean的值的断言节点
 * @Date: 2023/2/13 15:18
 * @Created by xuejiaming
 */
public class ColumnPropertyPredicate implements SqlSegment {
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
        String sqlColumnSegment = sqlEntityExpression.getSqlColumnSegment(table,propertyName);
        return sqlColumnSegment + " = ?";
    }

    public String getPropertyName() {
        return propertyName;
    }

}
