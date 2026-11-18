package com.easy.query.test.sqlite;

import com.easy.query.core.basic.api.database.CodeFirstCommand;
import com.easy.query.core.basic.api.database.DatabaseCodeFirst;
import com.easy.query.core.migration.data.ColumnMigrationData;
import com.easy.query.core.migration.data.TableMigrationData;
import com.easy.query.core.util.EasyDatabaseUtil;
import com.easy.query.test.cache.JsonUtil;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.MultiPrimaryEntity;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.TopicAuto;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * create time 2025/3/25 09:21
 * 文件说明
 *
 * @author xuejiaming
 */


public class SQLiteTest extends SQLiteBaseTest{
    @Test
    public void testQuery(){
        List<BlogEntity> list = entityQuery.queryable(BlogEntity.class).toList();
        System.out.println(list.size());
        for (BlogEntity blogEntity : list) {
            String s = JsonUtil.object2JsonStr(blogEntity);
            System.out.println(s);
        }
    }
    @Test
    public void testQuery1(){
        {

            List<BlogEntity> list = entityQuery.queryable(BlogEntity.class)
                    .where(t_blog -> {
                        t_blog.createTime().rangeClosed(LocalDateTime.of(2020,2,1,1,0,0),LocalDateTime.of(2021,3,1,1,0));
                    }).toList();
            System.out.println(list.size());
            for (BlogEntity blogEntity : list) {
                String s = JsonUtil.object2JsonStr(blogEntity);
                System.out.println(s);
            }
        }
    }

    @Test
    public  void testDDL(){
        DatabaseCodeFirst databaseCodeFirst = entityQuery.getDatabaseCodeFirst();
        CodeFirstCommand codeFirstCommand = databaseCodeFirst.dropTableIfExistsCommand(Arrays.asList(Topic.class));
        codeFirstCommand.forEach(s->s.commit());
        CodeFirstCommand codeFirstCommand1 = databaseCodeFirst.syncTableCommand(Arrays.asList(Topic.class));
        codeFirstCommand1.forEach(s->{
            Assert.assertEquals("\n" +
                    "CREATE TABLE IF NOT EXISTS \"t_topic\" ( \n" +
                    "\"Id\" VARCHAR(255) NOT NULL ,\n" +
                    "\"Stars\" integer NULL ,\n" +
                    "\"Title\" VARCHAR(255) NULL ,\n" +
                    "\"CreateTime\" TEXT NULL , \n" +
                    " PRIMARY KEY (\"Id\")\n" +
                    ");",s.getSQL());
            s.commit();
        });
    }
    @Test
    public  void testDDL2(){
        DatabaseCodeFirst databaseCodeFirst = entityQuery.getDatabaseCodeFirst();
        CodeFirstCommand codeFirstCommand = databaseCodeFirst.dropTableIfExistsCommand(Arrays.asList(MultiPrimaryEntity.class));
        codeFirstCommand.forEach(s->s.commit());
        CodeFirstCommand codeFirstCommand1 = databaseCodeFirst.syncTableCommand(Arrays.asList(MultiPrimaryEntity.class));
        codeFirstCommand1.forEach(s->{
            Assert.assertEquals("\n" +
                    "CREATE TABLE IF NOT EXISTS \"t_multi_primary\" ( \n" +
                    "\"Id1\" VARCHAR(255) NOT NULL ,\n" +
                    "\"Id2\" VARCHAR(255) NOT NULL , \n" +
                    " PRIMARY KEY (\"Id1\", \"Id2\")\n" +
                    ");",s.getSQL());
            s.commit();
        });
    }

