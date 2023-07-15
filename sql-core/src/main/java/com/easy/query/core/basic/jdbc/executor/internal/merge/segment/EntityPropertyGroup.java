package com.easy.query.core.basic.jdbc.executor.internal.merge.segment;

import com.easy.query.core.expression.parser.core.available.TableAvailable;

/**
 * create time 2023/4/28 16:28
 * 文件说明
 *
 * @author xuejiaming
 */
public class EntityPropertyGroup implements PropertyGroup{

    private final TableAvailable table;
    private final String propertyName;
    private final int columnIndex;

    public EntityPropertyGroup(TableAvailable table, String propertyName, int columnIndex){

        this.table = table;
        this.propertyName = propertyName;
        this.columnIndex = columnIndex;
    }
    public TableAvailable getTable(){
        return table;
    }
    public String propertyName(){
        return propertyName;
    }
    public int columnIndex(){
        return columnIndex;
    }
}
