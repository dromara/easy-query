package com.easy.query.core.expression.parser.impl;

import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.SqlGroupBySelector;
import com.easy.query.core.expression.segment.GroupColumnSegmentImpl;
import com.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.util.LambdaUtil;

/**
 * @FileName: DefaultSqlColumnSelector.java
 * @Description: 文件说明
 * @Date: 2023/2/12 21:36
 * @author xuejiaming
 */
public class DefaultSqlGroupColumnSelector<T1> implements SqlGroupBySelector<T1> {
    protected final int index;
    protected final EntityExpressionBuilder entityQueryExpressionBuilder;
    protected final TableAvailable table;
    protected final SqlBuilderSegment sqlSegmentBuilder;

    public DefaultSqlGroupColumnSelector(int index, EntityQueryExpressionBuilder entityQueryExpressionBuilder) {
        this.index = index;

        this.entityQueryExpressionBuilder = entityQueryExpressionBuilder;
        this.table=entityQueryExpressionBuilder.getTable(index).getEntityTable();
        this.sqlSegmentBuilder = entityQueryExpressionBuilder.getGroup();
    }


    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public SqlGroupBySelector<T1> column(Property<T1, ?> column) {
        EntityTableExpressionBuilder table = entityQueryExpressionBuilder.getTable(index);
        String propertyName = LambdaUtil.getPropertyName(column);
        sqlSegmentBuilder.append(new GroupColumnSegmentImpl(table.getEntityTable(), propertyName, entityQueryExpressionBuilder.getRuntimeContext()));
        return this;
    }
}
