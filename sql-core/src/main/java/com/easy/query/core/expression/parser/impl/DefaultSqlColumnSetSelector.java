package com.easy.query.core.expression.parser.impl;

import com.easy.query.core.expression.segment.SqlEntitySegment;
import com.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.abstraction.SqlColumnSelector;
import com.easy.query.core.expression.segment.condition.predicate.ColumnPropertyPredicate;
import com.easy.query.core.expression.sql.SqlEntityExpression;
import com.easy.query.core.expression.sql.SqlEntityTableExpression;

import java.util.Collection;
import java.util.Objects;

/**
 * @FileName: DefaultSqlColumnSetSelector.java
 * @Description: 文件说明
 * @Date: 2023/2/25 21:31
 * @author xuejiaming
 */
public class DefaultSqlColumnSetSelector<T> implements SqlColumnSelector<T> {
    private final int index;
    private final SqlEntityExpression sqlEntityExpression;
    private final SqlBuilderSegment sqlSegmentBuilder;

    public DefaultSqlColumnSetSelector(int index, SqlEntityExpression sqlEntityExpression, SqlBuilderSegment sqlSegmentBuilder){

        this.index = index;
        this.sqlEntityExpression = sqlEntityExpression;
        this.sqlSegmentBuilder = sqlSegmentBuilder;
    }
    @Override
    public SqlColumnSelector<T> column(Property<T, ?> column) {
        SqlEntityTableExpression table = sqlEntityExpression.getTable(index);
        String propertyName = table.getPropertyName(column);
        sqlSegmentBuilder.append(new ColumnPropertyPredicate(table,propertyName,sqlEntityExpression));
        return this;
    }

    @Override
    public SqlColumnSelector<T> columnIgnore(Property<T, ?> column) {

        SqlEntityTableExpression table = sqlEntityExpression.getTable(index);
        String propertyName = table.getPropertyName(column);
        sqlSegmentBuilder.getSqlSegments().removeIf(sqlSegment -> {
            if (sqlSegment instanceof SqlEntitySegment) {
                SqlEntitySegment sqlEntitySegment = (SqlEntitySegment) sqlSegment;
                return Objects.equals(sqlEntitySegment.getTable(), table) && Objects.equals(sqlEntitySegment.getPropertyName(), propertyName);
            }
            return false;
        });
        return this;
    }

    @Override
    public SqlColumnSelector<T> columnAll() {
        SqlEntityTableExpression table = sqlEntityExpression.getTable(index);
        Collection<String> properties = table.getEntityMetadata().getProperties();
        for (String property : properties) {
            sqlSegmentBuilder.append(new ColumnPropertyPredicate(table, property,sqlEntityExpression));
        }
        return this;
    }

    @Override
    public int getIndex() {
        return index;
    }
}
