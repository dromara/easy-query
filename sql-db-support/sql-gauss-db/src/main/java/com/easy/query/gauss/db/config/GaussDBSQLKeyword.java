package com.easy.query.gauss.db.config;

import com.easy.query.core.configuration.dialect.AbstractSQLKeyword;

/**
 * create time 2023/7/28 21:09
 * 文件说明
 *
 * @author xuejiaming
 */
public final class GaussDBSQLKeyword extends AbstractSQLKeyword {
    @Override
    protected String getQuoteStart() {
        return "\"";
    }

    @Override
    protected String getQuoteEnd() {
        return "\"";
    }
}
