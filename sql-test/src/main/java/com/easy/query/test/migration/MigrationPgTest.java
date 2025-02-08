package com.easy.query.test.migration;

import com.easy.query.api.proxy.client.DefaultEasyEntityQuery;
import com.easy.query.api.proxy.client.EasyEntityQuery;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.api.database.CodeFirstCommand;
import com.easy.query.core.basic.api.database.DatabaseCodeFirst;
import com.easy.query.core.bootstrapper.EasyQueryBootstrapper;
import com.easy.query.core.configuration.QueryConfiguration;
import com.easy.query.pgsql.config.PgSQLDatabaseConfiguration;
import com.easy.query.test.common.MD5Util;
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
public class MigrationPgTest {
    @Test
    public void test(){

        HikariDataSource   dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:postgresql://127.0.0.1:55000/easy-query-test?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true&rewriteBatchedStatements=true");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgrespw");
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setMaximumPoolSize(50);
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
                .useDatabaseConfigure(new PgSQLDatabaseConfiguration())
                .build();
        EasyEntityQuery easyEntityQuery = new DefaultEasyEntityQuery(easyQueryClient);




        DatabaseCodeFirst databaseCodeFirst = easyEntityQuery.getDatabaseCodeFirst();
        if (databaseCodeFirst.tableExists(MyMigrationBlog0.class)) {
            System.out.println("存在表:MyMigrationBlog0");
            boolean any = easyEntityQuery.queryable(MyMigrationBlog0.class).any();
            CodeFirstCommand codeFirstCommand = databaseCodeFirst.dropTableCommand(Arrays.asList(MyMigrationBlog0.class));
            codeFirstCommand.executeWithTransaction(arg -> {
                System.out.println("执行删除");
                System.out.println(arg.sql);
            });
        } else {
            System.out.println("不存在表:MyMigrationBlog0");
        }

        CodeFirstCommand codeFirstCommand = databaseCodeFirst.syncTableCommand(Arrays.asList(MyMigrationBlog0.class));
        codeFirstCommand.executeWithTransaction(arg -> {
            System.out.println(arg.sql);
            String md5 = MD5Util.getMD5Hash(arg.sql);
            System.out.println("sql-hash:"+md5);
            Assert.assertEquals("1cd82fb71caf196a00eaa729911127b2", md5);
            arg.commit();
        });
        boolean any = easyEntityQuery.queryable(MyMigrationBlog0.class).any();
        Assert.assertFalse(any);
        CodeFirstCommand codeFirstCommand1 = databaseCodeFirst.dropTableCommand(Arrays.asList(MyMigrationBlog0.class));
        codeFirstCommand1.executeWithEnvTransaction(arg->{
            System.out.println("执行删除");
            System.out.println(arg.sql);
        });
        Exception ex=null;
        try {
            easyEntityQuery.queryable(MyMigrationBlog0.class).any();
        } catch (Exception e) {
            ex=e;
            System.out.println(ex);
        }
        Assert.assertNotNull(ex);
    }
}
