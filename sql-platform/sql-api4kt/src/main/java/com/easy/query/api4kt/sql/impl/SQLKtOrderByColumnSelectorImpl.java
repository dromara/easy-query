package com.easy.query.api4kt.sql.impl;

import com.easy.query.api4kt.sql.SQLKtOrderBySelector;
import com.easy.query.core.expression.parser.core.base.ColumnOrderSelector;
import com.easy.query.core.expression.parser.core.base.core.SQLPropertyNative;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * create time 2023/6/16 22:26
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLKtOrderByColumnSelectorImpl<T> implements SQLKtOrderBySelector<T> {
    private final ColumnOrderSelector<T> orderByColumnSelector;

    public SQLKtOrderByColumnSelectorImpl(ColumnOrderSelector<T> orderByColumnSelector){

        this.orderByColumnSelector = orderByColumnSelector;
    }
    @Override
    public ColumnOrderSelector<T> getOrderBySelector() {
        return orderByColumnSelector;
    }

    @Override
    public <T1> SQLPropertyNative<T1> getSQLPropertyNative() {
        return EasyObjectUtil.typeCastNullable(orderByColumnSelector);
    }

    @Override
    public SQLKtOrderBySelector<T> castTChain() {
        return this;
    }
}
