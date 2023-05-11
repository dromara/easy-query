package com.easy.query.pgsql;


import com.easy.query.core.sql.dialect.abstraction.AbstractDialect;

/**
 * create time 2023/5/4 09:03
 * 文件说明
 *
 * @author xuejiaming
 */
public final class PgSqlDialect extends AbstractDialect {
    @Override
    protected String getQuoteStart() {
        return "\"";
    }

    @Override
    protected String getQuoteEnd() {
        return "\"";
    }
}
