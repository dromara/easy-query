package com.easy.query.test;

import com.easy.query.core.migration.MigrationCommand;
import com.easy.query.core.migration.MigrationContext;
import com.easy.query.core.migration.MigrationsSQLGenerator;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.MyMigrationBlog;
import com.easy.query.test.entity.NewTopic;
import com.easy.query.test.entity.NewTopic3;
import com.easy.query.test.entity.Topic;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * create time 2025/1/14 14:23
 * 文件说明
 *
 * @author xuejiaming
 */
public class MigrationTest extends BaseTest{

    @Test
    public void createTableSQLTest1(){
        MigrationsSQLGenerator migrationsSQLGenerator = easyEntityQuery.getRuntimeContext().getMigrationsSQLGenerator();
        MigrationContext migrationContext = new MigrationContext(Arrays.asList(Topic.class, BlogEntity.class, MyMigrationBlog.class));
        List<MigrationCommand> migrationCommands = migrationsSQLGenerator.generateMigrationSQL(migrationContext);
        Assert.assertEquals(1,migrationCommands.size());
        for (MigrationCommand migrationCommand : migrationCommands) {
            System.out.println(migrationCommand.toSQL());
        }
        MigrationCommand migrationCommand = migrationCommands.get(0);
        Assert.assertEquals("CREATE TABLE IF NOT EXISTS `my_migration_blog` ( \n" +
                "`id` VARCHAR(255) NOT NULL ,\n" +
                "`create_time` DATETIME(3) NULL ,\n" +
                "`update_time` DATETIME(3) NULL ,\n" +
                "`create_by` VARCHAR(255) NULL ,\n" +
                "`update_by` VARCHAR(255) NULL ,\n" +
                "`deleted` TINYINT(1) NULL ,\n" +
                "`title` VARCHAR(255) NULL  COMMENT 标题,\n" +
                "`content` VARCHAR(255) NULL  COMMENT 内容,\n" +
                "`url` VARCHAR(255) NULL  COMMENT 博客链接,\n" +
                "`star` INT(11) NULL ,\n" +
                "`publish_time` DATETIME(3) NULL ,\n" +
                "`score` DECIMAL(16,2) NULL ,\n" +
                "`status` INT(11) NULL ,\n" +
                "`order` DECIMAL(16,2) NULL ,\n" +
                "`is_top` TINYINT(1) NULL ,\n" +
                "`top` TINYINT(1) NULL , \n" +
                " PRIMARY KEY (`id`),\n" +
                ") Engine=InnoDB;",migrationCommand.toSQL());
    }
    @Test
    public void createTableSQLTest2(){
        MigrationsSQLGenerator migrationsSQLGenerator = easyEntityQuery.getRuntimeContext().getMigrationsSQLGenerator();
        MigrationContext migrationContext = new MigrationContext(Arrays.asList(NewTopic.class));
        List<MigrationCommand> migrationCommands = migrationsSQLGenerator.generateMigrationSQL(migrationContext);
        for (MigrationCommand migrationCommand : migrationCommands) {
            System.out.println(migrationCommand.toSQL());
        }
        Assert.assertEquals(2,migrationCommands.size());
        MigrationCommand migrationCommand0 = migrationCommands.get(0);
        Assert.assertEquals("ALTER TABLE `t_topic` RENAME TO `t_topic2`;",migrationCommand0.toSQL());
        MigrationCommand migrationCommand1 = migrationCommands.get(1);
        Assert.assertEquals("ALTER TABLE `t_topic2` ADD `abc` VARCHAR(255) NULL;",migrationCommand1.toSQL());
    }
    @Test
    public void createTableSQLTest3(){
        MigrationsSQLGenerator migrationsSQLGenerator = easyEntityQuery.getRuntimeContext().getMigrationsSQLGenerator();
        MigrationContext migrationContext = new MigrationContext(Arrays.asList(NewTopic3.class));
        List<MigrationCommand> migrationCommands = migrationsSQLGenerator.generateMigrationSQL(migrationContext);
        for (MigrationCommand migrationCommand : migrationCommands) {
            System.out.println(migrationCommand.toSQL());
        }
        Assert.assertEquals(1,migrationCommands.size());
        MigrationCommand migrationCommand0 = migrationCommands.get(0);
        Assert.assertEquals("ALTER TABLE `t_topic` CHANGE `title` `abc` varchar(200) NULL;",migrationCommand0.toSQL());
    }
}
