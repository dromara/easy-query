package org.easy.query.core.basic.expression.parser.impl;

import org.easy.query.core.basic.sql.segment.builder.SqlSegmentBuilder;
import org.easy.query.core.basic.expression.lambda.Property;
import org.easy.query.core.basic.expression.parser.abstraction.SqlColumnSetter;
import org.easy.query.core.basic.expression.parser.abstraction.internal.WherePredicate;
import org.easy.query.core.impl.UpdateContext;
import org.easy.query.core.query.builder.SqlTableInfo;
import org.easy.query.core.basic.sql.segment.segment.predicate.ColumnSetValuePredicate;

/**
 * @FileName: DefaultSqlColumnSetter.java
 * @Description: 文件说明
 * @Date: 2023/2/25 17:39
 * @Created by xuejiaming
 */
public class DefaultSqlColumnSetter<T>  implements SqlColumnSetter<T> {
    private final int index;
    private final UpdateContext updateContext;
    private final SqlSegmentBuilder sqlSegment0Builder;

    public DefaultSqlColumnSetter(int index, UpdateContext updateContext, SqlSegmentBuilder sqlSegment0Builder){

        this.index = index;
        this.updateContext = updateContext;
        this.sqlSegment0Builder = sqlSegment0Builder;
    }
    @Override
    public SqlColumnSetter<T> set(boolean condition, Property<T, ?> column, Object val) {
        if(condition)
        {
            SqlTableInfo table = updateContext.getTable(index);
            String propertyName = table.getPropertyName(column);
            String columnName = table.getColumnName(propertyName);
            sqlSegment0Builder.append(new ColumnSetValuePredicate(index,columnName,propertyName,val, updateContext));
        }
        return this;
    }

    @Override
    public <T2, TChain2> SqlColumnSetter<T> set(boolean condition, WherePredicate<T2, TChain2> sub, Property<T, ?> column1, Property<T2, ?> column2) {
        return null;
    }

    @Override
    public SqlColumnSetter<T> setIncrement(boolean condition, Property<T, ? extends Number> column, String val) {
        return null;
    }

    @Override
    public SqlColumnSetter<T> setDecrement(boolean condition, Property<T, ? extends Number> column, String val) {
        return null;
    }

    @Override
    public int getIndex() {
        return index;
    }
}
