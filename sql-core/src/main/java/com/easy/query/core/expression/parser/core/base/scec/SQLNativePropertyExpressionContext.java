package com.easy.query.core.expression.parser.core.base.scec;

import com.easy.query.core.expression.parser.core.available.TableAvailable;

/**
 * create time 2023/7/29 22:57
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLNativePropertyExpressionContext {
    SQLNativePropertyExpressionContext expression(String property);
    SQLNativePropertyExpressionContext expression(TableAvailable table, String property);
    SQLNativePropertyExpressionContext value(Object val);
    SQLNativePropertyExpressionContext constValue(Object constVal);
    SQLNativePropertyExpressionContext setAlias(String alias);
}
