package com.easy.query.api4j.sql;

import com.easy.query.api4j.select.Queryable;

/**
 * create time 2023/7/18 17:19
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLFillSelector {
    <TREntity> Queryable<TREntity> with(Class<TREntity> entityClass);

}
