package org.easy.query.core.impl.lambda;

import org.easy.query.core.abstraction.SqlSegment0Builder;
import org.easy.query.core.abstraction.lambda.Property;
import org.easy.query.core.abstraction.metadata.ColumnMetadata;
import org.easy.query.core.impl.SelectContext;
import org.easy.query.core.query.builder.SelectTableInfo;
import org.easy.query.core.abstraction.sql.base.ColumnSelector;
import org.easy.query.core.segments.ColumnSegment;
import org.easy.query.core.util.LambdaUtil;

import java.util.Collection;

/**
 * @FileName: DefaultSqlColumnSelector.java
 * @Description: 文件说明
 * @Date: 2023/2/8 12:26
 * @Created by xuejiaming
 */
public class AbstractSqlColumnSelector<T1,TChain> implements ColumnSelector<T1, TChain> {
    private final int index;
    private final SelectContext selectContext;
    private final SqlSegment0Builder sqlSegmentBuilder;

    public AbstractSqlColumnSelector(int index, SelectContext selectContext, SqlSegment0Builder sqlSegmentBuilder){
        this.index = index;

        this.selectContext = selectContext;
        this.sqlSegmentBuilder = sqlSegmentBuilder;
    }
    @Override
    public  int getIndex(){
        return this.index;
    }

    @Override
    public TChain column(Property<T1, ?> column) {
        String columnName = selectContext.getTable(index).getColumnName(column);
        sqlSegmentBuilder.append(new ColumnSegment(index,columnName,selectContext));
        return (TChain)this;
    }

    @Override
    public TChain columnAll() {
        SelectTableInfo table = selectContext.getTable(index);
        Collection<ColumnMetadata> columns = table.getEntityMetadata().getColumns();
        for (ColumnMetadata column : columns) {
            sqlSegmentBuilder.append(new ColumnSegment(index, column.getName(),selectContext));
        }
        return (TChain)this;
    }


    public SelectContext getSelectContext() {
        return selectContext;
    }
}
