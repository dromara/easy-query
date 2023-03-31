package com.easy.query.core.expression.segment;

import com.easy.query.core.expression.sql.EntityExpression;
import com.easy.query.core.expression.sql.EntityTableExpression;

/**
 * @FileName: ColumnSegment.java
 * @Description: 文件说明
 * @Date: 2023/2/13 15:18
 * @author xuejiaming
 */
public class ColumnSegment implements SqlEntityAliasSegment {


    protected final EntityTableExpression table;


    protected final String propertyName;
    protected final EntityExpression sqlEntityExpression;
    protected String alias;

    public ColumnSegment(EntityTableExpression table, String propertyName, EntityExpression sqlEntityExpression){
        this(table,propertyName,sqlEntityExpression,null);
    }
    public ColumnSegment(EntityTableExpression table, String propertyName, EntityExpression sqlEntityExpression, String alias){
        this.table = table;

        this.propertyName = propertyName;
        this.sqlEntityExpression = sqlEntityExpression;
        this.alias = alias;
    }

    @Override
    public EntityTableExpression getTable() {
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
    public String toSql() {
        String sqlColumnSegment = sqlEntityExpression.getSqlOwnerColumn(table,propertyName);
        StringBuilder sql = new StringBuilder();
        sql.append(sqlColumnSegment);
        if(alias!=null){
            sql.append(" AS ").append(sqlEntityExpression.getQuoteName(alias));
        }
        return sql.toString();
    }
}
