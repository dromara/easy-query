package com.easy.query.api4kt.sql;

import com.easy.query.api4kt.select.KtQueryable;

/**
 * create time 2023/7/19 10:00
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtFillSelector {
    <TREntity> KtQueryable<TREntity> with(Class<TREntity> entityClass);
}
