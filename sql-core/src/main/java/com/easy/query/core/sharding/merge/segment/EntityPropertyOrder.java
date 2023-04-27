package com.easy.query.core.sharding.merge.segment;

import com.easy.query.core.expression.sql.expression.EasyTableSqlExpression;
import com.easy.query.core.metadata.ColumnMetadata;

/**
 * create time 2023/4/27 12:57
 * 文件说明
 *
 * @author xuejiaming
 */
public class EntityPropertyOrder implements PropertyOrder {

    private final EasyTableSqlExpression tableSqlExpression;
    private final ColumnMetadata columnMetadata;
    private final String propertyName;
    private final int columnIndex;
    private final boolean asc;

    public EntityPropertyOrder(EasyTableSqlExpression tableSqlExpression,String propertyName,int columnIndex,boolean asc){

        this.tableSqlExpression = tableSqlExpression;
        this.propertyName = propertyName;
        this.columnIndex = columnIndex;
        this.asc = asc;
        this.columnMetadata = tableSqlExpression.getEntityMetadata().getColumnNotNull(propertyName);
    }
    public EasyTableSqlExpression getTable(){
        return tableSqlExpression;
    }
    public String propertyName(){
        return propertyName;
    }
    public Class<?> propertyType(){
        return columnMetadata.getProperty().getPropertyType();
    }
    public int columnIndex(){
        return columnIndex+1;
    }
    public boolean asc(){
        return asc;
    }
}
