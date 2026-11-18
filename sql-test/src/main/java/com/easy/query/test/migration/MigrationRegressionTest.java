package com.easy.query.test.migration;

import com.easy.query.core.configuration.dialect.DefaultSQLKeyword;
import com.easy.query.core.exception.EasyQuerySQLCommandException;
import com.easy.query.core.inject.ServiceProvider;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.migration.AbstractDatabaseMigrationProvider;
import com.easy.query.core.migration.ColumnDbTypeResult;
import com.easy.query.core.migration.DatabaseMigrationProvider;
import com.easy.query.core.migration.DefaultDatabaseMigrationProvider;
import com.easy.query.core.migration.DefaultMigrationsSQLGenerator;
import com.easy.query.core.migration.EntityMigrationMetadata;
import com.easy.query.core.migration.MigrationCommand;
import com.easy.query.core.migration.MigrationEntityParser;
import com.easy.query.core.migration.TableForeignKeyResult;
import com.easy.query.core.migration.TableIndexResult;
import com.easy.query.core.migration.data.ColumnMigrationData;
import com.easy.query.core.migration.data.IndexMigrationData;
import com.easy.query.core.migration.data.TableMigrationData;
import com.easy.query.core.util.EasyDatabaseUtil;
import com.easy.query.duckdb.config.DuckDBSQLKeyword;
import com.easy.query.duckdb.migration.DuckDBSQLDatabaseMigrationProvider;
import com.easy.query.duckdb.migration.DuckDBSQLMigrationEntityParser;
import com.easy.query.gauss.db.config.GaussDBSQLKeyword;
import com.easy.query.gauss.db.migration.GaussDBDatabaseMigrationProvider;
import com.easy.query.gauss.db.migration.GaussDBMigrationEntityParser;
import com.easy.query.h2.config.H2SQLKeyword;
import com.easy.query.h2.migration.H2DatabaseMigrationProvider;
import com.easy.query.h2.migration.H2MigrationEntityParser;
import com.easy.query.kingbase.es.config.KingbaseESSQLKeyword;
import com.easy.query.kingbase.es.migration.KingbaseESDatabaseMigrationProvider;
import com.easy.query.kingbase.es.migration.KingbaseESMigrationEntityParser;
import com.easy.query.mssql.config.MsSQLSQLKeyword;
import com.easy.query.mssql.migration.MsSQLDatabaseMigrationProvider;
import com.easy.query.mssql.migration.MsSQLMigrationEntityParser;
import com.easy.query.mysql.config.MySQLSQLKeyword;
import com.easy.query.mysql.migration.MySQLDatabaseMigrationProvider;
import com.easy.query.mysql.migration.MySQLMigrationEntityParser;
import com.easy.query.oracle.config.OracleSQLKeyword;
import com.easy.query.oracle.migration.OracleDatabaseMigrationProvider;
import com.easy.query.oracle.migration.OracleMigrationEntityParser;
import com.easy.query.pgsql.config.PgSQLSQLKeyword;
import com.easy.query.pgsql.migration.PgSQLDatabaseMigrationProvider;
import com.easy.query.pgsql.migration.PgSQLMigrationEntityParser;
import com.easy.query.sqlite.config.SQLiteSQLKeyword;
import com.easy.query.sqlite.migration.SQLiteDatabaseMigrationProvider;
import com.easy.query.sqlite.migration.SQLiteMigrationEntityParser;
import org.junit.Assert;
import org.junit.Test;

