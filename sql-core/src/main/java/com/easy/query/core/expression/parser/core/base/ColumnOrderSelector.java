package com.easy.query.core.expression.parser.core.base;

import com.easy.query.core.expression.SQLTableOwner;
import com.easy.query.core.expression.builder.OrderSelector;
import com.easy.query.core.expression.func.ColumnPropertyFunction;

/**
 * create time 2023/6/16 21:19
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnOrderSelector<T1> extends SQLTableOwner {
    OrderSelector getOrderSelector();

    ColumnOrderSelector<T1> column(String property);

    ColumnOrderSelector<T1> columnFunc(ColumnPropertyFunction columnPropertyFunction);
    ColumnOrderSelector<T1> columnConst(String columnConst);

    default <T2> ColumnOrderSelector<T2> then(ColumnOrderSelector<T2> sub) {
        return sub;
    }
}
