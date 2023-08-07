package com.easy.query.core.expression.sql.builder;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.segment.scec.context.SQLNativeExpressionContext;

/**
 * create time 2023/8/7 11:12
 * 文件说明
 *
 * @author xuejiaming
 */
public class ColumnConfigurerContext {
    private final QueryRuntimeContext runtimeContext;
    private final String sqlSegment;
    private final SQLNativeExpressionContext sqlNativeExpressionContext;

    public ColumnConfigurerContext(QueryRuntimeContext runtimeContext, String sqlSegment, SQLNativeExpressionContext sqlNativeExpressionContext){

        this.runtimeContext = runtimeContext;
        this.sqlSegment = sqlSegment;
        this.sqlNativeExpressionContext = sqlNativeExpressionContext;
    }

    public QueryRuntimeContext getRuntimeContext() {
        return runtimeContext;
    }

    public String getSqlSegment() {
        return sqlSegment;
    }

    public SQLNativeExpressionContext getSqlNativeExpressionContext() {
        return sqlNativeExpressionContext;
    }
}
