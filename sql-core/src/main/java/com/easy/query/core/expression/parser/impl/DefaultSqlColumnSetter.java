package com.easy.query.core.expression.parser.impl;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.SqlColumnSetter;
import com.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import com.easy.query.core.enums.SqlPredicateCompareEnum;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.segment.condition.predicate.ColumnValuePredicate;
import com.easy.query.core.expression.segment.condition.predicate.ColumnWithColumnPredicate;
import com.easy.query.core.expression.segment.ColumnWithSelfSegment;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.util.LambdaUtil;

/**
 * @FileName: DefaultSqlColumnSetter.java
 * @Description: 文件说明
 * @Date: 2023/2/25 17:39
 * @author xuejiaming
 */
public class DefaultSqlColumnSetter<T>  implements SqlColumnSetter<T> {
    protected final int index;
    protected final EntityExpressionBuilder entityExpressionBuilder;
    protected final TableAvailable table;
    protected final SqlBuilderSegment sqlBuilderSegment;

    public DefaultSqlColumnSetter(int index, EntityExpressionBuilder entityExpressionBuilder, SqlBuilderSegment sqlBuilderSegment){

        this.index = index;
        this.entityExpressionBuilder = entityExpressionBuilder;
        this.table=entityExpressionBuilder.getTable(index).getEntityTable();
        this.sqlBuilderSegment = sqlBuilderSegment;
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public SqlColumnSetter<T> set(boolean condition, Property<T, ?> column, Object val) {
        if(condition)
        {
            EntityTableExpressionBuilder table = entityExpressionBuilder.getTable(index);
            String propertyName = LambdaUtil.getPropertyName(column);
            sqlBuilderSegment.append(new ColumnValuePredicate(table.getEntityTable(),propertyName,val, SqlPredicateCompareEnum.EQ, entityExpressionBuilder.getRuntimeContext()));
        }
        return this;
    }

    @Override
    public SqlColumnSetter<T> set(boolean condition, Property<T, ?> column1, Property<T, ?> column2) {
        if(condition)
        {
            EntityTableExpressionBuilder table1 = entityExpressionBuilder.getTable(index);
            String propertyName1 = LambdaUtil.getPropertyName(column1);
            EntityTableExpressionBuilder table2 = entityExpressionBuilder.getTable(index);
            String propertyName2 = LambdaUtil.getPropertyName(column2);
            sqlBuilderSegment.append(new ColumnWithColumnPredicate(table1.getEntityTable(),propertyName1,table2.getEntityTable(),propertyName2, SqlPredicateCompareEnum.EQ, entityExpressionBuilder.getRuntimeContext()));
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

        EntityTableExpressionBuilder table = entityExpressionBuilder.getTable(index);
        String propertyName = LambdaUtil.getPropertyName(column);
        sqlBuilderSegment.append(new ColumnWithSelfSegment(increment,table.getEntityTable(),propertyName,val, SqlPredicateCompareEnum.EQ, entityExpressionBuilder.getRuntimeContext()));
    }

    @Override
    public SqlColumnSetter<T> setDecrementNumber(boolean condition, Property<T, ? extends Number> column, Number val) {

        if(condition)
        {
            setSelf(false,column,val);
        }
        return this;
    }
}
