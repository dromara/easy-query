package com.easy.query.core.expression.parser.impl;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.SQLColumnSelector;
import com.easy.query.core.expression.segment.OrderColumnSegmentImpl;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.segment.SQLEntitySegment;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.util.EasyLambdaUtil;

import java.util.Collection;
import java.util.Objects;

/**
 * @Description: 文件说明
 * @Date: 2023/2/8 12:26
 * @author xuejiaming
 */
public  class DefaultSQLOrderColumnSelector<T1> implements SQLColumnSelector<T1> {
    protected final int index;
    protected final EntityQueryExpressionBuilder entityQueryExpressionBuilder;
    protected final TableAvailable table;
    protected boolean asc;

    public DefaultSQLOrderColumnSelector(int index, EntityQueryExpressionBuilder entityQueryExpressionBuilder){
        this.index = index;
        this.entityQueryExpressionBuilder = entityQueryExpressionBuilder;
        this.table = entityQueryExpressionBuilder.getTable(index).getEntityTable();
    }

    @Override
    public TableAvailable getTable() {
        return null;
    }

    @Override
    public SQLColumnSelector<T1> column(Property<T1, ?> column) {
        EntityTableExpressionBuilder table = entityQueryExpressionBuilder.getTable(index);
        String propertyName = EasyLambdaUtil.getPropertyName(column);
        entityQueryExpressionBuilder.getOrder().append(new OrderColumnSegmentImpl(table.getEntityTable(),propertyName, entityQueryExpressionBuilder.getRuntimeContext(),asc));
        return this;
    }

    @Override
    public SQLColumnSelector<T1> columnIgnore(Property<T1, ?> column) {

        EntityTableExpressionBuilder table = entityQueryExpressionBuilder.getTable(index);
        String propertyName = EasyLambdaUtil.getPropertyName(column);
        entityQueryExpressionBuilder.getOrder().getSQLSegments().removeIf(sqlSegment -> {
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
