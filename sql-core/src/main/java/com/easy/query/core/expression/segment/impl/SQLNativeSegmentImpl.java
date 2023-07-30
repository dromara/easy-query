package com.easy.query.core.expression.segment.impl;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.segment.AbstractSQLNativeSegmentImpl;
import com.easy.query.core.expression.segment.SQLEntitySegment;
import com.easy.query.core.expression.segment.SQLNativeSegment;
import com.easy.query.core.expression.segment.scec.context.SQLNativeExpressionContext;

/**
 * create time 2023/6/16 20:55
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLNativeSegmentImpl extends AbstractSQLNativeSegmentImpl implements SQLNativeSegment {
    protected final QueryRuntimeContext runtimeContext;
    protected final String sqlSegment;
    protected final SQLNativeExpressionContext sqlConstExpressionContext;

    public SQLNativeSegmentImpl(QueryRuntimeContext runtimeContext, String sqlSegment, SQLNativeExpressionContext sqlConstExpressionContext) {
        super(runtimeContext,sqlSegment,sqlConstExpressionContext);
        this.runtimeContext = runtimeContext;
        this.sqlSegment = sqlSegment;
        this.sqlConstExpressionContext = sqlConstExpressionContext;
    }

    @Override
    public String getAlias() {
        return sqlConstExpressionContext.getAlias();
    }

    @Override
    public SQLEntitySegment cloneSQLColumnSegment() {
        return new SQLNativeSegmentImpl(runtimeContext, sqlSegment, sqlConstExpressionContext);
    }
}
