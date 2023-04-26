package com.easy.query.core.config;

/**
 * create time 2023/4/26 17:24
 * 文件说明
 *
 * @author xuejiaming
 */
public class MySqlDialect extends AbstractDialect{
    @Override
    public String getQuoteStart() {
        return "`";
    }

    @Override
    public String getQuoteEnd() {
        return "`";
    }
}
