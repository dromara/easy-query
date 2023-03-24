package com.easy.query.core.expression.parser.impl;

import com.easy.query.core.abstraction.metadata.ColumnMetadata;
import com.easy.query.core.abstraction.metadata.EntityMetadata;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.segment.ColumnSegment;
import com.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import com.easy.query.core.query.SqlEntityTableExpression;
import com.easy.query.core.expression.parser.abstraction.SqlColumnResultSelector;
import com.easy.query.core.query.SqlEntityExpression;

/**
 * @FileName: DefaultColumnResultSelector.java
 * @Description: 文件说明
 * @Date: 2023/3/8 21:35
 * @author xuejiaming
 */
public class DefaultSqlColumnResultSelector<T1,TR> implements SqlColumnResultSelector<T1, TR> {
    private final int index;
    private final SqlEntityExpression sqlEntityExpression;
    private final SqlBuilderSegment sqlBuilderSegment;

    public DefaultSqlColumnResultSelector(int index, SqlEntityExpression sqlEntityExpression, SqlBuilderSegment sqlBuilderSegment){

        this.index = index;
        this.sqlEntityExpression = sqlEntityExpression;
        this.sqlBuilderSegment = sqlBuilderSegment;
    }
    @Override
    public void column(Property<T1, TR> column) {
        if(sqlBuilderSegment.isNotEmpty()){
            sqlBuilderSegment.getSqlSegments().clear();
        }
        SqlEntityTableExpression table = sqlEntityExpression.getTable(index);
        String propertyName = table.getPropertyName(column);
        sqlBuilderSegment.append(new ColumnSegment(table,propertyName, sqlEntityExpression));

    }

    @Override
    public int getIndex() {
        return index;
    }
}
