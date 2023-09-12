package com.easy.query.api4kt.sql.impl;

import com.easy.query.api4kt.sql.SQLKtColumnAsSelector;
import com.easy.query.core.expression.parser.core.base.ColumnAsSelector;
import com.easy.query.core.expression.parser.core.base.core.SQLAsPropertyNative;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * @author xuejiaming
 * @FileName: DefaultSqSelector.java
 * @Description: 文件说明
 * @Date: 2023/2/8 00:10
 */
public class SQLKtColumnAsSelectorImpl<T1, TR> implements SQLKtColumnAsSelector<T1, TR> {
    private final ColumnAsSelector<T1, TR> columnAsSelector;

    public SQLKtColumnAsSelectorImpl(ColumnAsSelector<T1, TR> columnAsSelector) {
        this.columnAsSelector = columnAsSelector;
    }

    @Override
    public ColumnAsSelector<T1, TR> getColumnAsSelector() {
        return columnAsSelector;
    }

    @Override
    public <T> SQLAsPropertyNative<T> getSQLAsPropertyNative() {
        return EasyObjectUtil.typeCastNullable(columnAsSelector);
    }

    @Override
    public SQLKtColumnAsSelector<T1, TR> castTChain() {
        return this;
    }
}
