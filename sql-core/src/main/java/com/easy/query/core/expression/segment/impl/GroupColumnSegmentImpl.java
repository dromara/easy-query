package com.easy.query.core.expression.segment.impl;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.Column2Segment;
import com.easy.query.core.expression.segment.GroupByColumnSegment;
import com.easy.query.core.expression.segment.OrderBySegment;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.metadata.ColumnMetadata;

/**
 * create time 2023/4/30 21:45
 * 文件说明
 *
 * @author xuejiaming
 */
public class GroupColumnSegmentImpl extends ColumnSegmentImpl implements GroupByColumnSegment {
    public GroupColumnSegmentImpl(Column2Segment column2Segment) {
        super(column2Segment,null);
    }

    @Override
    public OrderBySegment createOrderByColumnSegment(boolean asc) {
        return column2Segment.getExpressionContext().getRuntimeContext().getSQLSegmentFactory().createOrderByColumnSegment(column2Segment,asc);
    }

}
