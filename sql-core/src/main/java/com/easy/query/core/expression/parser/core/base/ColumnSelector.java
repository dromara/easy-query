package com.easy.query.core.expression.parser.core.base;

import com.easy.query.core.expression.builder.Selector;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.parser.core.available.TableAvailable;

/**
 * @author xuejiaming
 * @Description: 文件说明
 * @Date: 2023/2/6 23:20
 */
public interface ColumnSelector<T1> {
    Selector getSelector();
    TableAvailable getTable();

    ColumnSelector<T1> column(String property);

    ColumnSelector<T1> columnFunc(ColumnPropertyFunction columnPropertyFunction);

    ColumnSelector<T1> columnIgnore(String property);

    ColumnSelector<T1> columnAll();

    default <T2> ColumnSelector<T2> then(ColumnSelector<T2> sub) {
        return sub;
    }
}
