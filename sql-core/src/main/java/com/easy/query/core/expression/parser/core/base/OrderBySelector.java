package com.easy.query.core.expression.parser.core.base;

import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.parser.core.available.TableAvailable;

/**
 * create time 2023/6/16 21:19
 * 文件说明
 *
 * @author xuejiaming
 */
public interface OrderBySelector<T1> {
    TableAvailable getTable();

    OrderBySelector<T1> column(String property);

    OrderBySelector<T1> columnFunc(ColumnPropertyFunction columnPropertyFunction);
    OrderBySelector<T1> columnConst(String columnConst);

    OrderBySelector<T1> columnIgnore(String property);

    OrderBySelector<T1> columnAll();

    default <T2> OrderBySelector<T2> then(OrderBySelector<T2> sub) {
        return sub;
    }
}
