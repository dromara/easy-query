package com.easy.query.core.basic.jdbc.executor.internal.merge.segment;

import com.easy.query.core.expression.sql.expression.EasyTableSqlExpression;

/**
 * create time 2023/4/27 12:57
 * 文件说明
 *
 * @author xuejiaming
 */
public class EntityPropertyOrder implements PropertyOrder {

    private final EasyTableSqlExpression tableSqlExpression;
    private final String propertyName;
    private final int columnIndex;
    private final boolean asc;

    public EntityPropertyOrder(EasyTableSqlExpression tableSqlExpression,String propertyName,int columnIndex,boolean asc){

        this.tableSqlExpression = tableSqlExpression;
        this.propertyName = propertyName;
        this.columnIndex = columnIndex;
        this.asc = asc;
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
    public boolean asc(){
        return asc;
    }
}
