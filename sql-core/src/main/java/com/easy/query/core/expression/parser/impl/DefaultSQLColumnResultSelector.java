package com.easy.query.core.expression.parser.impl;

import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.ColumnSegment;
import com.easy.query.core.expression.segment.factory.SQLSegmentFactory;
import com.easy.query.core.expression.segment.impl.ColumnSegmentImpl;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.parser.core.SQLColumnResultSelector;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.util.EasyLambdaUtil;

/**
 * @FileName: DefaultColumnResultSelector.java
 * @Description: 文件说明
 * @Date: 2023/3/8 21:35
 * @author xuejiaming
 */
public class DefaultSQLColumnResultSelector<T1,TR> implements SQLColumnResultSelector<T1, TR> {
    protected final int index;
    protected final EntityExpressionBuilder entityExpressionBuilder;
    protected final SQLBuilderSegment sqlBuilderSegment;
    protected final TableAvailable table;
    protected final SQLSegmentFactory sqlSegmentFactory;

    public DefaultSQLColumnResultSelector(int index, EntityExpressionBuilder entityExpressionBuilder, SQLBuilderSegment sqlBuilderSegment){

        this.index = index;
        this.entityExpressionBuilder = entityExpressionBuilder;
        this.sqlSegmentFactory = entityExpressionBuilder.getRuntimeContext().getSQLSegmentFactory();
        this.table = entityExpressionBuilder.getTable(index).getEntityTable();
        this.sqlBuilderSegment = sqlBuilderSegment;
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public void column(Property<T1, TR> column) {
        if(sqlBuilderSegment.isNotEmpty()){
            sqlBuilderSegment.getSQLSegments().clear();
        }
        EntityTableExpressionBuilder table = entityExpressionBuilder.getTable(index);
        String propertyName = EasyLambdaUtil.getPropertyName(column);
        ColumnSegment columnSegment = sqlSegmentFactory.createColumnSegment(table.getEntityTable(), propertyName, entityExpressionBuilder.getRuntimeContext(), null);
        sqlBuilderSegment.append(columnSegment);

    }
}
