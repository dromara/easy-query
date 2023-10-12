package com.easy.query.core.func;

import com.easy.query.core.expression.parser.core.base.scec.core.SQLNativeChainExpressionContext;

/**
 * create time 2023/10/5 22:03
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLFunction {
    String sqlSegment();
    int paramMarks();
    void setAlias(String alias);
    void setPropertyAlias(String propertyAlias);
    void consume(SQLNativeChainExpressionContext context);
}
