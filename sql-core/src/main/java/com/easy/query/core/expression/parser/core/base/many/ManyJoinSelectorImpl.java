package com.easy.query.core.expression.parser.core.base.many;

import com.easy.query.core.expression.parser.core.available.TableAvailable;

/**
 * create time 2025/3/9 22:28
 * 文件说明
 *
 * @author xuejiaming
 */
public class ManyJoinSelectorImpl<T1> implements ManyJoinSelector<T1> {
    private final TableAvailable table;

    public ManyJoinSelectorImpl(TableAvailable table) {
        this.table = table;
    }

    @Override
    public ManyColumn manyColumn(String property) {
        return new ManyColumnImpl(table, property);
    }

    @Override
    public ManyColumn manyColumn(TableAvailable table, String property) {
        return new ManyColumnImpl(table, property);
    }
}
