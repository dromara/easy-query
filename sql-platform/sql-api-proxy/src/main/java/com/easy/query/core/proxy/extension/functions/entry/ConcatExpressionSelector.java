package com.easy.query.core.proxy.extension.functions.entry;

import com.easy.query.core.proxy.PropTypeColumn;

/**
 * create time 2024/1/13 21:20
 * concat函数表达式选择器
 *
 * @author xuejiaming
 */
public interface ConcatExpressionSelector {
    /**
     * 请使用{@link #value(String)}
     * @param val
     * @return
     */
    @Deprecated
    default ConcatExpressionSelector concatWith(String val){
        return value(val);
    }
    /**
     * 可以是值
     * @param val 值
     * @return 返回当前选择器
     */
    ConcatExpressionSelector value(String val);

    /**
     * 请使用{@link #expression(PropTypeColumn)}
     * @param propTypeColumn 列或者函数
     * @return 返回当前选择器
     */
    @Deprecated
   default ConcatExpressionSelector concatWith(PropTypeColumn<String> propTypeColumn){
       return expression(propTypeColumn);
   }

    /**
     * 可以是函数也可以是列
     * @param propTypeColumn 列或者函数
     * @return 返回当前选择器
     */
    ConcatExpressionSelector expression(PropTypeColumn<String> propTypeColumn);
}
