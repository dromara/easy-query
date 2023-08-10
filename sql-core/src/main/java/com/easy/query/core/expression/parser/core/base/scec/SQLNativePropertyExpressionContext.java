package com.easy.query.core.expression.parser.core.base.scec;

import com.easy.query.core.expression.parser.core.available.TableAvailable;

/**
 * create time 2023/7/29 22:57
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLNativePropertyExpressionContext {
    /**
     * 当前表的使用的列,自动识别所属表别名
     * @param property 使用哪个列
     * @return
     */
    SQLNativePropertyExpressionContext expression(String property);
    /**
     * 当前表的使用的列,自动识别所属表别名
     * @param table 哪张表
     * @param property 哪个列
     * @return
     */
    SQLNativePropertyExpressionContext expression(TableAvailable table, String property);
    SQLNativePropertyExpressionContext value(Object val);
    SQLNativePropertyExpressionContext constValue(Object constVal);
    SQLNativePropertyExpressionContext setAlias(String alias);
}
