package com.easy.query.core.expression.parser.core.base;

import com.easy.query.core.expression.builder.UpdateSetSelector;
import com.easy.query.core.expression.parser.core.available.TableAvailable;

/**
 * @author xuejiaming
 * @Description: 文件说明
 * @Date: 2023/2/6 23:20
 */
public interface ColumnUpdateSetSelector<T1> {
    UpdateSetSelector getUpdateSetSelector();
    TableAvailable getTable();

    ColumnUpdateSetSelector<T1> column(String property);

    ColumnUpdateSetSelector<T1> columnIgnore(String property);

    ColumnUpdateSetSelector<T1> columnAll();

    default <T2> ColumnUpdateSetSelector<T2> then(ColumnUpdateSetSelector<T2> sub) {
        return sub;
    }
}
