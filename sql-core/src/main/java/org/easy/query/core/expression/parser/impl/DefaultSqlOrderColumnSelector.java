package org.easy.query.core.expression.parser.impl;

import org.easy.query.core.expression.lambda.Property;
import org.easy.query.core.expression.parser.abstraction.SqlColumnSelector;
import org.easy.query.core.expression.context.SelectContext;
import org.easy.query.core.query.builder.SqlTableInfo;
import org.easy.query.core.expression.segment.OrderColumnSegment;

import java.util.Collection;

/**
 * @FileName: DefaultSqlColumnSelector.java
 * @Description: 文件说明
 * @Date: 2023/2/8 12:26
 * @Created by xuejiaming
 */
public  class DefaultSqlOrderColumnSelector<T1> implements SqlColumnSelector<T1> {
    private final int index;
    private final SelectContext selectContext;
    private boolean asc;

    public DefaultSqlOrderColumnSelector(int index, SelectContext selectContext){
        this.index = index;
        this.selectContext = selectContext;
    }
    @Override
    public  int getIndex(){
        return this.index;
    }

    @Override
    public SqlColumnSelector<T1> column(Property<T1, ?> column) {
        SqlTableInfo table = selectContext.getTable(index);
        String propertyName = table.getPropertyName(column);
        selectContext.getOrder().append(new OrderColumnSegment(table,propertyName,selectContext,asc));
        return this;
    }

    @Override
    public SqlColumnSelector<T1> columnAll() {
        SqlTableInfo table = selectContext.getTable(index);
        Collection<String> properties = table.getEntityMetadata().getProperties();
        for (String property : properties) {
            selectContext.getOrder().append(new OrderColumnSegment(table, property,selectContext,asc));
        }
        return this;
    }


    public void setAsc(boolean asc) {
        this.asc = asc;
    }

}
