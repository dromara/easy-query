package com.easy.query.core.expression.parser.core.base;

import com.easy.query.core.expression.builder.GroupSelector;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.parser.core.available.TableAvailable;

/**
 * create time 2023/4/30 21:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnGroupSelector<T1> {
    GroupSelector getGroupSelector();
    TableAvailable getTable();

    ColumnGroupSelector<T1> column(String property);
    ColumnGroupSelector<T1> columnConst(String columnConst);

    ColumnGroupSelector<T1> columnFunc(ColumnPropertyFunction columnPropertyFunction);

    default <T2> ColumnGroupSelector<T2> then(ColumnGroupSelector<T2> sub) {
        return sub;
    }
}
