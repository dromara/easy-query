package com.easy.query.core.expression.parser.impl;

import com.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import com.easy.query.core.enums.SqlPredicateCompareEnum;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.abstraction.SqlColumnSetter;
import com.easy.query.core.expression.segment.condition.predicate.ColumnValuePredicate;
import com.easy.query.core.expression.segment.condition.predicate.ColumnWithColumnPredicate;
import com.easy.query.core.expression.segment.ColumnWithSelfSegment;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;

/**
 * @FileName: DefaultSqlColumnSetter.java
 * @Description: 文件说明
 * @Date: 2023/2/25 17:39
 * @author xuejiaming
 */
public class DefaultSqlColumnSetter<T>  implements SqlColumnSetter<T> {
    private final int index;
    private final EntityExpressionBuilder sqlEntityExpression;
    private final SqlBuilderSegment sqlSegment0Builder;

    public DefaultSqlColumnSetter(int index, EntityExpressionBuilder sqlEntityExpression, SqlBuilderSegment sqlSegment0Builder){

        this.index = index;
        this.sqlEntityExpression = sqlEntityExpression;
        this.sqlSegment0Builder = sqlSegment0Builder;
    }
    @Override
    public SqlColumnSetter<T> set(boolean condition, Property<T, ?> column, Object val) {
        if(condition)
        {
            EntityTableExpressionBuilder table = sqlEntityExpression.getTable(index);
            String propertyName = table.getPropertyName(column);
            sqlSegment0Builder.append(new ColumnValuePredicate(table.getEntityTable(),propertyName,val, SqlPredicateCompareEnum.EQ, sqlEntityExpression.getRuntimeContext()));
        }
        return this;
    }

    @Override
    public SqlColumnSetter<T> set(boolean condition, Property<T, ?> column1, Property<T, ?> column2) {
        if(condition)
        {
            EntityTableExpressionBuilder table1 = sqlEntityExpression.getTable(index);
            String propertyName1 = table1.getPropertyName(column1);
            EntityTableExpressionBuilder table2 = sqlEntityExpression.getTable(index);
            String propertyName2 = table2.getPropertyName(column2);
            sqlSegment0Builder.append(new ColumnWithColumnPredicate(table1.getEntityTable(),propertyName1,table2.getEntityTable(),propertyName2, SqlPredicateCompareEnum.EQ, sqlEntityExpression.getRuntimeContext()));
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

        EntityTableExpressionBuilder table = sqlEntityExpression.getTable(index);
        String propertyName = table.getPropertyName(column);
        sqlSegment0Builder.append(new ColumnWithSelfSegment(increment,table.getEntityTable(),propertyName,val, SqlPredicateCompareEnum.EQ, sqlEntityExpression.getRuntimeContext()));
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
