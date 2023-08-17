package com.easy.query.api4j.select.extension.queryable;

import com.easy.query.api4j.select.Queryable;

/**
 * create time 2023/8/17 11:14
 * 文件说明
 *
 * @author xuejiaming
 */
public interface QueryableAvailable<T1> {
    Queryable<T1> getQueryable();
}
