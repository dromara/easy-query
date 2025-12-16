package com.easy.query.test;

import com.easy.query.api.proxy.base.MapProxy;
import com.easy.query.api.proxy.client.DefaultEasyEntityQuery;
import com.easy.query.api.proxy.enums.MapKeyModeEnum;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.api.database.CodeFirstCommand;
import com.easy.query.core.basic.api.database.DatabaseCodeFirst;
import com.easy.query.core.bootstrapper.EasyQueryBootstrapper;
import com.easy.query.core.migration.MigrationCommand;
import com.easy.query.core.migration.MigrationContext;
import com.easy.query.core.migration.MigrationsSQLGenerator;
import com.easy.query.core.migration.data.TableMigrationData;
import com.easy.query.mysql.config.MySQLDatabaseConfiguration;
import com.easy.query.test.common.MD5Util;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.MyMigrationBlog;
import com.easy.query.test.entity.MyMigrationBlog0;
import com.easy.query.test.entity.NewTopic;
import com.easy.query.test.entity.NewTopic3;
import com.easy.query.test.entity.Topic;
import com.zaxxer.hikari.HikariDataSource;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * create time 2025/1/14 14:23
 * 文件说明
 *
 * @author xuejiaming
 */
public class MigrationTest extends BaseTest {

    @Test
    public void createTableSQLTest1() {
        MigrationsSQLGenerator migrationsSQLGenerator = easyEntityQuery.getRuntimeContext().getMigrationsSQLGenerator();
        List<TableMigrationData> tableMigrationDataList = Arrays.asList(Topic.class, BlogEntity.class, MyMigrationBlog.class).stream()
                .map(o -> migrationsSQLGenerator.parseEntity(o)).collect(Collectors.toList());
        MigrationContext migrationContext = new MigrationContext(tableMigrationDataList);
        List<MigrationCommand> migrationCommands = migrationsSQLGenerator.generateMigrationSQL(migrationContext);
        Assert.assertEquals(1, migrationCommands.size());
        for (MigrationCommand migrationCommand : migrationCommands) {
            System.out.println(migrationCommand.toSQL());
        }
        MigrationCommand migrationCommand = migrationCommands.get(0);
        Assert.assertEquals("CREATE TABLE IF NOT EXISTS `my_migration_blog` ( \n" +
                "`id` VARCHAR(255) NOT NULL ,\n" +
                "`create_time` DATETIME(3) NULL  COMMENT '创建时间;创建时间',\n" +
                "`update_time` DATETIME(3) NULL  COMMENT '修改时间;修改时间',\n" +
                "`create_by` VARCHAR(255) NULL  COMMENT '创建人;创建人',\n" +
                "`update_by` VARCHAR(255) NULL  COMMENT '修改人;修改人',\n" +
                "`deleted` TINYINT(1) NULL  COMMENT '是否删除;是否删除',\n" +
                "`title` VARCHAR(255) NULL  COMMENT '标题',\n" +
                "`content` VARCHAR(255) NULL  COMMENT '内容',\n" +
                "`url` VARCHAR(255) NULL  COMMENT '博客链接',\n" +
                "`star` INT(11) NULL  COMMENT '点赞数',\n" +
                "`publish_time` DATETIME(3) NULL  COMMENT '发布时间',\n" +
                "`score` DECIMAL(16,2) NULL  COMMENT '评分',\n" +
                "`status` INT(11) NULL  COMMENT '状态',\n" +
                "`order` DECIMAL(16,2) NULL  COMMENT '排序',\n" +
                "`is_top` TINYINT(1) NULL  COMMENT '是否置顶',\n" +
                "`top` TINYINT(1) NULL  COMMENT '是否置顶', \n" +
                " PRIMARY KEY (`id`)\n" +
                ") Engine=InnoDB;", migrationCommand.toSQL());
    }

    @Test
    public void createTableSQLTest2() {
        MigrationsSQLGenerator migrationsSQLGenerator = easyEntityQuery.getRuntimeContext().getMigrationsSQLGenerator();
        List<TableMigrationData> tableMigrationDataList = Arrays.asList(NewTopic.class).stream()
                .map(o -> migrationsSQLGenerator.parseEntity(o)).collect(Collectors.toList());
        MigrationContext migrationContext = new MigrationContext(tableMigrationDataList);
        List<MigrationCommand> migrationCommands = migrationsSQLGenerator.generateMigrationSQL(migrationContext);
        for (MigrationCommand migrationCommand : migrationCommands) {
            System.out.println(migrationCommand.toSQL());
        }
        Assert.assertEquals(2, migrationCommands.size());
        MigrationCommand migrationCommand0 = migrationCommands.get(0);
        Assert.assertEquals("ALTER TABLE `t_topic` RENAME TO `t_topic2`;", migrationCommand0.toSQL());
        MigrationCommand migrationCommand1 = migrationCommands.get(1);
        Assert.assertEquals("ALTER TABLE `t_topic2` ADD `abc` VARCHAR(255) NULL;", migrationCommand1.toSQL());
    }

