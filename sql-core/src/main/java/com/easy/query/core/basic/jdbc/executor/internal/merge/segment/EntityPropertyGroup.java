package com.easy.query.core.basic.jdbc.executor.internal.merge.segment;

import com.easy.query.core.expression.sql.expression.TableSQLExpression;

/**
 * create time 2023/4/28 16:28
 * 文件说明
 *
 * @author xuejiaming
 */
public class EntityPropertyGroup implements PropertyGroup{

    private final TableSQLExpression tableSQLExpression;
    private final String propertyName;
    private final int columnIndex;

    public EntityPropertyGroup(TableSQLExpression tableSQLExpression, String propertyName, int columnIndex){

        this.tableSQLExpression = tableSQLExpression;
        this.propertyName = propertyName;
        this.columnIndex = columnIndex;
    }
    public TableSQLExpression getTable(){
        return tableSQLExpression;
    }
    public String propertyName(){
        return propertyName;
    }
    public int columnIndex(){
        return columnIndex;
    }
}
