package com.easy.query.core.expression.parser.core.base;

import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.parser.core.available.TableAvailable;

/**
 * create time 2023/4/30 21:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface GroupBySelector<T1> {
    TableAvailable getTable();

    GroupBySelector<T1> column(String property);

    GroupBySelector<T1> columnFunc(ColumnPropertyFunction columnPropertyFunction);

    default <T2> GroupBySelector<T2> and(GroupBySelector<T2> sub) {
        return sub;
    }
}
