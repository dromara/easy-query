package com.easy.query.core.expression.parser.impl;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.SQLColumnSetter;
import com.easy.query.core.expression.segment.ColumnWithSelfSegment;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.enums.SQLPredicateCompareEnum;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.segment.condition.predicate.ColumnValuePredicate;
import com.easy.query.core.expression.segment.condition.predicate.ColumnWithColumnPredicate;
import com.easy.query.core.expression.segment.factory.SQLSegmentFactory;
import com.easy.query.core.expression.segment.impl.ColumnWithSelfSegmentImpl;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.util.EasyLambdaUtil;

/**
 * @author xuejiaming
 * @Description: 文件说明
 * @Date: 2023/2/25 17:39
 */
public class DefaultSQLColumnSetter<T> implements SQLColumnSetter<T> {
    protected final int index;
    protected final EntityExpressionBuilder entityExpressionBuilder;
    protected final TableAvailable table;
    protected final SQLBuilderSegment sqlBuilderSegment;
    protected final QueryRuntimeContext runtimeContext;
    protected final SQLSegmentFactory sqlSegmentFactory;

    public DefaultSQLColumnSetter(int index, EntityExpressionBuilder entityExpressionBuilder, SQLBuilderSegment sqlBuilderSegment) {

        this.index = index;
        this.entityExpressionBuilder = entityExpressionBuilder;
        this.runtimeContext = entityExpressionBuilder.getRuntimeContext();
        this.sqlSegmentFactory = runtimeContext.getSQLSegmentFactory();
        this.table = entityExpressionBuilder.getTable(index).getEntityTable();
        this.sqlBuilderSegment = sqlBuilderSegment;
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public SQLColumnSetter<T> set(boolean condition, Property<T, ?> column, Object val) {
        if (condition) {
            EntityTableExpressionBuilder table = entityExpressionBuilder.getTable(index);
            String propertyName = EasyLambdaUtil.getPropertyName(column);
            sqlBuilderSegment.append(new ColumnValuePredicate(table.getEntityTable(), propertyName, val, SQLPredicateCompareEnum.EQ, entityExpressionBuilder.getRuntimeContext()));
        }
        return this;
    }

    @Override
    public SQLColumnSetter<T> set(boolean condition, Property<T, ?> column1, Property<T, ?> column2) {
        if (condition) {
            EntityTableExpressionBuilder table1 = entityExpressionBuilder.getTable(index);
            String propertyName1 = EasyLambdaUtil.getPropertyName(column1);
            EntityTableExpressionBuilder table2 = entityExpressionBuilder.getTable(index);
            String propertyName2 = EasyLambdaUtil.getPropertyName(column2);
            sqlBuilderSegment.append(new ColumnWithColumnPredicate(table1.getEntityTable(), propertyName1, table2.getEntityTable(), propertyName2, SQLPredicateCompareEnum.EQ, entityExpressionBuilder.getRuntimeContext()));
        }
        return this;
    }

    @Override
    public SQLColumnSetter<T> setIncrementNumber(boolean condition, Property<T, ? extends Number> column, Number val) {

        if (condition) {
            setSelf(true, column, val);
        }
        return this;
    }

    private void setSelf(boolean increment, Property<T, ? extends Number> column, Number val) {

        EntityTableExpressionBuilder table = entityExpressionBuilder.getTable(index);
        String propertyName = EasyLambdaUtil.getPropertyName(column);
        ColumnWithSelfSegment columnWithSelfSegment = sqlSegmentFactory.createColumnWithSelfSegment(increment, table.getEntityTable(), propertyName, val, SQLPredicateCompareEnum.EQ, entityExpressionBuilder.getRuntimeContext());
        sqlBuilderSegment.append(columnWithSelfSegment);
    }

    @Override
    public SQLColumnSetter<T> setDecrementNumber(boolean condition, Property<T, ? extends Number> column, Number val) {

        if (condition) {
            setSelf(false, column, val);
        }
        return this;
    }
}
