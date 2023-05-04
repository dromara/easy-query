package com.easy.query.core.expression.parser.impl;

import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.SqlColumnSelector;
import com.easy.query.core.expression.segment.ColumnInsertSegment;
import com.easy.query.core.expression.segment.SqlEntitySegment;
import com.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.util.LambdaUtil;

import java.util.Collection;
import java.util.Objects;

/**
 * @FileName: DefaultSqlColumnSelector.java
 * @Description: 文件说明
 * @Date: 2023/2/12 21:36
 * @author xuejiaming
 */
public class DefaultInsertSqlColumnSelector<T1> implements SqlColumnSelector<T1> {
    private final int index;
    private final EntityExpressionBuilder sqlEntityExpression;
    private final TableAvailable table;
    private final SqlBuilderSegment sqlSegmentBuilder;

    public DefaultInsertSqlColumnSelector(int index, EntityExpressionBuilder sqlEntityExpression, SqlBuilderSegment sqlSegmentBuilder) {
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
    public SqlColumnSelector<T1> column(Property<T1, ?> column) {
        EntityTableExpressionBuilder table = sqlEntityExpression.getTable(index);
        String propertyName = LambdaUtil.getPropertyName(column);
        sqlSegmentBuilder.append(new ColumnInsertSegment(table.getEntityTable(), propertyName, sqlEntityExpression.getRuntimeContext()));
        return this;
    }

    @Override
    public SqlColumnSelector<T1> columnIgnore(Property<T1, ?> column) {

        EntityTableExpressionBuilder table = sqlEntityExpression.getTable(index);
        String propertyName = LambdaUtil.getPropertyName(column);
        sqlSegmentBuilder.getSqlSegments().removeIf(sqlSegment -> {
            if (sqlSegment instanceof SqlEntitySegment) {
                SqlEntitySegment sqlEntitySegment = (SqlEntitySegment) sqlSegment;
                return Objects.equals(sqlEntitySegment.getTable(), table) && Objects.equals(sqlEntitySegment.getPropertyName(), propertyName);
            }
            return false;
        });
        return this;
    }

    @Override
    public SqlColumnSelector<T1> columnAll() {
        EntityTableExpressionBuilder table = sqlEntityExpression.getTable(index);
        Collection<String> properties = table.getEntityMetadata().getProperties();
        for (String property : properties) {
            sqlSegmentBuilder.append(new ColumnInsertSegment(table.getEntityTable(), property, sqlEntityExpression.getRuntimeContext()));
        }
        return this;
    }
}
