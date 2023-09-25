package com.easy.query.api4j.sql.impl;

import com.easy.query.api4j.sql.SQLOrderBySelector;
import com.easy.query.core.expression.parser.core.base.ColumnOrderSelector;
import com.easy.query.core.expression.parser.core.base.core.SQLPropertyNative;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * create time 2023/6/16 22:26
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLOrderByColumnSelectorImpl<T> implements SQLOrderBySelector<T> {
    private final ColumnOrderSelector<T> orderByColumnSelector;

    public SQLOrderByColumnSelectorImpl(ColumnOrderSelector<T> orderByColumnSelector){

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
    public SQLOrderBySelector<T> castChain() {
        return this;
    }
}
