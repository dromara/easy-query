package org.easy.query.core.basic.expression.parser.impl;

import org.easy.query.core.basic.sql.segment.builder.SqlSegmentBuilder;
import org.easy.query.core.basic.expression.lambda.Property;
import org.easy.query.core.abstraction.metadata.ColumnMetadata;
import org.easy.query.core.basic.expression.parser.abstraction.SqlColumnSelector;
import org.easy.query.core.impl.SqlContext;
import org.easy.query.core.query.builder.SqlTableInfo;
import org.easy.query.core.basic.sql.segment.segment.UpdateColumnSegment;

import java.util.Collection;

/**
 * @FileName: DefaultSqlColumnSetSelector.java
 * @Description: 文件说明
 * @Date: 2023/2/25 21:31
 * @Created by xuejiaming
 */
public class DefaultSqlColumnSetSelector<T> implements SqlColumnSelector<T> {
    private final int index;
    private final SqlContext sqlContext;
    private final SqlSegmentBuilder sqlSegmentBuilder;

    public DefaultSqlColumnSetSelector(int index, SqlContext sqlContext, SqlSegmentBuilder sqlSegmentBuilder){

        this.index = index;
        this.sqlContext = sqlContext;
        this.sqlSegmentBuilder = sqlSegmentBuilder;
    }
    @Override
    public SqlColumnSelector<T> column(Property<T, ?> column) {
        SqlTableInfo table = sqlContext.getTable(index);
        String propertyName = table.getPropertyName(column);
        String columnName = table.getColumnName(propertyName);
        sqlSegmentBuilder.append(new UpdateColumnSegment(index,columnName,propertyName,sqlContext));
        return this;
    }

    @Override
    public SqlColumnSelector<T> columnAll() {
        SqlTableInfo table = sqlContext.getTable(index);
        Collection<ColumnMetadata> columns = table.getEntityMetadata().getColumns();
        for (ColumnMetadata column : columns) {
            String propertyName = column.getProperty().getName();
            sqlSegmentBuilder.append(new UpdateColumnSegment(index, column.getName(),propertyName,sqlContext));
        }
        return this;
    }

    @Override
    public int getIndex() {
        return index;
    }
}
