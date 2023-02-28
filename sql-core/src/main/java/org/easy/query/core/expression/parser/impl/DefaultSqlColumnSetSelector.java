package org.easy.query.core.expression.parser.impl;

import org.easy.query.core.basic.api.context.SqlColumnPredicateContext;
import org.easy.query.core.basic.sql.segment.builder.SqlSegmentBuilder;
import org.easy.query.core.expression.lambda.Property;
import org.easy.query.core.abstraction.metadata.ColumnMetadata;
import org.easy.query.core.expression.parser.abstraction.SqlColumnSelector;
import org.easy.query.core.basic.api.context.UpdateContext;
import org.easy.query.core.query.builder.SqlTableInfo;
import org.easy.query.core.basic.sql.segment.segment.ColumnPredicateSegment;

import java.util.Collection;

/**
 * @FileName: DefaultSqlColumnSetSelector.java
 * @Description: 文件说明
 * @Date: 2023/2/25 21:31
 * @Created by xuejiaming
 */
public class DefaultSqlColumnSetSelector<T> implements SqlColumnSelector<T> {
    private final int index;
    private final SqlColumnPredicateContext sqlColumnPredicateContext;
    private final SqlSegmentBuilder sqlSegmentBuilder;

    public DefaultSqlColumnSetSelector(int index, SqlColumnPredicateContext sqlColumnPredicateContext, SqlSegmentBuilder sqlSegmentBuilder){

        this.index = index;
        this.sqlColumnPredicateContext = sqlColumnPredicateContext;
        this.sqlSegmentBuilder = sqlSegmentBuilder;
    }
    @Override
    public SqlColumnSelector<T> column(Property<T, ?> column) {
        SqlTableInfo table = sqlColumnPredicateContext.getTable(index);
        String propertyName = table.getPropertyName(column);
        String columnName = table.getColumnName(propertyName);
        sqlSegmentBuilder.append(new ColumnPredicateSegment(index,columnName,propertyName,sqlColumnPredicateContext));
        return this;
    }

    @Override
    public SqlColumnSelector<T> columnAll() {
        SqlTableInfo table = sqlColumnPredicateContext.getTable(index);
        Collection<ColumnMetadata> columns = table.getEntityMetadata().getColumns();
        for (ColumnMetadata column : columns) {
            String propertyName = column.getProperty().getName();
            sqlSegmentBuilder.append(new ColumnPredicateSegment(index, column.getName(),propertyName,sqlColumnPredicateContext));
        }
        return this;
    }

    @Override
    public int getIndex() {
        return index;
    }
}
