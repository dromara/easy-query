package com.easy.query.core.proxy.extension.functions.entry;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.func.column.ColumnFuncSelector;
import com.easy.query.core.proxy.PropTypeColumn;

/**
 * create time 2024/1/13 21:20
 * concat函数表达式选择器
 *
 * @author xuejiaming
 */
public interface ConcatExpressionSelector {
    ColumnFuncSelector getColumnFuncSelector();
    /**
     * 可以是值
     * @param val 值
     * @return 返回当前选择器
     */
    ConcatExpressionSelector value(String val);

    /**
     * 格式化处理
     * @param valFormat
     * @return
     */
    ConcatExpressionSelector format(Object valFormat);

    /**
     * 接受子查询
     * @param subQuery
     * @return
     */
    ConcatExpressionSelector subQuery(Query<?> subQuery);


    /**
     * 可以是函数也可以是列
     * @param propTypeColumn 列或者函数
     * @return 返回当前选择器
     */
    ConcatExpressionSelector expression(PropTypeColumn<String> propTypeColumn);

    static void accept(ConcatExpressionSelector concatExpressionSelector,Object val){
        if(val==null){
            concatExpressionSelector.value(null);
        } else if(val instanceof PropTypeColumn){
            PropTypeColumn.columnFuncSelector(concatExpressionSelector.getColumnFuncSelector(), (PropTypeColumn<?>) val);
        }else{
            concatExpressionSelector.getColumnFuncSelector().value(val);
        }
    }
}
