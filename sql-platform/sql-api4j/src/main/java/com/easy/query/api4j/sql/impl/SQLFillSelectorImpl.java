package com.easy.query.api4j.sql.impl;

import com.easy.query.api4j.select.Queryable;
import com.easy.query.api4j.select.impl.EasyQueryable;
import com.easy.query.api4j.sql.SQLFillSelector;
import com.easy.query.core.expression.parser.core.base.FillSelector;

/**
 * create time 2023/7/18 22:37
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLFillSelectorImpl implements SQLFillSelector {
    private final FillSelector fillSelector;

    public SQLFillSelectorImpl(FillSelector fillSelector){

        this.fillSelector = fillSelector;
    }
    @Override
    public <TREntity> Queryable<TREntity> with(Class<TREntity> entityClass) {
        return new EasyQueryable<>(fillSelector.with(entityClass));
    }
}
