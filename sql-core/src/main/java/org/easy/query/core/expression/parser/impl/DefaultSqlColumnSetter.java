package org.easy.query.core.expression.parser.impl;

import org.easy.query.core.enums.SqlPredicateCompareEnum;
import org.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import org.easy.query.core.expression.lambda.Property;
import org.easy.query.core.expression.parser.abstraction.SqlColumnSetter;
import org.easy.query.core.expression.parser.abstraction.internal.WherePredicate;
import org.easy.query.core.expression.segment.condition.predicate.ColumnValuePredicate0;
import org.easy.query.core.expression.segment.condition.predicate.ColumnWithColumnPredicate0;
import org.easy.query.core.expression.segment.condition.predicate.ColumnWithSelfPredicate;
import org.easy.query.core.query.SqlEntityExpression;
import org.easy.query.core.query.SqlEntityQueryExpression;
import org.easy.query.core.query.SqlEntityTableExpression;

/**
 * @FileName: DefaultSqlColumnSetter.java
 * @Description: 文件说明
 * @Date: 2023/2/25 17:39
 * @Created by xuejiaming
 */
public class DefaultSqlColumnSetter<T>  implements SqlColumnSetter<T> {
    private final int index;
    private final SqlEntityExpression sqlEntityExpression;
    private final SqlBuilderSegment sqlSegment0Builder;

    public DefaultSqlColumnSetter(int index, SqlEntityExpression sqlEntityExpression, SqlBuilderSegment sqlSegment0Builder){

        this.index = index;
        this.sqlEntityExpression = sqlEntityExpression;
        this.sqlSegment0Builder = sqlSegment0Builder;
    }
    @Override
    public SqlColumnSetter<T> set(boolean condition, Property<T, ?> column, Object val) {
        if(condition)
        {
            SqlEntityTableExpression table = sqlEntityExpression.getTable(index);
            String propertyName = table.getPropertyName(column);
            sqlSegment0Builder.append(new ColumnValuePredicate0(table,propertyName,val, SqlPredicateCompareEnum.EQ, sqlEntityExpression));
        }
        return this;
    }

    @Override
    public <T2, TChain2> SqlColumnSetter<T> set(boolean condition, WherePredicate<T2, TChain2> sub, Property<T, ?> column1, Property<T2, ?> column2) {
        if(condition)
        {
            SqlEntityTableExpression table1 = sqlEntityExpression.getTable(index);
            String propertyName1 = table1.getPropertyName(column1);
            SqlEntityTableExpression table2 = sqlEntityExpression.getTable(sub.getIndex());
            String propertyName2 = table2.getPropertyName(column2);
            sqlSegment0Builder.append(new ColumnWithColumnPredicate0(table1,propertyName1,table2,propertyName2, SqlPredicateCompareEnum.EQ, sqlEntityExpression));
        }
        return this;
    }

    @Override
    public SqlColumnSetter<T> setIncrement(boolean condition, Property<T, ? extends Number> column, Number val) {

        if(condition)
        {
            setSelf(true,column,val);
        }
        return this;
    }

    private void setSelf(boolean increment,Property<T, ? extends Number> column, Number val){

        SqlEntityTableExpression table = sqlEntityExpression.getTable(index);
        String propertyName = table.getPropertyName(column);
        sqlSegment0Builder.append(new ColumnWithSelfPredicate(increment,table,propertyName,val, SqlPredicateCompareEnum.EQ, sqlEntityExpression));
    }

    @Override
    public SqlColumnSetter<T> setDecrement(boolean condition, Property<T, ? extends Number> column, Number val) {

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
