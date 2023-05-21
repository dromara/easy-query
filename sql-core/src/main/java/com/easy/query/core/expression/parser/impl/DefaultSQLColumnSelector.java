package com.easy.query.core.expression.parser.impl;

import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.SQLColumnSelector;
import com.easy.query.core.expression.segment.FuncColumnSegmentImpl;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;

/**
 * @Description: 文件说明
 * @Date: 2023/2/12 21:36
 * @author xuejiaming
 */
public class DefaultSQLColumnSelector<T1> extends AbstractSQLColumnSelector<T1, SQLColumnSelector<T1>> implements SQLColumnSelector<T1> {
    public DefaultSQLColumnSelector(int index, EntityQueryExpressionBuilder entityQueryExpressionBuilder, SQLBuilderSegment sqlSegmentBuilder) {
        super(index, entityQueryExpressionBuilder,sqlSegmentBuilder);
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public SQLColumnSelector<T1> columnFunc(ColumnPropertyFunction columnPropertyFunction) {
        EntityTableExpressionBuilder table = entityQueryExpressionBuilder.getTable(index);
        String propertyName = columnPropertyFunction.getPropertyName();
        ColumnFunction columnFunction = columnPropertyFunction.getColumnFunction();
        String columnAsName = table.getColumnName(propertyName);
        sqlSegmentBuilder.append(new FuncColumnSegmentImpl(table.getEntityTable(), propertyName, entityQueryExpressionBuilder.getRuntimeContext(), columnFunction, columnAsName));
        return this;
    }
}