    @Test
    public void testAutoIncrementDDL() {
        DatabaseCodeFirst databaseCodeFirst = entityQuery.getDatabaseCodeFirst();
        databaseCodeFirst.dropTableIfExistsCommand(Arrays.asList(TopicAuto.class)).forEach(s -> s.commit());
        databaseCodeFirst.syncTableCommand(Arrays.asList(TopicAuto.class)).forEach(s -> {
            Assert.assertTrue(s.getSQL().contains("\"Id\" INTEGER PRIMARY KEY AUTOINCREMENT"));
            Assert.assertFalse(s.getSQL().contains("PRIMARY KEY (\"Id\")"));
            s.commit();
        });

        TopicAuto topicAuto = new TopicAuto();
        topicAuto.setStars(1);
        topicAuto.setTitle("sqlite-auto");
        topicAuto.setCreateTime(LocalDateTime.now());
        long rows = entityQuery.insertable(topicAuto).executeRows();
        Assert.assertEquals(1, rows);
        TopicAuto inserted = entityQuery.queryable(TopicAuto.class)
                .where(t -> t.title().eq("sqlite-auto"))
                .firstNotNull();
        Assert.assertNotNull(inserted.getId());
        Assert.assertTrue(inserted.getId() > 0);
    }

    @Test
    public void testRenameTableAndColumnDDL() {
        DatabaseCodeFirst databaseCodeFirst = entityQuery.getDatabaseCodeFirst();
        TableMigrationData oldTable = migrationTable("T_SQLITE_CODE_FIRST_OLD", null);
        TableMigrationData newTable = migrationTable("T_SQLITE_CODE_FIRST_NEW", "T_SQLITE_CODE_FIRST_OLD");
        databaseCodeFirst.dropTableIfExistsCommandByMigrationData(Arrays.asList(oldTable, newTable))
                .forEach(s -> s.commit());
        databaseCodeFirst.syncTableCommandByMigrationData(Arrays.asList(oldTable))
                .forEach(s -> s.commit());

        databaseCodeFirst.syncTableCommandByMigrationData(Arrays.asList(newTable)).forEach(s -> {
            Assert.assertTrue(s.getSQL().contains("ALTER TABLE \"T_SQLITE_CODE_FIRST_OLD\" RENAME TO \"T_SQLITE_CODE_FIRST_NEW\";"));
            s.commit();
        });
        Assert.assertTrue(databaseCodeFirst.tableExistsByMigrationData(newTable));

        TableMigrationData renameColumnTable = migrationTable("T_SQLITE_CODE_FIRST_NEW", null);
        ColumnMigrationData renamedColumn = renameColumnTable.getColumns().get(1);
        renamedColumn.setName("TITLE");
        renamedColumn.setOldColumnName("NAME");
        databaseCodeFirst.syncTableCommandByMigrationData(Arrays.asList(renameColumnTable)).forEach(s -> {
            Assert.assertTrue(s.getSQL().contains("ALTER TABLE \"T_SQLITE_CODE_FIRST_NEW\" RENAME COLUMN \"NAME\" TO \"TITLE\";"));
            s.commit();
        });

        java.util.Set<String> columns = EasyDatabaseUtil.getColumns(dataSource,
                "SELECT * FROM \"T_SQLITE_CODE_FIRST_NEW\" WHERE 1=2");
        Assert.assertTrue(columns.contains("TITLE"));
        Assert.assertFalse(columns.contains("NAME"));

        int[] syncCommands = {0};
        databaseCodeFirst.syncTableCommandByMigrationData(Arrays.asList(renameColumnTable)).forEach(s -> {
            syncCommands[0]++;
            s.commit();
        });
        Assert.assertEquals(0, syncCommands[0]);
        databaseCodeFirst.dropTableIfExistsCommandByMigrationData(Arrays.asList(renameColumnTable))
                .forEach(s -> s.commit());
    }

    private TableMigrationData migrationTable(String tableName, String oldTableName) {
        TableMigrationData table = new TableMigrationData();
        table.setTableName(tableName);
        table.setOldTableName(oldTableName);
        ColumnMigrationData id = new ColumnMigrationData();
        id.setName("ID");
        id.setDbType("INTEGER");
        id.setPrimary(true);
        id.setNotNull(true);
        ColumnMigrationData name = new ColumnMigrationData();
        name.setName("NAME");
        name.setDbType("VARCHAR(255)");
        table.setColumns(new java.util.ArrayList<>(Arrays.asList(id, name)));
        return table;
    }
}
