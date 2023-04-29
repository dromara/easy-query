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
public class ColumnSegmentImpl implements ColumnSegment {


    protected final EntityTableExpressionBuilder table;


    protected final String propertyName;
    protected final EntityExpressionBuilder entityExpressionBuilder;
    protected String alias;

    public ColumnSegmentImpl(EntityTableExpressionBuilder table, String propertyName, EntityExpressionBuilder entityExpressionBuilder){
        this(table,propertyName,entityExpressionBuilder,null);
    }
    public ColumnSegmentImpl(EntityTableExpressionBuilder table, String propertyName, EntityExpressionBuilder entityExpressionBuilder, String alias){
        this.table = table;

        this.propertyName = propertyName;
        this.entityExpressionBuilder = entityExpressionBuilder;
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
        String sqlColumnSegment = entityExpressionBuilder.getSqlOwnerColumn(table,propertyName);
        StringBuilder sql = new StringBuilder();
        sql.append(sqlColumnSegment);
        if(alias!=null){
            sql.append(" AS ").append(entityExpressionBuilder.getQuoteName(alias));
        }
        return sql.toString();
    }

    @Override
    public ColumnSegment cloneSqlEntitySegment() {
        return new ColumnSegmentImpl(table,propertyName, entityExpressionBuilder,alias);
    }

    @Override
    public ColumnSegment cloneOrderSqlSegment() {
        return new OrderColumnSegment(table,propertyName,entityExpressionBuilder,true);
    }
}
