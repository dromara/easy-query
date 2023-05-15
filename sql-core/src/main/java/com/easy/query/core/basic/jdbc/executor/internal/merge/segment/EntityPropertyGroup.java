package com.easy.query.core.basic.jdbc.executor.internal.merge.segment;

import com.easy.query.core.expression.sql.expression.EasyTableSqlExpression;

/**
 * create time 2023/4/28 16:28
 * 文件说明
 *
 * @author xuejiaming
 */
public class EntityPropertyGroup implements PropertyGroup{

    private final EasyTableSqlExpression tableSqlExpression;
    private final String propertyName;
    private final int columnIndex;

    public EntityPropertyGroup(EasyTableSqlExpression tableSqlExpression,String propertyName,int columnIndex){

        this.tableSqlExpression = tableSqlExpression;
        this.propertyName = propertyName;
        this.columnIndex = columnIndex;
    }
    public EasyTableSqlExpression getTable(){
        return tableSqlExpression;
    }
    public String propertyName(){
        return propertyName;
    }
    public int columnIndex(){
        return columnIndex;
    }
}
