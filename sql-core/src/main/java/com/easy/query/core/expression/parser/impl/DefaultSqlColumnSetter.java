package com.easy.query.core.expression.parser.impl;

import com.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import com.easy.query.core.enums.SqlPredicateCompareEnum;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.abstraction.SqlColumnSetter;
import com.easy.query.core.expression.segment.condition.predicate.ColumnValuePredicate;
import com.easy.query.core.expression.segment.condition.predicate.ColumnWithColumnPredicate;
import com.easy.query.core.expression.segment.condition.predicate.ColumnWithSelfPredicate;
import com.easy.query.core.expression.sql.EntityExpression;
import com.easy.query.core.expression.sql.EntityTableExpression;

/**
 * @FileName: DefaultSqlColumnSetter.java
 * @Description: 文件说明
 * @Date: 2023/2/25 17:39
 * @author xuejiaming
 */
public class DefaultSqlColumnSetter<T>  implements SqlColumnSetter<T> {
    private final int index;
    private final EntityExpression sqlEntityExpression;
    private final SqlBuilderSegment sqlSegment0Builder;

    public DefaultSqlColumnSetter(int index, EntityExpression sqlEntityExpression, SqlBuilderSegment sqlSegment0Builder){

        this.index = index;
        this.sqlEntityExpression = sqlEntityExpression;
        this.sqlSegment0Builder = sqlSegment0Builder;
    }
    @Override
    public SqlColumnSetter<T> set(boolean condition, Property<T, ?> column, Object val) {
        if(condition)
        {
            EntityTableExpression table = sqlEntityExpression.getTable(index);
            String propertyName = table.getPropertyName(column);
            sqlSegment0Builder.append(new ColumnValuePredicate(table,propertyName,val, SqlPredicateCompareEnum.EQ, sqlEntityExpression));
        }
        return this;
    }

    @Override
    public SqlColumnSetter<T> set(boolean condition, Property<T, ?> column1, Property<T, ?> column2) {
        if(condition)
        {
            EntityTableExpression table1 = sqlEntityExpression.getTable(index);
            String propertyName1 = table1.getPropertyName(column1);
            EntityTableExpression table2 = sqlEntityExpression.getTable(index);
            String propertyName2 = table2.getPropertyName(column2);
            sqlSegment0Builder.append(new ColumnWithColumnPredicate(table1,propertyName1,table2,propertyName2, SqlPredicateCompareEnum.EQ, sqlEntityExpression));
        }
        return this;
    }

    @Override
    public SqlColumnSetter<T> setIncrementNumber(boolean condition, Property<T, ? extends Number> column, Number val) {

        if(condition)
        {
            setSelf(true,column,val);
        }
        return this;
    }

    private void setSelf(boolean increment,Property<T, ? extends Number> column, Number val){

        EntityTableExpression table = sqlEntityExpression.getTable(index);
        String propertyName = table.getPropertyName(column);
        sqlSegment0Builder.append(new ColumnWithSelfPredicate(increment,table,propertyName,val, SqlPredicateCompareEnum.EQ, sqlEntityExpression));
    }

    @Override
    public SqlColumnSetter<T> setDecrementNumber(boolean condition, Property<T, ? extends Number> column, Number val) {

        if(condition)
        {
            setSelf(false,column,val);
        }
        return this;
    }

    @Override
    public int getIndex() {
        return index;
    }
}
