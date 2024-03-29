package com.easy.query.core.proxy.sql.scec;

import com.easy.query.core.expression.segment.scec.context.SQLNativeExpressionContext;

/**
 * create time 2023/7/29 23:38
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLNativeProxyExpressionContext extends SQLNativeProxyExpressionChain<SQLNativeProxyExpressionContext>{
    SQLNativeExpressionContext getSQLNativeExpressionContext();
}
