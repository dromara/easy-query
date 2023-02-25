package org.easy.query.core.impl.lambda.select;

import org.easy.query.core.abstraction.SqlSegment0Builder;
import org.easy.query.core.abstraction.lambda.Property;
import org.easy.query.core.abstraction.sql.base.SqlColumnSetter;
import org.easy.query.core.abstraction.sql.base.WherePredicate;
import org.easy.query.core.enums.SqlKeywordEnum;
import org.easy.query.core.impl.SqlContext;
import org.easy.query.core.impl.UpdateContext;
import org.easy.query.core.query.builder.SqlTableInfo;
import org.easy.query.core.segments.ColumnSetValueSegment;
import org.easy.query.core.segments.predicate.ColumnValuePredicate;

/**
 * @FileName: DefaultSqlColumnSetter.java
 * @Description: 文件说明
 * @Date: 2023/2/25 17:39
 * @Created by xuejiaming
 */
public class DefaultSqlColumnSetter<T>  implements SqlColumnSetter<T> {
    private final int index;
    private final UpdateContext updateContext;
    private final SqlSegment0Builder sqlSegment0Builder;

    public DefaultSqlColumnSetter(int index, UpdateContext updateContext, SqlSegment0Builder sqlSegment0Builder){

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
            sqlSegment0Builder.append(new ColumnSetValueSegment(index,columnName,propertyName,val, updateContext));
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
