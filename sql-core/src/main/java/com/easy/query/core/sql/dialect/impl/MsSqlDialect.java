package com.easy.query.core.sql.dialect.impl;

import com.easy.query.core.sql.dialect.abstraction.AbstractDialect;

/**
 * create time 2023/5/4 08:30
 * sql server 方言
 *
 * @author xuejiaming
 */
public final class MsSqlDialect extends AbstractDialect {
    @Override
    protected String getQuoteStart() {
        return "[";
    }

    @Override
    protected String getQuoteEnd() {
        return "]";
    }
}
