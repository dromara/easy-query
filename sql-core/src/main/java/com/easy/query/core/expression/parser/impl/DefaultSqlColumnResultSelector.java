package com.easy.query.core.expression.parser.impl;

import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.ColumnSegmentImpl;
import com.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.parser.core.SqlColumnResultSelector;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.util.LambdaUtil;

/**
 * @FileName: DefaultColumnResultSelector.java
 * @Description: 文件说明
 * @Date: 2023/3/8 21:35
 * @author xuejiaming
 */
public class DefaultSqlColumnResultSelector<T1,TR> implements SqlColumnResultSelector<T1, TR> {
    protected final int index;
    protected final EntityExpressionBuilder entityExpressionBuilder;
    protected final SqlBuilderSegment sqlBuilderSegment;
    protected final TableAvailable table;

    public DefaultSqlColumnResultSelector(int index, EntityExpressionBuilder entityExpressionBuilder, SqlBuilderSegment sqlBuilderSegment){

        this.index = index;
        this.entityExpressionBuilder = entityExpressionBuilder;
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
            sqlBuilderSegment.getSqlSegments().clear();
        }
        EntityTableExpressionBuilder table = entityExpressionBuilder.getTable(index);
        String propertyName = LambdaUtil.getPropertyName(column);
        sqlBuilderSegment.append(new ColumnSegmentImpl(table.getEntityTable(),propertyName, entityExpressionBuilder.getRuntimeContext()));

    }
}
