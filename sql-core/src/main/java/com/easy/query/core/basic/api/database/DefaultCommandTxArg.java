package com.easy.query.core.basic.api.database;

import java.util.function.Consumer;

/**
 * create time 2025/1/26 08:41
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultCommandTxArg implements CommandTxArg {
    private final String sql;

    private final Consumer<String> commit;

    public DefaultCommandTxArg(String sql, Consumer<String> commit) {
        this.sql = sql;
        this.commit = commit;
    }

    @Override
    public String getSQL() {
        return sql;
    }

    @Override
    public void commit() {
        commit.accept(sql);
    }

}
