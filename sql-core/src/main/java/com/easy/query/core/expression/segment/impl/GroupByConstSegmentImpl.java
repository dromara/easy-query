package com.easy.query.core.expression.segment.impl;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.ColumnSegment;
import com.easy.query.core.expression.segment.GroupByColumnSegment;
import com.easy.query.core.expression.segment.OrderBySegment;

/**
 * create time 2023/6/16 20:43
 * 文件说明
 *
 * @author xuejiaming
 */
public class GroupByConstSegmentImpl extends ColumnConstSegmentImpl implements GroupByColumnSegment {

    public GroupByConstSegmentImpl(TableAvailable table, QueryRuntimeContext runtimeContext, String columnConst){
        super(table,runtimeContext,columnConst);

    }

    @Override
    public ColumnSegment cloneSQLEntitySegment() {
        return new GroupByConstSegmentImpl(table,runtimeContext,columnConst);
    }

    @Override
    public String getAlias() {
        return null;
    }

    @Override
    public OrderBySegment createOrderByColumnSegment(boolean asc) {
        return runtimeContext.getSQLSegmentFactory().createOrderByConstSegment(table,runtimeContext,columnConst,asc);
    }
}
