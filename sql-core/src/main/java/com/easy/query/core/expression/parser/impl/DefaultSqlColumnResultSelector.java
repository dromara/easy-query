package com.easy.query.core.expression.parser.impl;

import com.easy.query.core.expression.lambda.Property;
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
    private final int index;
    private final EntityExpressionBuilder entityExpressionBuilder;
    private final SqlBuilderSegment sqlBuilderSegment;

    public DefaultSqlColumnResultSelector(int index, EntityExpressionBuilder entityExpressionBuilder, SqlBuilderSegment sqlBuilderSegment){

        this.index = index;
        this.entityExpressionBuilder = entityExpressionBuilder;
        this.sqlBuilderSegment = sqlBuilderSegment;
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

    @Override
    public int getIndex() {
        return index;
    }
}
