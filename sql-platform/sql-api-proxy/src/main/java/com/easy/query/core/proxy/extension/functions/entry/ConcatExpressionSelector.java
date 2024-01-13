package com.easy.query.core.proxy.extension.functions.entry;

import com.easy.query.core.proxy.PropTypeColumn;

/**
 * create time 2024/1/13 21:20
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ConcatExpressionSelector {
    /**
     * 可以是值
     * @param val
     * @return
     */
    ConcatExpressionSelector concatWith(String val);

    /**
     * 可以是函数也可以是列
     * @param propTypeColumn
     * @return
     */
    ConcatExpressionSelector concatWith(PropTypeColumn<String> propTypeColumn);
}
