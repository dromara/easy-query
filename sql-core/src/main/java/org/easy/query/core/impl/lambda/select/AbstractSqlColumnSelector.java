package org.easy.query.core.impl.lambda.select;

import org.easy.query.core.abstraction.SqlSegment0Builder;
import org.easy.query.core.abstraction.lambda.Property;
import org.easy.query.core.abstraction.metadata.ColumnMetadata;
import org.easy.query.core.impl.SqlContext;
import org.easy.query.core.query.builder.SqlTableInfo;
import org.easy.query.core.abstraction.sql.base.ColumnSelector;
import org.easy.query.core.segments.ColumnSegment;

import java.util.Collection;

/**
 * @FileName: DefaultSqlColumnSelector.java
 * @Description: 文件说明
 * @Date: 2023/2/8 12:26
 * @Created by xuejiaming
 */
public class AbstractSqlColumnSelector<T1,TChain> implements ColumnSelector<T1, TChain> {
    private final int index;
    private final SqlContext sqlContext;
    private final SqlSegment0Builder sqlSegmentBuilder;

    public AbstractSqlColumnSelector(int index, SqlContext sqlContext, SqlSegment0Builder sqlSegmentBuilder){
        this.index = index;

        this.sqlContext = sqlContext;
        this.sqlSegmentBuilder = sqlSegmentBuilder;
    }
    @Override
    public  int getIndex(){
        return this.index;
    }

    @Override
    public TChain column(Property<T1, ?> column) {
        SqlTableInfo table = sqlContext.getTable(index);
        String propertyName = table.getPropertyName(column);
        String columnName = table.getColumnName(propertyName);
        sqlSegmentBuilder.append(new ColumnSegment(index,columnName,propertyName,sqlContext));
        return (TChain)this;
    }

    @Override
    public TChain columnAll() {
        SqlTableInfo table = sqlContext.getTable(index);
        Collection<ColumnMetadata> columns = table.getEntityMetadata().getColumns();
        for (ColumnMetadata column : columns) {
            String propertyName = column.getProperty().getName();
            sqlSegmentBuilder.append(new ColumnSegment(index, column.getName(),propertyName,sqlContext));
        }
        return (TChain)this;
    }


    public SqlContext getSqlContext() {
        return sqlContext;
    }
}
