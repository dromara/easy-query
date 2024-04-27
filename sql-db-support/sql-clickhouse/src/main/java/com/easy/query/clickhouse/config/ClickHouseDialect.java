package com.easy.query.clickhouse.config;

import com.easy.query.core.configuration.dialect.AbstractDialect;

/**
 * create time 2023/4/26 17:24
 * 文件说明
 *
 * @author xuejiaming
 */
public final class ClickHouseDialect extends AbstractDialect {
    @Override
    public String getQuoteStart() {
        return "`";
    }

    @Override
    public String getQuoteEnd() {
        return "`";
    }
}
