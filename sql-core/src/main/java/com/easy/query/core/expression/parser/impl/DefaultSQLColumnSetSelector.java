package com.easy.query.core.expression.parser.impl;

import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.SQLColumnSelector;
import com.easy.query.core.expression.segment.SQLEntitySegment;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.segment.condition.predicate.ColumnPropertyPredicate;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.util.EasyLambdaUtil;

import java.util.Collection;
import java.util.Objects;

/**
 * @Description: 文件说明
 * @Date: 2023/2/25 21:31
 * @author xuejiaming
 */
public class DefaultSQLColumnSetSelector<T> implements SQLColumnSelector<T> {
    protected final int index;
    protected final EntityExpressionBuilder entityExpressionBuilder;
    protected final TableAvailable table;
    protected final SQLBuilderSegment sqlSegmentBuilder;

    public DefaultSQLColumnSetSelector(int index, EntityExpressionBuilder entityExpressionBuilder, SQLBuilderSegment sqlSegmentBuilder){

        this.index = index;
        this.entityExpressionBuilder = entityExpressionBuilder;
        this.table=entityExpressionBuilder.getTable(index).getEntityTable();
        this.sqlSegmentBuilder = sqlSegmentBuilder;
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public SQLColumnSelector<T> column(Property<T, ?> column) {
        EntityTableExpressionBuilder table = entityExpressionBuilder.getTable(index);
        String propertyName = EasyLambdaUtil.getPropertyName(column);
        sqlSegmentBuilder.append(new ColumnPropertyPredicate(table.getEntityTable(),propertyName, entityExpressionBuilder.getRuntimeContext()));
        return this;
    }

    @Override
    public SQLColumnSelector<T> columnIgnore(Property<T, ?> column) {

        EntityTableExpressionBuilder table = entityExpressionBuilder.getTable(index);
        String propertyName = EasyLambdaUtil.getPropertyName(column);
        sqlSegmentBuilder.getSQLSegments().removeIf(sqlSegment -> {
            if (sqlSegment instanceof SQLEntitySegment) {
                SQLEntitySegment sqlEntitySegment = (SQLEntitySegment) sqlSegment;
                return Objects.equals(sqlEntitySegment.getTable(), table) && Objects.equals(sqlEntitySegment.getPropertyName(), propertyName);
            }
            return false;
        });
        return this;
    }

    @Override
    public SQLColumnSelector<T> columnAll() {
        EntityTableExpressionBuilder table = entityExpressionBuilder.getTable(index);
        Collection<String> properties = table.getEntityMetadata().getProperties();
        for (String property : properties) {
            sqlSegmentBuilder.append(new ColumnPropertyPredicate(table.getEntityTable(), property, entityExpressionBuilder.getRuntimeContext()));
        }
        return this;
    }

    @Override
    public SQLColumnSelector<T> columnFunc(ColumnPropertyFunction columnPropertyFunction) {
        throw new UnsupportedOperationException();
    }
}
