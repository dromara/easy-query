package com.easy.query.core.expression.segment.scec.context;

import com.easy.query.core.expression.segment.scec.context.core.SQLNativeExpressionChain;

/**
 * create time 2023/7/29 21:44
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLAliasNativeExpressionContext extends SQLNativeExpressionChain<SQLAliasNativeExpressionContext> {
    SQLAliasNativeExpressionContext expressionAlias(String property);
    SQLAliasNativeExpressionContext setPropertyAlias(String property);
}
