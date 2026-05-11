package com.easy.query.mysql.migration;

import com.easy.query.core.basic.api.database.TableInfo;
import com.easy.query.core.configuration.dialect.SQLKeyword;
import com.easy.query.core.migration.DefaultDatabaseMigrationProvider;
import com.easy.query.core.migration.MigrationEntityParser;
import com.easy.query.core.util.EasyDatabaseUtil;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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
