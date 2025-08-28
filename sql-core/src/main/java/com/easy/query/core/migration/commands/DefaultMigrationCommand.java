package com.easy.query.core.migration.commands;

import com.easy.query.core.migration.MigrationCommand;

/**
 * create time 2025/1/14 14:20
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultMigrationCommand implements MigrationCommand {
    private final String sql;

    public DefaultMigrationCommand(String sql){
        this.sql = sql;
    }

    @Override
    public String toSQL() {
        return sql;
    }
}
