package com.easy.query.h2.config;

import com.easy.query.core.configuration.dialect.AbstractSQLKeyword;

/**
 * create time 2025/8/21 21:24
 * 文件说明
 *
 * @author xuejiaming
 */
public class H2SQLKeyword extends AbstractSQLKeyword {
    @Override
    protected String getQuoteStart() {
        return "\"";
    }

    @Override
    protected String getQuoteEnd() {
        return "\"";
    }
}
