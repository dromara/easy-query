package com.easy.query.test.migration;

import com.easy.query.api.proxy.client.DefaultEasyEntityQuery;
import com.easy.query.api.proxy.client.EasyEntityQuery;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.api.database.DatabaseCodeFirst;
import com.easy.query.core.bootstrapper.EasyQueryBootstrapper;
import com.easy.query.core.migration.data.ColumnMigrationData;
import com.easy.query.core.migration.data.IndexMigrationData;
import com.easy.query.core.migration.data.TableMigrationData;
import com.easy.query.core.util.EasyDatabaseUtil;
import com.easy.query.pgsql.config.PgSQLDatabaseConfiguration;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MigrationPgIndexTest {

    @Test
    public void mixedCaseIndexAndCommentAreIdempotent() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:postgresql://127.0.0.1:54321/easy-query-test?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres");
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setMaximumPoolSize(5);
        EasyQueryClient easyQueryClient = EasyQueryBootstrapper.defaultBuilderConfiguration()
                .setDefaultDataSource(dataSource)
                .useDatabaseConfigure(new PgSQLDatabaseConfiguration())
                .build();
        EasyEntityQuery easyEntityQuery = new DefaultEasyEntityQuery(easyQueryClient);
        DatabaseCodeFirst databaseCodeFirst = easyEntityQuery.getDatabaseCodeFirst();
        TableMigrationData table = migrationTable();
        databaseCodeFirst.dropTableIfExistsCommandByMigrationData(Collections.singletonList(table))
                .forEach(arg -> arg.commit());
        try {
            List<String> statements = new ArrayList<>();
            databaseCodeFirst.syncTableCommandByMigrationData(Collections.singletonList(table)).forEach(arg -> {
                statements.add(arg.getSQL());
                arg.commit();
            });

            Assert.assertEquals(5, statements.size());
            Assert.assertTrue(statements.get(0).startsWith("CREATE TABLE IF NOT EXISTS \"public\".\"t_migration_index\""));
            Assert.assertEquals("COMMENT ON TABLE \"public\".\"t_migration_index\" IS '索引''表';", statements.get(1));
            Assert.assertEquals("COMMENT ON COLUMN \"public\".\"t_migration_index\".\"name\" IS '名称';", statements.get(2));
            Assert.assertEquals("CREATE INDEX \"IX_Migration_Mixed\" ON \"public\".\"t_migration_index\" (\"name\" ASC);", statements.get(3));
            Assert.assertEquals("COMMENT ON INDEX \"public\".\"IX_Migration_Mixed\" IS '混合''索引';", statements.get(4));

            List<Map<String, Object>> indexes = EasyDatabaseUtil.sqlQuery(dataSource,
                    "select i.relname as index_name, d.description as index_comment " +
                            "from pg_class i join pg_namespace n on n.oid = i.relnamespace " +
                            "left join pg_description d on d.objoid = i.oid and d.objsubid = 0 " +
                            "where n.nspname = ? and i.relname = ?",
                    Arrays.asList("public", "IX_Migration_Mixed"));
            Assert.assertEquals(1, indexes.size());
            Assert.assertEquals("IX_Migration_Mixed", indexes.get(0).get("index_name"));
            Assert.assertEquals("混合'索引", indexes.get(0).get("index_comment"));

            int[] syncCommands = {0};
            databaseCodeFirst.syncTableCommandByMigrationData(Collections.singletonList(table)).forEach(arg -> {
                syncCommands[0]++;
                arg.commit();
            });
            Assert.assertEquals(0, syncCommands[0]);
        } finally {
            databaseCodeFirst.dropTableIfExistsCommandByMigrationData(Collections.singletonList(table))
                    .forEach(arg -> arg.commit());
            dataSource.close();
        }
    }

    private static TableMigrationData migrationTable() {
        TableMigrationData table = new TableMigrationData();
        table.setSchema("public");
        table.setTableName("t_migration_index");
        table.setComment("索引'表");
        ColumnMigrationData id = new ColumnMigrationData();
        id.setName("id");
        id.setDbType("INT8");
        id.setPrimary(true);
        id.setNotNull(true);
        ColumnMigrationData name = new ColumnMigrationData();
        name.setName("name");
        name.setDbType("VARCHAR(100)");
        name.setComment("名称");
        table.setColumns(new ArrayList<>(Arrays.asList(id, name)));

        IndexMigrationData.EntityField field = new IndexMigrationData.EntityField();
        field.setColumnName("name");
        field.setAsc(true);
        IndexMigrationData index = new IndexMigrationData();
        index.setIndexName("IX_Migration_Mixed");
        index.setComment("混合'索引");
        index.setFields(Collections.singletonList(field));
        table.setIndexes(Collections.singletonList(index));
        return table;
    }
}
