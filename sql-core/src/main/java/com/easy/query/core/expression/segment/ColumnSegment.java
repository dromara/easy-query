package com.easy.query.core.expression.segment;

import com.easy.query.core.basic.jdbc.parameter.SqlParameterCollector;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;

/**
 * @FileName: ColumnSegment.java
 * @Description: 文件说明
 * @Date: 2023/2/13 15:18
 * @author xuejiaming
 */
public class ColumnSegment implements SqlEntityAliasSegment {


    protected final EntityTableExpressionBuilder table;


    protected final String propertyName;
    protected final EntityExpressionBuilder sqlEntityExpression;
    protected String alias;

    public ColumnSegment(EntityTableExpressionBuilder table, String propertyName, EntityExpressionBuilder sqlEntityExpression){
        this(table,propertyName,sqlEntityExpression,null);
    }
    public ColumnSegment(EntityTableExpressionBuilder table, String propertyName, EntityExpressionBuilder sqlEntityExpression, String alias){
        this.table = table;

        this.propertyName = propertyName;
        this.sqlEntityExpression = sqlEntityExpression;
        this.alias = alias;
    }

    @Override
    public EntityTableExpressionBuilder getTable() {
        return table;
    }

    @Override
    public String getPropertyName() {
        return propertyName;
    }

    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public String toSql(SqlParameterCollector sqlParameterCollector) {
        String sqlColumnSegment = sqlEntityExpression.getSqlOwnerColumn(table,propertyName);
        StringBuilder sql = new StringBuilder();
        sql.append(sqlColumnSegment);
        if(alias!=null){
            sql.append(" AS ").append(sqlEntityExpression.getQuoteName(alias));
        }
        return sql.toString();
    }
}
