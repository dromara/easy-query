package com.easy.query.core.expression.parser.core.base;

import com.easy.query.core.expression.parser.core.available.TableAvailable;

/**
 * @author xuejiaming
 * @Description: 文件说明
 * @Date: 2023/2/6 23:20
 */
public interface ColumnSetSelector<T1> {
    TableAvailable getTable();

    ColumnSetSelector<T1> column(String property);

    ColumnSetSelector<T1> columnIgnore(String property);

    ColumnSetSelector<T1> columnAll();

    default <T2> ColumnSetSelector<T2> then(ColumnSetSelector<T2> sub) {
        return sub;
    }
}
