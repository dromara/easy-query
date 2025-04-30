package com.easy.query.mysql.migration;

import com.easy.query.core.configuration.dialect.SQLKeyword;
import com.easy.query.core.migration.DefaultDatabaseMigrationProvider;
import com.easy.query.core.migration.MigrationEntityParser;

import javax.sql.DataSource;

/**
 * create time 2025/1/18 22:23
 * 文件说明
 *
 * @author xuejiaming
 */
public class MySQLDatabaseMigrationProvider extends DefaultDatabaseMigrationProvider {
    public MySQLDatabaseMigrationProvider(DataSource dataSource, SQLKeyword sqlKeyword, MigrationEntityParser migrationEntityParser) {
        super(dataSource, sqlKeyword,migrationEntityParser);
    }
}
