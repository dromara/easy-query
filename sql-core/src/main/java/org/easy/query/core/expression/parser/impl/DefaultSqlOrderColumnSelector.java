package org.easy.query.core.expression.parser.impl;

import org.easy.query.core.expression.lambda.Property;
import org.easy.query.core.abstraction.metadata.ColumnMetadata;
import org.easy.query.core.expression.parser.abstraction.SqlColumnSelector;
import org.easy.query.core.basic.api.context.SelectContext;
import org.easy.query.core.query.builder.SqlTableInfo;
import org.easy.query.core.basic.sql.segment.segment.OrderColumnSegment;

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
        String columnName = table.getColumnName(propertyName);
        selectContext.getOrder().append(new OrderColumnSegment(index,columnName,propertyName,selectContext,asc));
        return this;
    }

    @Override
    public SqlColumnSelector<T1> columnAll() {
        SqlTableInfo table = selectContext.getTable(index);
        Collection<ColumnMetadata> columns = table.getEntityMetadata().getColumns();
        for (ColumnMetadata column : columns) {
            selectContext.getOrder().append(new OrderColumnSegment(index, column.getName(),column.getProperty().getName(),selectContext,asc));
        }
        return this;
    }


    public void setAsc(boolean asc) {
        this.asc = asc;
    }

}
