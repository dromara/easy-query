package com.easy.query.gauss.db.config;

import com.easy.query.core.configuration.dialect.AbstractDialect;

/**
 * create time 2023/7/28 21:09
 * 文件说明
 *
 * @author xuejiaming
 */
public final class GaussDBDialect extends AbstractDialect {
    @Override
    protected String getQuoteStart() {
        return "\"";
    }

    @Override
    protected String getQuoteEnd() {
        return "\"";
    }
}
