package com.easy.query.core.basic.api.database;

/**
 * create time 2025/1/26 08:41
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultCodeFirstCommandArg implements CodeFirstCommandArg {
    private final String sql;

    public DefaultCodeFirstCommandArg(String sql){
        this.sql = sql;
    }

    @Override
    public String getSQL() {
        return sql;
    }
}
