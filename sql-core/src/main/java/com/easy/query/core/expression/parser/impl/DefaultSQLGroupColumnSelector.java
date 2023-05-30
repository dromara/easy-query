package com.easy.query.core.expression.parser.impl;

import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.SQLGroupBySelector;
import com.easy.query.core.expression.segment.FuncColumnSegment;
import com.easy.query.core.expression.segment.GroupByColumnSegment;
import com.easy.query.core.expression.segment.factory.SQLSegmentFactory;
import com.easy.query.core.expression.segment.impl.FuncColumnSegmentImpl;
import com.easy.query.core.expression.segment.impl.GroupColumnSegmentImpl;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.util.EasyLambdaUtil;

/**
 * @Description: 文件说明
 * @Date: 2023/2/12 21:36
 * @author xuejiaming
 */
public class DefaultSQLGroupColumnSelector<T1> implements SQLGroupBySelector<T1> {
    protected final int index;
    protected final EntityExpressionBuilder entityQueryExpressionBuilder;
    protected final TableAvailable table;
    protected final SQLBuilderSegment sqlSegmentBuilder;
    protected final SQLSegmentFactory sqlSegmentFactory;

    public DefaultSQLGroupColumnSelector(int index, EntityQueryExpressionBuilder entityQueryExpressionBuilder) {
        this.index = index;

        this.entityQueryExpressionBuilder = entityQueryExpressionBuilder;
        this.table=entityQueryExpressionBuilder.getTable(index).getEntityTable();
        this.sqlSegmentBuilder = entityQueryExpressionBuilder.getGroup();
        this.sqlSegmentFactory = entityQueryExpressionBuilder.getRuntimeContext().getSQLSegmentFactory();
    }


    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public SQLGroupBySelector<T1> column(Property<T1, ?> column) {
        EntityTableExpressionBuilder table = entityQueryExpressionBuilder.getTable(index);
        String propertyName = EasyLambdaUtil.getPropertyName(column);
        GroupByColumnSegment groupByColumnSegment = sqlSegmentFactory.createGroupByColumnSegment(table.getEntityTable(), propertyName, entityQueryExpressionBuilder.getRuntimeContext());
        sqlSegmentBuilder.append(groupByColumnSegment);
        return this;
    }

    @Override
    public SQLGroupBySelector<T1> columnFunc(ColumnPropertyFunction columnPropertyFunction) {
        EntityTableExpressionBuilder table = entityQueryExpressionBuilder.getTable(index);
        String propertyName = columnPropertyFunction.getPropertyName();
        ColumnFunction columnFunction = columnPropertyFunction.getColumnFunction();
        FuncColumnSegment funcColumnSegment = sqlSegmentFactory.createFuncColumnSegment(table.getEntityTable(), propertyName, entityQueryExpressionBuilder.getRuntimeContext(), columnFunction, null);
        sqlSegmentBuilder.append(funcColumnSegment);
        return this;
    }
}
