package com.easy.query.test.migration;

import com.easy.query.core.migration.MigrationCommand;
import com.easy.query.core.migration.commands.MultiMigrationCommand;
import com.easy.query.core.migration.data.ColumnMigrationData;
import com.easy.query.core.migration.data.IndexMigrationData;
import com.easy.query.core.migration.data.TableMigrationData;
import com.easy.query.dameng.config.DamengSQLKeyword;
import com.easy.query.dameng.migration.DamengDatabaseMigrationProvider;
import com.easy.query.dameng.migration.DamengMigrationEntityParser;
import com.easy.query.h2.config.H2SQLKeyword;
import com.easy.query.h2.migration.H2DatabaseMigrationProvider;
import com.easy.query.h2.migration.H2MigrationEntityParser;
import com.easy.query.mssql.config.MsSQLSQLKeyword;
import com.easy.query.mssql.migration.MsSQLDatabaseMigrationProvider;
import com.easy.query.mssql.migration.MsSQLMigrationEntityParser;
import com.easy.query.oracle.config.OracleSQLKeyword;
import com.easy.query.oracle.migration.OracleDatabaseMigrationProvider;
import com.easy.query.oracle.migration.OracleMigrationEntityParser;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MigrationProviderTest {

    @Test
    public void multiCommandKeepsStatementsExecutable() {
        MultiMigrationCommand command = new MultiMigrationCommand(Arrays.asList(
                "CREATE TABLE t(id INT);",
                "COMMENT ON TABLE t IS 'comment'"));

        Assert.assertEquals(Arrays.asList(
                "CREATE TABLE t(id INT);",
                "COMMENT ON TABLE t IS 'comment'"), command.toSQLList());
    }

    @Test
    public void oracleCreateTableUsesSequenceAndTrigger() {
        TableMigrationData table = table("t_user");
        table.setComment("用户'表");
        ColumnMigrationData id = column("id", "NUMBER(19)", true, true);
        id.setComment("主键'值");
        table.getColumns().add(id);
        table.getColumns().add(column("score", "double", false, false));
        OracleDatabaseMigrationProvider provider = new OracleDatabaseMigrationProvider(
                null, new OracleSQLKeyword(), new OracleMigrationEntityParser());

        MigrationCommand command = provider.createTable(table);
        List<String> statements = command.toSQLList();

        Assert.assertEquals(5, statements.size());
        Assert.assertTrue(statements.get(0).contains("\"score\" binary_double NULL"));
        Assert.assertTrue(statements.get(0).contains("CONSTRAINT \"t_user_PK\" PRIMARY KEY (\"id\")"));
        Assert.assertEquals("COMMENT ON TABLE \"t_user\" IS '用户''表'", statements.get(1));
        Assert.assertEquals("COMMENT ON COLUMN \"t_user\".\"id\" IS '主键''值'", statements.get(2));
        Assert.assertEquals("CREATE SEQUENCE \"t_user_SEQ\" INCREMENT BY 1 START WITH 1 NOCACHE", statements.get(3));
        Assert.assertTrue(statements.get(4).startsWith("CREATE OR REPLACE TRIGGER \"t_user_TRG\""));
        Assert.assertTrue(statements.get(4).endsWith("END;"));
        Assert.assertTrue(command.toSQLList().stream().noneMatch(sql -> sql.contains("execute immediate")));
    }

    @Test
    public void damengCreateTableUsesIdentityAndSeparateComments() {
        TableMigrationData table = table("t_user");
        table.setComment("用户表");
        table.getColumns().add(column("id", "NUMBER(19)", true, true));
        table.getColumns().add(column("name", "VARCHAR(255)", false, false));
        DamengDatabaseMigrationProvider provider = new DamengDatabaseMigrationProvider(
                null, new DamengSQLKeyword(), new DamengMigrationEntityParser());

        MigrationCommand command = provider.createTable(table);

        Assert.assertEquals(2, command.toSQLList().size());
        Assert.assertTrue(command.toSQLList().get(0).contains("\"id\" BIGINT IDENTITY(1,1) NOT NULL"));
        Assert.assertTrue(command.toSQLList().get(0).contains("CONSTRAINT \"t_user_PK\" PRIMARY KEY (\"id\")"));
        Assert.assertEquals("COMMENT ON TABLE \"t_user\" IS '用户表'", command.toSQLList().get(1));
        Assert.assertTrue(command.toSQLList().stream().noneMatch(sql -> sql.contains("execute immediate")));
    }

    @Test
    public void msSQLCreateTableSupportsCompositePrimaryKey() {
        TableMigrationData table = table("t_multi");
        table.setComment("multi'table");
        table.getColumns().add(column("id1", "BIGINT", true, false));
        table.getColumns().add(column("id2", "BIGINT", true, false));
        MsSQLDatabaseMigrationProvider provider = new MsSQLDatabaseMigrationProvider(
                null, new MsSQLSQLKeyword(), new MsSQLMigrationEntityParser());

        MigrationCommand command = provider.createTable(table);

        Assert.assertEquals(2, command.toSQLList().size());
        Assert.assertTrue(command.toSQLList().get(0).startsWith("CREATE TABLE [dbo].[t_multi]"));
        Assert.assertTrue(command.toSQLList().get(0).contains("CONSTRAINT [t_multi_PK] PRIMARY KEY ([id1],[id2])"));
        Assert.assertTrue(command.toSQLList().stream().noneMatch(sql -> sql.contains("USE ")));
        Assert.assertTrue(command.toSQLList().get(1).contains("N'multi''table'"));
    }

    @Test
    public void msSQLAddColumnAndIndexSplitComments() {
        ExposedMsSQLDatabaseMigrationProvider provider = new ExposedMsSQLDatabaseMigrationProvider();
        TableMigrationData table = table("t_multi");
        ColumnMigrationData column = column("age", "INT", false, false);
        column.setNotNull(true);
        column.setDefValue("0");
        column.setComment("年龄");

        MigrationCommand addColumn = provider.add(table, column);
        Assert.assertEquals(2, addColumn.toSQLList().size());
        Assert.assertTrue(addColumn.toSQLList().get(0).endsWith("DEFAULT 0 NOT NULL"));

        IndexMigrationData index = index("ix_multi_age", "age");
        index.setComment("年龄索引");
        MigrationCommand createIndex = provider.index(table, index);
        Assert.assertEquals(2, createIndex.toSQLList().size());
        Assert.assertEquals("CREATE INDEX ix_multi_age ON [dbo].[t_multi] ([age] ASC)", createIndex.toSQLList().get(0));
    }

    @Test
    public void h2RenameColumnAndQuoteIndex() {
        ExposedH2DatabaseMigrationProvider provider = new ExposedH2DatabaseMigrationProvider();
        TableMigrationData table = table("t");

        MigrationCommand rename = provider.rename(table, "old_id", column("new_id", "INT", false, false));
        Assert.assertEquals(Collections.singletonList("ALTER TABLE \"t\" ALTER COLUMN \"old_id\" RENAME TO \"new_id\";"), rename.toSQLList());

        MigrationCommand index = provider.index(table, index("ix_Mixed", "new_id"));
        Assert.assertEquals(Collections.singletonList("CREATE INDEX \"ix_Mixed\" ON \"t\" (\"new_id\" ASC);"), index.toSQLList());
    }

    private static TableMigrationData table(String tableName) {
        TableMigrationData table = new TableMigrationData();
        table.setTableName(tableName);
        table.setColumns(new ArrayList<>());
        return table;
    }

    private static ColumnMigrationData column(String name, String dbType, boolean primary, boolean generatedKey) {
        ColumnMigrationData column = new ColumnMigrationData();
        column.setName(name);
        column.setDbType(dbType);
        column.setPrimary(primary);
        column.setGeneratedKey(generatedKey);
        column.setNotNull(primary);
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

    private static class ExposedMsSQLDatabaseMigrationProvider extends MsSQLDatabaseMigrationProvider {
        private ExposedMsSQLDatabaseMigrationProvider() {
            super(null, new MsSQLSQLKeyword(), new MsSQLMigrationEntityParser());
        }

        private MigrationCommand add(TableMigrationData table, ColumnMigrationData column) {
            return addColumn(table, column);
        }

        private MigrationCommand index(TableMigrationData table, IndexMigrationData index) {
            return createIndex(table, index);
        }
    }

    private static class ExposedH2DatabaseMigrationProvider extends H2DatabaseMigrationProvider {
        private ExposedH2DatabaseMigrationProvider() {
            super(null, new H2SQLKeyword(), new H2MigrationEntityParser());
        }

        private MigrationCommand rename(TableMigrationData table, String oldName, ColumnMigrationData column) {
            return renameColumn(table, oldName, column);
        }

        private MigrationCommand index(TableMigrationData table, IndexMigrationData index) {
            return createIndex(table, index);
        }
    }
}
