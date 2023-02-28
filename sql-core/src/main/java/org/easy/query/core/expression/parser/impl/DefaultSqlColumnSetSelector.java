package org.easy.query.core.expression.parser.impl;

import org.easy.query.core.basic.api.context.SqlContext;
import org.easy.query.core.expression.builder.SqlSegmentBuilder;
import org.easy.query.core.expression.lambda.Property;
import org.easy.query.core.abstraction.metadata.ColumnMetadata;
import org.easy.query.core.expression.parser.abstraction.SqlColumnSelector;
import org.easy.query.core.query.builder.SqlTableInfo;
import org.easy.query.core.expression.segment.predicate.node.ColumnPropertyPredicate;

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
        sqlSegmentBuilder.append(new ColumnPropertyPredicate(table,propertyName,sqlContext));
        return this;
    }

    @Override
    public SqlColumnSelector<T> columnAll() {
        SqlTableInfo table = sqlContext.getTable(index);
        Collection<String> properties = table.getEntityMetadata().getProperties();
        for (String property : properties) {
            sqlSegmentBuilder.append(new ColumnPropertyPredicate(table, property,sqlContext));
        }
        return this;
    }

    @Override
    public int getIndex() {
        return index;
    }
}
