package com.easy.query.core.expression.parser.core.base.impl;

import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.ColumnGroupSelector;
import com.easy.query.core.expression.segment.FuncColumnSegment;
import com.easy.query.core.expression.segment.GroupByColumnSegment;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.segment.factory.SQLSegmentFactory;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;

/**
 * @author xuejiaming
 * @Description: 文件说明
 * @Date: 2023/2/12 21:36
 */
public class ColumnGroupSelectorImpl<T1> implements ColumnGroupSelector<T1> {
    protected final int index;
    protected final EntityExpressionBuilder entityQueryExpressionBuilder;
    protected final TableAvailable table;
    protected final SQLBuilderSegment sqlSegmentBuilder;
    protected final SQLSegmentFactory sqlSegmentFactory;

    public ColumnGroupSelectorImpl(int index, EntityQueryExpressionBuilder entityQueryExpressionBuilder) {
        this.index = index;

        this.entityQueryExpressionBuilder = entityQueryExpressionBuilder;
        this.table = entityQueryExpressionBuilder.getTable(index).getEntityTable();
        this.sqlSegmentBuilder = entityQueryExpressionBuilder.getGroup();
        this.sqlSegmentFactory = entityQueryExpressionBuilder.getRuntimeContext().getSQLSegmentFactory();
    }


    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public ColumnGroupSelector<T1> column(String property) {
        EntityTableExpressionBuilder table = entityQueryExpressionBuilder.getTable(index);
        GroupByColumnSegment groupByColumnSegment = sqlSegmentFactory.createGroupByColumnSegment(table.getEntityTable(), property, entityQueryExpressionBuilder.getRuntimeContext());
        sqlSegmentBuilder.append(groupByColumnSegment);
        return this;
    }

    @Override
    public ColumnGroupSelector<T1> columnConst(String columnConst) {
        EntityTableExpressionBuilder table = entityQueryExpressionBuilder.getTable(index);
        GroupByColumnSegment groupByColumnSegment = sqlSegmentFactory.createGroupByConstSegment(table.getEntityTable(), entityQueryExpressionBuilder.getRuntimeContext(),columnConst);
        sqlSegmentBuilder.append(groupByColumnSegment);
        return this;
    }

    @Override
    public ColumnGroupSelector<T1> columnFunc(ColumnPropertyFunction columnPropertyFunction) {
        EntityTableExpressionBuilder table = entityQueryExpressionBuilder.getTable(index);
        String propertyName = columnPropertyFunction.getPropertyName();
        ColumnFunction columnFunction = columnPropertyFunction.getColumnFunction();
        FuncColumnSegment funcColumnSegment = sqlSegmentFactory.createFuncColumnSegment(table.getEntityTable(), propertyName, entityQueryExpressionBuilder.getRuntimeContext(), columnFunction, null);
        sqlSegmentBuilder.append(funcColumnSegment);
        return this;
    }
}
