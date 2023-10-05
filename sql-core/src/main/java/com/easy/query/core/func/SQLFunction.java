package com.easy.query.core.func;

import com.easy.query.core.expression.parser.core.base.scec.SQLAliasNativePropertyExpressionContext;
import com.easy.query.core.expression.parser.core.base.scec.SQLNativePropertyExpressionContext;

/**
 * create time 2023/10/5 22:03
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLFunction {
    String sqlSegment();
    void consume(SQLNativePropertyExpressionContext context);
    void consume(SQLAliasNativePropertyExpressionContext context);
}
