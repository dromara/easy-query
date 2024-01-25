package com.easy.query.core.expression.parser.core.base;

import com.easy.query.core.basic.api.select.ClientQueryable;

/**
 * create time 2023/7/18 17:16
 * 文件说明
 *
 * @author xuejiaming
 */
public interface FillSelector {

    <TREntity> ClientQueryable<TREntity> with(Class<TREntity> entityClass);
    <TREntity> ClientQueryable<TREntity> adapter(Class<TREntity> entityClass, ClientQueryable<TREntity> queryable);
    FillSelector consumeNull(boolean consumeNull);
}
