package com.easy.query.tsdb.config;


import com.easy.query.core.configuration.dialect.AbstractSQLKeyword;

/**
 * create time 2023/5/4 09:03
 * 文件说明
 *
 * @author xuejiaming
 */
public final class TSDBSQLKeyword extends AbstractSQLKeyword {
    @Override
    protected String getQuoteStart() {
        return "`";
    }

    @Override
    protected String getQuoteEnd() {
        return "`";
    }
}
