package com.easy.query.core.expression.parser.impl;

import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.SQLColumnSelector;
import com.easy.query.core.expression.segment.ColumnInsertSegment;
import com.easy.query.core.expression.segment.SQLEntitySegment;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.util.LambdaUtil;

import java.util.Collection;
import java.util.Objects;

/**
 * @Description: 文件说明
 * @Date: 2023/2/12 21:36
 * @author xuejiaming
 */
public class DefaultInsertSQLColumnSelector<T1> implements SQLColumnSelector<T1> {
    protected final int index;
    protected final EntityExpressionBuilder sqlEntityExpression;
    protected final TableAvailable table;
    protected final SQLBuilderSegment sqlSegmentBuilder;

    public DefaultInsertSQLColumnSelector(int index, EntityExpressionBuilder sqlEntityExpression, SQLBuilderSegment sqlSegmentBuilder) {
        this.index = index;
        this.sqlEntityExpression = sqlEntityExpression;
        this.table = sqlEntityExpression.getTable(index).getEntityTable();
        this.sqlSegmentBuilder = sqlSegmentBuilder;
    }
    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public SQLColumnSelector<T1> column(Property<T1, ?> column) {
        EntityTableExpressionBuilder table = sqlEntityExpression.getTable(index);
        String propertyName = LambdaUtil.getPropertyName(column);
        sqlSegmentBuilder.append(new ColumnInsertSegment(table.getEntityTable(), propertyName, sqlEntityExpression.getRuntimeContext()));
        return this;
    }

    @Override
    public SQLColumnSelector<T1> columnIgnore(Property<T1, ?> column) {

        EntityTableExpressionBuilder table = sqlEntityExpression.getTable(index);
        String propertyName = LambdaUtil.getPropertyName(column);
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
    public SQLColumnSelector<T1> columnAll() {
        EntityTableExpressionBuilder table = sqlEntityExpression.getTable(index);
        Collection<String> properties = table.getEntityMetadata().getProperties();
        for (String property : properties) {
            sqlSegmentBuilder.append(new ColumnInsertSegment(table.getEntityTable(), property, sqlEntityExpression.getRuntimeContext()));
        }
        return this;
    }
}
