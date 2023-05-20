package com.easy.query.core.expression.parser.impl;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.SQLColumnSelector;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;

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
}
