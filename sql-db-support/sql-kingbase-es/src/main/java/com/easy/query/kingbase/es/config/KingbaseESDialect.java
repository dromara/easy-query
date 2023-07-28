package com.easy.query.kingbase.es.config;

import com.easy.query.core.configuration.dialect.AbstractDialect;

/**
 * create time 2023/7/28 21:09
 * 文件说明
 *
 * @author xuejiaming
 */
public final class KingbaseESDialect extends AbstractDialect {
    @Override
    protected String getQuoteStart() {
        return "\"";
    }

    @Override
    protected String getQuoteEnd() {
        return "\"";
    }
}
