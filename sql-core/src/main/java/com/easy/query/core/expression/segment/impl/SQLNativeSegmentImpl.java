package com.easy.query.core.expression.segment.impl;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.segment.SQLEntitySegment;
import com.easy.query.core.expression.segment.SQLNativeSegment;
import com.easy.query.core.expression.segment.scec.context.core.SQLNativeExpression;
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
    protected final SQLNativeExpression sqlNativeExpression;

    public SQLNativeSegmentImpl(QueryRuntimeContext runtimeContext, String sqlSegment, SQLNativeExpression sqlNativeExpression) {
        super(runtimeContext,sqlSegment,sqlNativeExpression);
        this.runtimeContext = runtimeContext;
        this.sqlSegment = sqlSegment;
        this.sqlNativeExpression = sqlNativeExpression;
    }

    @Override
    public String getAlias() {
        return sqlNativeExpression.getAlias();
    }

    @Override
    public SQLEntitySegment cloneSQLColumnSegment() {
        return new SQLNativeSegmentImpl(runtimeContext, sqlSegment, sqlNativeExpression);
    }

    public ColumnMetadata getColumnMetadata(){
        return null;
    }
}
