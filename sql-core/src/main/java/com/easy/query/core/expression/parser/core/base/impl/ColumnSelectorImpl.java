package com.easy.query.core.expression.parser.core.base.impl;

import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.ColumnSelector;
import com.easy.query.core.expression.segment.ColumnConstSegment;
import com.easy.query.core.expression.segment.FuncColumnSegment;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;

/**
 * @author xuejiaming
 * @Description: 文件说明
 * @Date: 2023/2/12 21:36
 */
public class ColumnSelectorImpl<T1> extends AbstractColumnSelector<T1, ColumnSelector<T1>> implements ColumnSelector<T1> {
    public ColumnSelectorImpl(int index, EntityQueryExpressionBuilder entityQueryExpressionBuilder, SQLBuilderSegment sqlSegmentBuilder) {
        super(index, entityQueryExpressionBuilder, sqlSegmentBuilder);
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public ColumnSelector<T1> columnFunc(ColumnPropertyFunction columnPropertyFunction) {
        EntityTableExpressionBuilder table = entityQueryExpressionBuilder.getTable(index);
        String propertyName = columnPropertyFunction.getPropertyName();
        ColumnFunction columnFunction = columnPropertyFunction.getColumnFunction();
        String columnAsName = table.getColumnName(propertyName);
        FuncColumnSegment funcColumnSegment = sqlSegmentFactory.createFuncColumnSegment(table.getEntityTable(), propertyName, entityQueryExpressionBuilder.getRuntimeContext(), columnFunction, columnAsName);
        sqlSegmentBuilder.append(funcColumnSegment);
        return this;
    }
}
