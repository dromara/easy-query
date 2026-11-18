package com.easy.query.test.migration;

import com.easy.query.api.proxy.client.DefaultEasyEntityQuery;
import com.easy.query.api.proxy.client.EasyEntityQuery;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.api.database.CodeFirstCommand;
import com.easy.query.core.basic.api.database.DatabaseCodeFirst;
import com.easy.query.core.bootstrapper.EasyQueryBootstrapper;
import com.easy.query.core.configuration.QueryConfiguration;
import com.easy.query.dameng.config.DamengDatabaseConfiguration;
import com.easy.query.test.common.MyQueryConfiguration;
import com.easy.query.test.entity.MyMigrationBlog0;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * create time 2025/1/26 21:42
 * 文件说明
 *
 * @author xuejiaming
 */
public class MigrationDamengTest {
    @Test
    public void test() {

        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:dm://localhost:5236");
        dataSource.setUsername("SYSDBA");
        dataSource.setPassword("SYSDBA");
        dataSource.setDriverClassName("dm.jdbc.driver.DmDriver");
        dataSource.setMaximumPoolSize(20);
        EasyQueryClient easyQueryClient = EasyQueryBootstrapper.defaultBuilderConfiguration()
                .setDefaultDataSource(dataSource)
                .optionConfigure(op -> {

                })
//                .replaceService(Column2MapKeyConversion.class, UpperColumn2MapKeyConversion.class)
//                .useDatabaseConfigure(new MySQLDatabaseConfiguration())
//                .replaceService(Dialect.class, DefaultDialect.class)
                .replaceService(QueryConfiguration.class, MyQueryConfiguration.class)
//                .replaceService(EntityMappingRule.class, PropertyEntityMappingRule.class)
//                .replaceService(EntityMappingRule.class, PropertyFirstEntityMappingRule.class)
//                .replaceService(SQLKeyword.class, DefaultSQLKeyword.class)
//                .replaceService(BeanValueCaller.class, ReflectBeanValueCaller.class)
                .useDatabaseConfigure(new DamengDatabaseConfiguration())
                .build();
        EasyEntityQuery easyEntityQuery = new DefaultEasyEntityQuery(easyQueryClient);


        DatabaseCodeFirst databaseCodeFirst = easyEntityQuery.getDatabaseCodeFirst();

//        CodeFirstExecutable codeFirstExecutable2 = databaseCodeFirst.dropTables(Arrays.asList(MyMigrationBlog0.class));
//        codeFirstExecutable2.commands(arg->{
//            System.out.println("执行删除");
//            System.out.println(arg.sql);
//        });
        if (databaseCodeFirst.tableExists(MyMigrationBlog0.class)) {
            System.out.println("存在表:MyMigrationBlog0");
            boolean any = easyEntityQuery.queryable(MyMigrationBlog0.class).any();
            CodeFirstCommand codeFirstCommand = databaseCodeFirst.dropTableCommand(Arrays.asList(MyMigrationBlog0.class));
            codeFirstCommand.forEach(arg -> {
                System.out.println("执行删除");
                System.out.println(arg.getSQL());
                arg.commit();
            });
        } else {
            System.out.println("不存在表:MyMigrationBlog0");
        }

        CodeFirstCommand codeFirstCommand = databaseCodeFirst.syncTableCommand(Arrays.asList(MyMigrationBlog0.class));
        boolean[] created = {false};
        codeFirstCommand.forEach(arg -> {
            System.out.println(arg.getSQL());
            if (arg.getSQL().contains("CREATE TABLE")) {
                created[0] = true;
            }
            Assert.assertFalse(arg.getSQL().contains("execute immediate"));
            Assert.assertFalse(arg.getSQL().startsWith("\nBEGIN"));
            arg.commit();
        });
        Assert.assertTrue(created[0]);
        boolean any = easyEntityQuery.queryable(MyMigrationBlog0.class).any();
        Assert.assertFalse(any);
        CodeFirstCommand codeFirstCommand1 = databaseCodeFirst.dropTableCommand(Arrays.asList(MyMigrationBlog0.class));
        codeFirstCommand1.forEach(arg -> {
            System.out.println("执行删除");
            System.out.println(arg.getSQL());
            arg.commit();
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
}