import javax.sql.DataSource;
import java.lang.reflect.Proxy;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MigrationRegressionTest {

    @Test
    public void msSQLRenameTableUsesSpRename() {
        MsSQLDatabaseMigrationProvider provider = new MsSQLDatabaseMigrationProvider(
                null, new MsSQLSQLKeyword(), new MsSQLMigrationEntityParser());
        TableMigrationData table = table(null, "new_table");
        table.setOldTableName("old_table");

        Assert.assertEquals("EXEC sp_rename N'[dbo].[old_table]', N'new_table'", provider.renameTable(table).toSQL());

        table.setSchema("sales");
        table.setTableName("new'table");
        Assert.assertEquals("EXEC sp_rename N'[sales].[old_table]', N'new''table'", provider.renameTable(table).toSQL());
    }

    @Test
    public void oracleDefaultPrecedesNotNullAndTriggerUsesLF() {
        OracleDatabaseMigrationProvider provider = new OracleDatabaseMigrationProvider(
                null, new OracleSQLKeyword(), new OracleMigrationEntityParser());
        TableMigrationData table = table(null, "t_oracle");
        ColumnMigrationData id = column("id", "NUMBER(19)");
        id.setPrimary(true);
        id.setGeneratedKey(true);
        id.setNotNull(true);
        ColumnMigrationData amount = column("amount", "NUMBER(10)");
        amount.setNotNull(true);
        amount.setDefValue("0");
        table.getColumns().add(id);
        table.getColumns().add(amount);

        List<String> statements = provider.createTable(table).toSQLList();

        Assert.assertEquals(3, statements.size());
        Assert.assertTrue(statements.get(0).contains("\"amount\" NUMBER(10) DEFAULT 0 NOT NULL"));
        Assert.assertFalse(statements.get(2).contains("\r"));
        Assert.assertTrue(statements.get(2).contains("FOR EACH ROW\nBEGIN\n"));
    }

    @Test
    public void pgFamilyCreateTableSplitsAndEscapesComments() {
        for (AbstractDatabaseMigrationProvider provider : pgFamilyProviders()) {
            TableMigrationData table = table("public", "t_user");
            table.setComment("用户'表");
            ColumnMigrationData id = column("id", "INT8");
            id.setPrimary(true);
            id.setNotNull(true);
            ColumnMigrationData name = column("name", "VARCHAR(100)");
            name.setComment("姓名'注释");
            table.getColumns().add(id);
            table.getColumns().add(name);

            List<String> statements = provider.createTable(table).toSQLList();

            Assert.assertEquals(provider.getClass().getName(), 3, statements.size());
            Assert.assertTrue(statements.get(0).startsWith("CREATE TABLE IF NOT EXISTS \"public\".\"t_user\""));
            Assert.assertEquals("COMMENT ON TABLE \"public\".\"t_user\" IS '用户''表';", statements.get(1));
            Assert.assertEquals("COMMENT ON COLUMN \"public\".\"t_user\".\"name\" IS '姓名''注释';", statements.get(2));
        }
    }

    @Test
    public void pgFamilyRenameAndAddColumnSplitComments() {
        for (AbstractDatabaseMigrationProvider provider : pgFamilyProviders()) {
            TableMigrationData renameTable = table("public", "t_user");
            ColumnMigrationData renamed = column("new_name", "VARCHAR(100)");
            renamed.setOldColumnName("old_name");
            renamed.setComment("姓名'注释");
            renameTable.getColumns().add(renamed);

            List<MigrationCommand> renameCommands = provider.addTableColumn(renameTable);
            Assert.assertEquals(1, renameCommands.size());
            Assert.assertEquals(Arrays.asList(
                    "ALTER TABLE \"public\".\"t_user\" RENAME COLUMN \"old_name\" TO \"new_name\";",
                    "COMMENT ON COLUMN \"public\".\"t_user\".\"new_name\" IS '姓名''注释';"
            ), renameCommands.get(0).toSQLList());

            TableMigrationData addTable = table("public", "t_user");
            ColumnMigrationData added = column("age", "INT");
            added.setComment("年龄");
            addTable.getColumns().add(added);
            List<MigrationCommand> addCommands = provider.addTableColumn(addTable);
            Assert.assertEquals(1, addCommands.size());
            Assert.assertEquals(Arrays.asList(
                    "ALTER TABLE \"public\".\"t_user\" ADD \"age\" INT NULL;",
                    "COMMENT ON COLUMN \"public\".\"t_user\".\"age\" IS '年龄';"
            ), addCommands.get(0).toSQLList());

            TableMigrationData table = table("public", "new_table");
            table.setOldTableName("old_table");
            Assert.assertEquals("ALTER TABLE \"public\".\"old_table\" RENAME TO \"new_table\";",
                    provider.renameTable(table).toSQL());
        }
    }

    @Test
    public void pgFamilyIndexUsesQuotedNameAndSeparateComment() {
        for (AbstractDatabaseMigrationProvider provider : pgCommentProviders()) {
            TableMigrationData table = table("public", "t_user");
            IndexMigrationData index = index("IX_Mixed", "name");
            index.setComment("索引'注释");
            table.setIndexes(Collections.singletonList(index));

            List<MigrationCommand> commands = provider.createTableIndex(table);

            Assert.assertEquals(1, commands.size());
            Assert.assertEquals(Arrays.asList(
                    "CREATE INDEX \"IX_Mixed\" ON \"public\".\"t_user\" (\"name\" ASC);",
                    "COMMENT ON INDEX \"public\".\"IX_Mixed\" IS '索引''注释';"
            ), commands.get(0).toSQLList());
        }

        AbstractDatabaseMigrationProvider duckDB = new DuckDBSQLDatabaseMigrationProvider(
                null, new DuckDBSQLKeyword(), new DuckDBSQLMigrationEntityParser());
        TableMigrationData table = table("main", "t_user");
        table.setIndexes(Collections.singletonList(index("IX_Mixed", "name")));
        Assert.assertEquals("CREATE INDEX \"IX_Mixed\" ON \"main\".\"t_user\" (\"name\" ASC);",
                duckDB.createTableIndex(table).get(0).toSQL());
    }

    @Test
    public void sqliteAndH2RenameUseNativeSyntax() {
        SQLiteDatabaseMigrationProvider sqlite = new SQLiteDatabaseMigrationProvider(
                null, new SQLiteSQLKeyword(), new SQLiteMigrationEntityParser(), null);
        TableMigrationData sqliteTable = table("main", "t_user");
        ColumnMigrationData renamed = column("new_name", "VARCHAR(100)");
        renamed.setOldColumnName("old_name");
        sqliteTable.getColumns().add(renamed);

        Assert.assertEquals("ALTER TABLE \"main\".\"t_user\" RENAME COLUMN \"old_name\" TO \"new_name\";",
                sqlite.addTableColumn(sqliteTable).get(0).toSQL());
        sqliteTable.setOldTableName("old_table");
        Assert.assertEquals("ALTER TABLE \"main\".\"old_table\" RENAME TO \"t_user\";",
                sqlite.renameTable(sqliteTable).toSQL());

        H2DatabaseMigrationProvider h2 = new H2DatabaseMigrationProvider(
                null, new H2SQLKeyword(), new H2MigrationEntityParser());
        TableMigrationData h2Table = table("PUBLIC", "new_table");
        h2Table.setOldTableName("old_table");
        Assert.assertEquals("ALTER TABLE \"PUBLIC\".\"old_table\" RENAME TO \"new_table\";",
                h2.renameTable(h2Table).toSQL());
    }

    @Test
    public void mySQLRenameColumnPreservesDefaultAndGeneratedKey() {
        MySQLDatabaseMigrationProvider provider = new MySQLDatabaseMigrationProvider(
                null, new MySQLSQLKeyword(), new MySQLMigrationEntityParser());
        TableMigrationData generatedTable = table(null, "t_user");
        ColumnMigrationData generated = column("id", "BIGINT");
        generated.setOldColumnName("old_id");
        generated.setNotNull(true);
        generated.setGeneratedKey(true);
        generated.setDefValue("0");
        generatedTable.getColumns().add(generated);

        Assert.assertEquals("ALTER TABLE `t_user` CHANGE `old_id` `id` BIGINT NOT NULL AUTO_INCREMENT;",
                provider.addTableColumn(generatedTable).get(0).toSQL());

        TableMigrationData defaultTable = table(null, "t_user");
        ColumnMigrationData amount = column("amount", "DECIMAL(10,2)");
        amount.setOldColumnName("old_amount");
        amount.setDefValue("0");
        amount.setComment("分数'说明");
        defaultTable.getColumns().add(amount);
        Assert.assertEquals("ALTER TABLE `t_user` CHANGE `old_amount` `amount` DECIMAL(10,2) NULL DEFAULT 0 COMMENT '分数''说明';",
                provider.addTableColumn(defaultTable).get(0).toSQL());
    }

    @Test
    public void foreignKeyActionSurvivesEntityParsing() {
        MigrationEntityParser parser = new MigrationEntityParser() {
            @Override
            public ColumnDbTypeResult getColumnDbType(EntityMigrationMetadata entityMigrationMetadata, ColumnMetadata columnMetadata) {
                return new ColumnDbTypeResult("VARCHAR(255)", null);
            }

            @Override
            public String getColumnComment(EntityMigrationMetadata entityMigrationMetadata, ColumnMetadata columnMetadata) {
                return null;
            }

            @Override
            public boolean isNullable(EntityMigrationMetadata entityMigrationMetadata, ColumnMetadata columnMetadata) {
                return true;
            }

            @Override
            public boolean columnExistInDb(EntityMigrationMetadata entityMigrationMetadata, ColumnMetadata columnMetadata) {
                return false;
            }

            @Override
            public String getTableComment(EntityMigrationMetadata entityMigrationMetadata) {
                return null;
            }

            @Override
            public String getColumnOldName(EntityMigrationMetadata entityMigrationMetadata, ColumnMetadata columnMetadata) {
                return null;
            }

            @Override
            public List<TableIndexResult> getTableIndexes(EntityMigrationMetadata entityMigrationMetadata) {
                return Collections.emptyList();
            }

            @Override
            public List<TableForeignKeyResult> getTableForeignKeys(EntityMigrationMetadata entityMigrationMetadata, com.easy.query.core.context.QueryRuntimeContext runtimeContext) {
                return Collections.singletonList(new TableForeignKeyResult(
                        "fk_user", "ON DELETE CASCADE", "t_detail", "t_user",
                        new String[]{"user_id"}, new String[]{"id"}));
            }
        };
        EntityMetadata entityMetadata = new EntityMetadata(Object.class);
        EntityMetadataManager metadataManager = (EntityMetadataManager) Proxy.newProxyInstance(
                getClass().getClassLoader(), new Class<?>[]{EntityMetadataManager.class},
                (proxy, method, args) -> "getEntityMetadata".equals(method.getName()) ? entityMetadata : null);
        ServiceProvider serviceProvider = (ServiceProvider) Proxy.newProxyInstance(
                getClass().getClassLoader(), new Class<?>[]{ServiceProvider.class},
                (proxy, method, args) -> null);
        DatabaseMigrationProvider provider = new DefaultDatabaseMigrationProvider(
                null, DefaultSQLKeyword.DEFAULT_SQL_KEYWORD, parser);
        DefaultMigrationsSQLGenerator generator = new DefaultMigrationsSQLGenerator(
                metadataManager, provider, serviceProvider);

        TableMigrationData table = generator.parseEntity(Object.class);

        Assert.assertEquals(1, table.getForeignKeys().size());
        Assert.assertEquals("ON DELETE CASCADE", table.getForeignKeys().get(0).getAction());
    }

    @Test
    public void metadataFailuresAreNotConvertedToEmptyCollections() {
        SQLException failure = new SQLException("permission denied");
        DataSource dataSource = (DataSource) Proxy.newProxyInstance(
                getClass().getClassLoader(), new Class<?>[]{DataSource.class},
                (proxy, method, args) -> {
                    if ("getConnection".equals(method.getName())) {
                        throw failure;
                    }
                    throw new UnsupportedOperationException(method.getName());
                });
        List<MetadataCall> calls = Arrays.asList(
                ds -> EasyDatabaseUtil.getColumns(ds, "select * from t_user where 1=2"),
                ds -> EasyDatabaseUtil.getTableIndexes(ds, "t_user"),
                ds -> EasyDatabaseUtil.getTableForeignKeys(ds, "t_user"));

        for (MetadataCall call : calls) {
            try {
                call.execute(dataSource);
                Assert.fail("Expected metadata failure");
            } catch (EasyQuerySQLCommandException ex) {
                Assert.assertSame(failure, ex.getCause());
            }
        }
    }

    private static List<AbstractDatabaseMigrationProvider> pgFamilyProviders() {
        return Arrays.asList(
                new PgSQLDatabaseMigrationProvider(null, new PgSQLSQLKeyword(), new PgSQLMigrationEntityParser()),
                new GaussDBDatabaseMigrationProvider(null, new GaussDBSQLKeyword(), new GaussDBMigrationEntityParser()),
                new KingbaseESDatabaseMigrationProvider(null, new KingbaseESSQLKeyword(), new KingbaseESMigrationEntityParser()),
                new DuckDBSQLDatabaseMigrationProvider(null, new DuckDBSQLKeyword(), new DuckDBSQLMigrationEntityParser()));
    }

    private static List<AbstractDatabaseMigrationProvider> pgCommentProviders() {
        return Arrays.asList(
                new PgSQLDatabaseMigrationProvider(null, new PgSQLSQLKeyword(), new PgSQLMigrationEntityParser()),
                new GaussDBDatabaseMigrationProvider(null, new GaussDBSQLKeyword(), new GaussDBMigrationEntityParser()),
                new KingbaseESDatabaseMigrationProvider(null, new KingbaseESSQLKeyword(), new KingbaseESMigrationEntityParser()));
    }

    private static TableMigrationData table(String schema, String tableName) {
        TableMigrationData table = new TableMigrationData();
        table.setSchema(schema);
        table.setTableName(tableName);
        table.setColumns(new ArrayList<>());
        return table;
    }

    private static ColumnMigrationData column(String name, String dbType) {
        ColumnMigrationData column = new ColumnMigrationData();
        column.setName(name);
        column.setDbType(dbType);
        return column;
    }

    private static IndexMigrationData index(String name, String columnName) {
        IndexMigrationData.EntityField field = new IndexMigrationData.EntityField();
        field.setColumnName(columnName);
        field.setAsc(true);
        IndexMigrationData index = new IndexMigrationData();
        index.setIndexName(name);
        index.setFields(Collections.singletonList(field));
        return index;
    }

    private interface MetadataCall {
        void execute(DataSource dataSource);
    }
}