    @Test
    public void createTableSQLTest3() {
        MigrationsSQLGenerator migrationsSQLGenerator = easyEntityQuery.getRuntimeContext().getMigrationsSQLGenerator();
        List<TableMigrationData> tableMigrationDataList = Arrays.asList(NewTopic3.class).stream()
                .map(o -> migrationsSQLGenerator.parseEntity(o)).collect(Collectors.toList());
        MigrationContext migrationContext = new MigrationContext(tableMigrationDataList);
        List<MigrationCommand> migrationCommands = migrationsSQLGenerator.generateMigrationSQL(migrationContext);
        for (MigrationCommand migrationCommand : migrationCommands) {
            System.out.println(migrationCommand.toSQL());
        }
        Assert.assertEquals(1, migrationCommands.size());
        MigrationCommand migrationCommand0 = migrationCommands.get(0);
        Assert.assertEquals("ALTER TABLE `t_topic` CHANGE `title` `abc` varchar(200) NULL;", migrationCommand0.toSQL());
    }

    @Test
    public void createTableSQLTest4() {

        DatabaseCodeFirst databaseCodeFirst = easyEntityQuery.getDatabaseCodeFirst();
        if (databaseCodeFirst.tableExists(MyMigrationBlog0.class)) {
            System.out.println("存在表:MyMigrationBlog0");
            boolean any = easyEntityQuery.queryable(MyMigrationBlog0.class).any();
            CodeFirstCommand codeFirstCommand = databaseCodeFirst.dropTableCommand(Arrays.asList(MyMigrationBlog0.class));
            codeFirstCommand.executeWithTransaction(arg -> {
                System.out.println("执行删除");
                System.out.println(arg.getSQL());
            });
        } else {
            System.out.println("不存在表:MyMigrationBlog0");
        }

        CodeFirstCommand codeFirstCommand = databaseCodeFirst.syncTableCommand(Arrays.asList(MyMigrationBlog0.class));
        codeFirstCommand.executeWithTransaction(arg -> {
            System.out.println(arg.getSQL());
            String md5 = MD5Util.getMD5Hash(arg.getSQL());
            System.out.println("sql-hash:" + md5);
            Assert.assertEquals("2a927e4ae0dbf7d6f6d687a50888df93", md5);
            arg.commit();
        });

        boolean any = easyEntityQuery.queryable(MyMigrationBlog0.class).any();
        Assert.assertFalse(any);
        CodeFirstCommand codeFirstCommand1 = databaseCodeFirst.dropTableCommand(Arrays.asList(MyMigrationBlog0.class));
        codeFirstCommand1.executeWithEnvTransaction(arg -> {
            System.out.println("执行删除");
            System.out.println(arg.getSQL());
        });
        Exception ex = null;
        try {
            easyEntityQuery.queryable(MyMigrationBlog0.class).any();
        } catch (Exception e) {
            ex = e;
            System.out.println(ex);
        }
        Assert.assertNotNull(ex);
    }



    @SneakyThrows
    @Test
    public void test3(){

//        List<Map<String, Object>> list = easyEntityQuery.queryable(Topic.class)
//                .leftJoin(BlogEntity.class, (t_topic, t_blog) -> t_blog.id().eq(t_topic.id()))
//                .select((t_topic, t_blog) -> new MapProxy()
//                        .selectAll(t_topic, MapKeyModeEnum.FIELD_NAME)
//                        .put("a1", t_blog.star())
//                ).toList();
//        Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true&rewriteBatchedStatements=true", "root", "root");
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/eq_db3?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true&rewriteBatchedStatements=true");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setMaximumPoolSize(1);


        EasyQueryClient client = EasyQueryBootstrapper.defaultBuilderConfiguration()
                .setDefaultDataSource(dataSource)
                .optionConfigure(op -> {
                    //进行一系列可以选择的配置
                    //op.setPrintSql(true);
                })
                .useDatabaseConfigure(new MySQLDatabaseConfiguration())
                .build();
        DefaultEasyEntityQuery entityQuery = new DefaultEasyEntityQuery(client);

        DatabaseCodeFirst databaseCodeFirst = entityQuery.getDatabaseCodeFirst();
        databaseCodeFirst.createDatabaseIfNotExists();
        //自动同步数据库表
        CodeFirstCommand codeFirstCommand = databaseCodeFirst.syncTableCommand(Arrays.asList(Topic.class, BlogEntity.class));
        //执行命令
        codeFirstCommand.executeWithTransaction(arg->{
            System.out.println(arg.getSQL());
            arg.commit();
        });
    }

}
