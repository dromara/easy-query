package com.easy.query.core.expression.segment.impl;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.segment.SQLEntitySegment;
import com.easy.query.core.expression.segment.SQLNativeSegment;
import com.easy.query.core.expression.segment.scec.context.SQLNativeExpressionContext;
import com.easy.query.core.metadata.ColumnMetadata;

/**
 * create time 2023/6/16 20:55
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLNativeSegmentImpl extends AbstractSQLNativeSegmentImpl implements SQLNativeSegment {
    protected final QueryRuntimeContext runtimeContext;
    protected final String sqlSegment;
    protected final SQLNativeExpressionContext sqlNativeExpressionContext;

    public SQLNativeSegmentImpl(QueryRuntimeContext runtimeContext, String sqlSegment, SQLNativeExpressionContext sqlNativeExpressionContext) {
        super(runtimeContext,sqlSegment,sqlNativeExpressionContext);
        this.runtimeContext = runtimeContext;
        this.sqlSegment = sqlSegment;
        this.sqlNativeExpressionContext = sqlNativeExpressionContext;
    }

    @Override
    public String getAlias() {
        return sqlNativeExpressionContext.getAlias();
    }

    @Override
    public SQLEntitySegment cloneSQLColumnSegment() {
        return new SQLNativeSegmentImpl(runtimeContext, sqlSegment, sqlNativeExpressionContext);
    }

    public ColumnMetadata getColumnMetadata(){
        return null;
    }
}
