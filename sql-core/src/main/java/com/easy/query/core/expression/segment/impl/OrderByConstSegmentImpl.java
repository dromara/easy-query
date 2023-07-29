package com.easy.query.core.expression.segment.impl;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.SQLKeywordEnum;
import com.easy.query.core.expression.segment.ColumnSegment;
import com.easy.query.core.expression.segment.OrderBySegment;
import com.easy.query.core.expression.segment.scec.context.SQLConstExpressionContext;

/**
 * create time 2023/6/16 20:43
 * 文件说明
 *
 * @author xuejiaming
 */
public class OrderByConstSegmentImpl extends ColumnConstSegmentImpl implements OrderBySegment {
    protected final boolean asc;

    public OrderByConstSegmentImpl(QueryRuntimeContext runtimeContext, String columnConst, SQLConstExpressionContext sqlConstExpressionContext, boolean asc){
        super(runtimeContext,columnConst,sqlConstExpressionContext);

        this.asc = asc;
    }

    @Override
    public ColumnSegment cloneSQLColumnSegment() {
        return new OrderByConstSegmentImpl(runtimeContext,columnConst,sqlConstExpressionContext,asc);
    }

    @Override
    public boolean isAsc() {
        return asc;
    }

    @Override
    public String getAlias() {
        return null;
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {

        StringBuilder sql = new StringBuilder().append(columnConst);
        if(asc){
            sql.append(" ").append(SQLKeywordEnum.ASC.toSQL());
        }else {
            sql.append(" ").append(SQLKeywordEnum.DESC.toSQL());
        }
        return sql.toString();
    }
}
