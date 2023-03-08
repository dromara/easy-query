package org.easy.query.core.expression.parser.impl;

import org.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import org.easy.query.core.expression.lambda.Property;
import org.easy.query.core.expression.parser.abstraction.SqlColumnSelector;
import org.easy.query.core.expression.segment.ColumnInsertSegment;
import org.easy.query.core.query.SqlEntityExpression;
import org.easy.query.core.query.SqlEntityTableExpression;

import java.util.Collection;

/**
 * @FileName: DefaultSqlColumnSelector.java
 * @Description: 文件说明
 * @Date: 2023/2/12 21:36
 * @Created by xuejiaming
 */
public class DefaultInsertSqlColumnSelector<T1> implements SqlColumnSelector<T1> {
    private final int index;
    private final SqlEntityExpression sqlEntityExpression;
    private final SqlBuilderSegment sqlSegmentBuilder;

    public DefaultInsertSqlColumnSelector(int index, SqlEntityExpression sqlEntityExpression, SqlBuilderSegment sqlSegmentBuilder) {
        this.index = index;

        this.sqlEntityExpression = sqlEntityExpression;
        this.sqlSegmentBuilder = sqlSegmentBuilder;
    }

    @Override
    public int getIndex() {
        return this.index;
    }

    @Override
    public SqlColumnSelector<T1> column(Property<T1, ?> column) {
        SqlEntityTableExpression table = sqlEntityExpression.getTable(index);
        String propertyName = table.getPropertyName(column);
        sqlSegmentBuilder.append(new ColumnInsertSegment(table, propertyName, sqlEntityExpression));
        return this;
    }

    @Override
    public SqlColumnSelector<T1> columnAll() {
        SqlEntityTableExpression table = sqlEntityExpression.getTable(index);
        Collection<String> properties = table.getEntityMetadata().getProperties();
        for (String property : properties) {
            sqlSegmentBuilder.append(new ColumnInsertSegment(table, property, sqlEntityExpression));
        }
        return this;
    }
}
