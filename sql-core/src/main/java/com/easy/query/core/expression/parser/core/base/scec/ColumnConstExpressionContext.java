package com.easy.query.core.expression.parser.core.base.scec;

import com.easy.query.core.expression.parser.core.available.TableAvailable;

/**
 * create time 2023/7/29 22:57
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnConstExpressionContext {
    ColumnConstExpressionContext expression(String property);
    ColumnConstExpressionContext expression(TableAvailable table, String property);
    ColumnConstExpressionContext value(Object val);
    ColumnConstExpressionContext setAlias(String alias);
}
