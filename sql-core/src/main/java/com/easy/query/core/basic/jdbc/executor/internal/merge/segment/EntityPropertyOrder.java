package com.easy.query.core.basic.jdbc.executor.internal.merge.segment;

import com.easy.query.core.expression.parser.core.available.TableAvailable;

/**
 * create time 2023/4/27 12:57
 * 文件说明
 *
 * @author xuejiaming
 */
public class EntityPropertyOrder implements PropertyOrder {

    private final TableAvailable table;
    private final String propertyName;
    private final int columnIndex;
    private final boolean asc;

    public EntityPropertyOrder(TableAvailable table, String propertyName, int columnIndex, boolean asc){

        this.table = table;
        this.propertyName = propertyName;
        this.columnIndex = columnIndex;
        this.asc = asc;
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
    public boolean asc(){
        return asc;
    }
}
