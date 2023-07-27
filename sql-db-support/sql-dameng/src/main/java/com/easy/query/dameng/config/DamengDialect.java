package com.easy.query.dameng.config;

import com.easy.query.core.configuration.dialect.AbstractDialect;

/**
 * create time 2023/7/27 10:48
 * 文件说明
 *
 * @author xuejiaming
 */
public final class DamengDialect extends AbstractDialect {
    @Override
    protected String getQuoteStart() {
        return "\"";
    }

    @Override
    protected String getQuoteEnd() {
        return "\"";
    }
}
