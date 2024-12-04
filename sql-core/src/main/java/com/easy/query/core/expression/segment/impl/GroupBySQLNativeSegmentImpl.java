package com.easy.query.core.expression.segment.impl;

import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.segment.ColumnSegment;
import com.easy.query.core.expression.segment.GroupByColumnSegment;
import com.easy.query.core.expression.segment.GroupBySQLNativeSegment;
import com.easy.query.core.expression.segment.OrderBySegment;
import com.easy.query.core.expression.segment.scec.context.core.SQLNativeExpression;
import com.easy.query.core.expression.sql.builder.ExpressionContext;

/**
 * create time 2023/6/16 20:43
 * 文件说明
 *
 * @author xuejiaming
 */
public class GroupBySQLNativeSegmentImpl extends SQLNativeSegmentImpl implements GroupBySQLNativeSegment {

    public GroupBySQLNativeSegmentImpl(ExpressionContext expressionContext, String columnConst, SQLNativeExpression sqlNativeExpression){
        super(expressionContext,columnConst,sqlNativeExpression);

    }

    @Override
    public GroupBySQLNativeSegment cloneSQLColumnSegment() {
        return new GroupBySQLNativeSegmentImpl(expressionContext, sqlSegment, sqlNativeExpression);
    }

    @Override
    public OrderBySegment createOrderByColumnSegment(boolean asc) {
        throw new EasyQueryInvalidOperationException("group const column cant convert order column");
    }
}
