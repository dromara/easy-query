package org.easy.query.core.expression.segment.condition.predicate;

import org.easy.query.core.expression.context.SqlContext;
import org.easy.query.core.basic.jdbc.parameter.PropertySQLParameter;
import org.easy.query.core.expression.segment.SqlSegment;
import org.easy.query.core.query.builder.SqlTableInfo;

/**
 * @FileName: ColumnPropertyPredicate.java
 * @Description: column和某个bean的值的断言节点
 * @Date: 2023/2/13 15:18
 * @Created by xuejiaming
 */
public class ColumnPropertyPredicate implements SqlSegment {
    private final SqlTableInfo table;
    private final String propertyName;
    private final SqlContext sqlContext;

    public ColumnPropertyPredicate(SqlTableInfo table, String propertyName, SqlContext sqlContext){
        this.table = table;
        this.propertyName = propertyName;
        this.sqlContext = sqlContext;
    }

    @Override
    public String toSql() {
        sqlContext.addParameter(new PropertySQLParameter(table,propertyName));
        String sqlColumnSegment = sqlContext.getSqlColumnSegment(table,propertyName);
        return sqlColumnSegment + " = ?";
    }

    public String getPropertyName() {
        return propertyName;
    }

}
