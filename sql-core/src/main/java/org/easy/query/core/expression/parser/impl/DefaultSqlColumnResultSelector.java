package org.easy.query.core.expression.parser.impl;

import org.easy.query.core.expression.lambda.Property;
import org.easy.query.core.expression.parser.abstraction.SqlColumnResultSelector;
import org.easy.query.core.expression.segment.ColumnSegment;
import org.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import org.easy.query.core.query.SqlEntityExpression;
import org.easy.query.core.query.SqlEntityTableExpression;

/**
 * @FileName: DefaultColumnResultSelector.java
 * @Description: 文件说明
 * @Date: 2023/3/8 21:35
 * @Created by xuejiaming
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
