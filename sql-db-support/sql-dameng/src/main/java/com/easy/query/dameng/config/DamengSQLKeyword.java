package com.easy.query.dameng.config;

import com.easy.query.core.configuration.dialect.AbstractSQLKeyword;

/**
 * create time 2023/7/27 10:48
 * 文件说明
 *
 * @author xuejiaming
 */
public final class DamengSQLKeyword extends AbstractSQLKeyword {
    @Override
    protected String getQuoteStart() {
        return "\"";
    }

    @Override
    protected String getQuoteEnd() {
        return "\"";
    }
}
