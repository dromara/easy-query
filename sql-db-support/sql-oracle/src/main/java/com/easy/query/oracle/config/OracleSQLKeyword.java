package com.easy.query.oracle.config;

import com.easy.query.core.configuration.dialect.AbstractSQLKeyword;

/**
 * create time 2023/8/15 17:16
 * 文件说明
 *
 * @author xuejiaming
 */
public class OracleSQLKeyword extends AbstractSQLKeyword {
    @Override
    protected String getQuoteStart() {
        return "\"";
    }

    @Override
    protected String getQuoteEnd() {
        return "\"";
    }
}
