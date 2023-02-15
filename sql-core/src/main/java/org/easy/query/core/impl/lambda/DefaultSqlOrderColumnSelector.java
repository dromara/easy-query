package org.easy.query.core.impl.lambda;

import org.easy.query.core.abstraction.lambda.Property;
import org.easy.query.core.abstraction.metadata.ColumnMetadata;
import org.easy.query.core.abstraction.sql.base.SqlColumnSelector;
import org.easy.query.core.impl.SelectContext;
import org.easy.query.core.query.builder.SelectTableInfo;
import org.easy.query.core.segments.OrderColumnSegment;

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
        String columnName = selectContext.getTable(index).getColumnName(column);
        selectContext.getOrder().append(new OrderColumnSegment(index,columnName,selectContext,asc));
        return this;
    }

    @Override
    public SqlColumnSelector<T1> columnAll() {
        SelectTableInfo table = selectContext.getTable(index);
        Collection<ColumnMetadata> columns = table.getEntityMetadata().getColumns();
        for (ColumnMetadata column : columns) {
            selectContext.getOrder().append(new OrderColumnSegment(index, column.getName(),selectContext,asc));
        }
        return this;
    }


    public void setAsc(boolean asc) {
        this.asc = asc;
    }

}
