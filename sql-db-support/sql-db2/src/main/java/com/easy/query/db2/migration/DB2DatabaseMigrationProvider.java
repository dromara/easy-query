package com.easy.query.db2.migration;

import com.easy.query.core.basic.api.database.TableInfo;
import com.easy.query.core.configuration.dialect.SQLKeyword;
import com.easy.query.core.migration.DefaultDatabaseMigrationProvider;
import com.easy.query.core.migration.MigrationEntityParser;
import com.easy.query.core.util.EasyDatabaseUtil;
import com.easy.query.core.util.EasyStringUtil;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * create time 2026/5/11 08:39
 * 文件说明
 *
 * @author xuejiaming
 */
public class DB2DatabaseMigrationProvider extends DefaultDatabaseMigrationProvider {
    public DB2DatabaseMigrationProvider(DataSource dataSource, SQLKeyword sqlKeyword, MigrationEntityParser migrationEntityParser) {
        super(dataSource, sqlKeyword, migrationEntityParser);
    }


    @Override
    protected String tableQuerySql() {
        return "SELECT tabschema AS table_schema, tabname AS table_name FROM syscat.tables WHERE tabschema = ?  AND type = 'T'";
    }

}
