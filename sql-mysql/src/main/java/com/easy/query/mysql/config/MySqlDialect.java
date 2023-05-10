package com.easy.query.mysql.config;

import com.easy.query.core.sql.dialect.abstraction.AbstractDialect;

/**
 * create time 2023/4/26 17:24
 * 文件说明
 *
 * @author xuejiaming
 */
public final class MySqlDialect extends AbstractDialect {
    @Override
    public String getQuoteStart() {
        return "`";
    }

    @Override
    public String getQuoteEnd() {
        return "`";
    }
}
