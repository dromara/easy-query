package com.easy.query.core.expression.parser.core.base;

import com.easy.query.core.expression.builder.OnlySelector;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;

/**
 * @author xuejiaming
 * @Description: 文件说明
 * @Date: 2023/2/6 23:20
 */
public interface ColumnOnlySelector<T1> extends EntitySQLTableOwner<T1> {
    OnlySelector getOnlySelector();

    ColumnOnlySelector<T1> columnKeys();
    ColumnOnlySelector<T1> column(String property);

    ColumnOnlySelector<T1> columnIgnore(String property);

    ColumnOnlySelector<T1> columnAll();

    default <T2> ColumnOnlySelector<T2> then(ColumnOnlySelector<T2> sub) {
        return sub;
    }
}
