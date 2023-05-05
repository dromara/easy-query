package com.easy.query.core.sql.dialect.impl;

import com.easy.query.core.sql.dialect.Dialect;

/**
 * create time 2023/2/12 09:25
 * 没有方言
 *
 * @author xuejiaming
 */
public final class DefaultDialect implements Dialect {

    @Override
    public String getQuoteName(String keyword) {
        return keyword;
    }
}
