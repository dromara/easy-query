package com.easy.query.api4kt.sql.impl;

import com.easy.query.api4kt.select.KtQueryable;
import com.easy.query.api4kt.select.impl.EasyKtQueryable;
import com.easy.query.api4kt.sql.SQLKtFillSelector;
import com.easy.query.core.expression.parser.core.base.FillSelector;

/**
 * create time 2023/7/18 22:37
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLKtFillSelectorImpl implements SQLKtFillSelector {
    private final FillSelector fillSelector;

    public SQLKtFillSelectorImpl(FillSelector fillSelector){

        this.fillSelector = fillSelector;
    }
    @Override
    public <TREntity> KtQueryable<TREntity> with(Class<TREntity> entityClass) {
        return new EasyKtQueryable<>(fillSelector.with(entityClass));
    }
}
