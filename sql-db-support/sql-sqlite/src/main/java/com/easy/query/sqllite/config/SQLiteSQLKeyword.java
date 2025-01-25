package com.easy.query.sqllite.config;


import com.easy.query.core.configuration.dialect.AbstractSQLKeyword;

/**
 * create time 2023/5/4 08:30
 * sql server 方言
 *
 * @author xuejiaming
 */
public final class SQLiteSQLKeyword extends AbstractSQLKeyword {
    @Override
    protected String getQuoteStart() {
        return "\"";
    }

    @Override
    protected String getQuoteEnd() {
        return "\"";
    }
}
