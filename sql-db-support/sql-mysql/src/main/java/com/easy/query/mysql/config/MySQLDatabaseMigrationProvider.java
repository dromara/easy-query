package com.easy.query.mysql.config;

import com.easy.query.core.configuration.dialect.SQLKeyword;
import com.easy.query.core.migration.DefaultDatabaseMigrationProvider;

import javax.sql.DataSource;

/**
 * create time 2025/1/18 22:23
 * 文件说明
 *
 * @author xuejiaming
 */
public class MySQLDatabaseMigrationProvider extends DefaultDatabaseMigrationProvider {
    public MySQLDatabaseMigrationProvider(DataSource dataSource, SQLKeyword sqlKeyword) {
        super(dataSource, sqlKeyword);
    }
}
