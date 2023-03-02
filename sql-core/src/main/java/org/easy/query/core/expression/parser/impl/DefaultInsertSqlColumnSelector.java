package org.easy.query.core.expression.parser.impl;

import org.easy.query.core.expression.context.SqlContext;
import org.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import org.easy.query.core.expression.lambda.Property;
import org.easy.query.core.expression.parser.abstraction.SqlColumnSelector;
import org.easy.query.core.expression.segment.ColumnInsertSegment;
import org.easy.query.core.query.builder.SqlTableInfo;

import java.util.Collection;

/**
 * @FileName: DefaultSqlColumnSelector.java
 * @Description: 文件说明
 * @Date: 2023/2/12 21:36
 * @Created by xuejiaming
 */
public class DefaultInsertSqlColumnSelector<T1> implements SqlColumnSelector<T1> {
    private final int index;
    private final SqlContext sqlContext;
    private final SqlBuilderSegment sqlSegmentBuilder;

    public DefaultInsertSqlColumnSelector(int index, SqlContext sqlContext, SqlBuilderSegment sqlSegmentBuilder) {
        this.index = index;

        this.sqlContext = sqlContext;
        this.sqlSegmentBuilder = sqlSegmentBuilder;
    }

    @Override
    public int getIndex() {
        return this.index;
    }

    @Override
    public SqlColumnSelector<T1> column(Property<T1, ?> column) {
        SqlTableInfo table = sqlContext.getTable(index);
        String propertyName = table.getPropertyName(column);
        sqlSegmentBuilder.append(new ColumnInsertSegment(table, propertyName, sqlContext));
        return this;
    }

    @Override
    public SqlColumnSelector<T1> columnAll() {
        SqlTableInfo table = sqlContext.getTable(index);
        Collection<String> properties = table.getEntityMetadata().getProperties();
        for (String property : properties) {
            sqlSegmentBuilder.append(new ColumnInsertSegment(table, property, sqlContext));
        }
        return this;
    }
}
