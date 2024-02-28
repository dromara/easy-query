package com.easy.query.core.expression.segment.impl;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.segment.SQLEntitySegment;
import com.easy.query.core.expression.segment.SQLLazySegement;
import com.easy.query.core.expression.segment.SQLNativeSegment;
import com.easy.query.core.expression.segment.scec.context.core.SQLNativeExpression;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.metadata.ColumnMetadata;

import java.util.function.Function;

/**
 * create time 2023/6/16 20:55
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLNativeLazySegmentImpl extends AbstractSQLNativeLazySegmentImpl implements SQLNativeSegment {
    protected final QueryRuntimeContext runtimeContext;
    protected final SQLNativeExpression sqlNativeExpression;

    public SQLNativeLazySegmentImpl(QueryRuntimeContext runtimeContext, ExpressionContext expressionContext, SQLLazySegement sqlLazySegement, Function<String,String> sqlSegmentFunction, SQLNativeExpression sqlNativeExpression) {
        super(runtimeContext,expressionContext,sqlLazySegement,sqlSegmentFunction,sqlNativeExpression);
        this.runtimeContext = runtimeContext;
        this.sqlNativeExpression = sqlNativeExpression;
    }

    @Override
    public String getAlias() {
        return sqlNativeExpression.getAlias();
    }

    @Override
    public SQLEntitySegment cloneSQLColumnSegment() {
        return new SQLNativeLazySegmentImpl(runtimeContext, expressionContext,sqlLazySegement,sqlSegmentFunction,sqlNativeExpression);
    }

    public ColumnMetadata getColumnMetadata(){
        return null;
    }
}
