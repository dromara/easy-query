package com.easy.query.api.proxy.core;

import com.easy.query.api.proxy.core.base.SQLAnd;

/**
 * create time 2023/6/21 22:04
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLSelectColumn<TSQLQuery> extends SQLAnd<TSQLQuery> {
    String value();
}
