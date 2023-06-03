package com.easy.query.api4kt.sql.impl;

import com.easy.query.api4kt.sql.SQLKtColumnResultSelector;
import com.easy.query.core.expression.parser.core.base.ColumnResultSelector;

/**
 * @author xuejiaming
 * @FileName: DefaultColumnResultSelector.java
 * @Description: 文件说明
 * @Date: 2023/3/8 21:35
 */
public class SQLKtColumnResultSelectorImpl<T1, TR> implements SQLKtColumnResultSelector<T1, TR> {
    private final ColumnResultSelector<T1> columnResultSelector;

    public SQLKtColumnResultSelectorImpl(ColumnResultSelector<T1> columnResultSelector) {
        this.columnResultSelector = columnResultSelector;
    }

    @Override
    public ColumnResultSelector<T1> getColumnResultSelector() {
        return columnResultSelector;
    }
}
