package com.easy.query.core.expression.parser.impl;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.SqlColumnSelector;
import com.easy.query.core.expression.segment.OrderColumnSegmentImpl;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.segment.SqlEntitySegment;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.util.LambdaUtil;

import java.util.Collection;
import java.util.Objects;

/**
 * @FileName: DefaultSqlColumnSelector.java
 * @Description: 文件说明
 * @Date: 2023/2/8 12:26
 * @author xuejiaming
 */
public  class DefaultSqlOrderColumnSelector<T1> implements SqlColumnSelector<T1> {
    protected final int index;
    protected final EntityQueryExpressionBuilder entityQueryExpressionBuilder;
    protected final TableAvailable table;
    protected boolean asc;

    public DefaultSqlOrderColumnSelector(int index, EntityQueryExpressionBuilder entityQueryExpressionBuilder){
        this.index = index;
        this.entityQueryExpressionBuilder = entityQueryExpressionBuilder;
        this.table = entityQueryExpressionBuilder.getTable(index).getEntityTable();
    }

    @Override
    public TableAvailable getTable() {
        return null;
    }

    @Override
    public SqlColumnSelector<T1> column(Property<T1, ?> column) {
        EntityTableExpressionBuilder table = entityQueryExpressionBuilder.getTable(index);
        String propertyName = LambdaUtil.getPropertyName(column);
        entityQueryExpressionBuilder.getOrder().append(new OrderColumnSegmentImpl(table.getEntityTable(),propertyName, entityQueryExpressionBuilder.getRuntimeContext(),asc));
        return this;
    }

    @Override
    public SqlColumnSelector<T1> columnIgnore(Property<T1, ?> column) {

        EntityTableExpressionBuilder table = entityQueryExpressionBuilder.getTable(index);
        String propertyName = LambdaUtil.getPropertyName(column);
        entityQueryExpressionBuilder.getOrder().getSqlSegments().removeIf(sqlSegment -> {
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
        EntityTableExpressionBuilder table = entityQueryExpressionBuilder.getTable(index);
        Collection<String> properties = table.getEntityMetadata().getProperties();
        for (String property : properties) {
            entityQueryExpressionBuilder.getOrder().append(new OrderColumnSegmentImpl(table.getEntityTable(), property, entityQueryExpressionBuilder.getRuntimeContext(),asc));
        }
        return this;
    }


    public void setAsc(boolean asc) {
        this.asc = asc;
    }

}
