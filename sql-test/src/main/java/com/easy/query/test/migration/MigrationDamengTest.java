package com.easy.query.test.migration;

import com.easy.query.api.proxy.client.DefaultEasyEntityQuery;
import com.easy.query.api.proxy.client.EasyEntityQuery;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.api.database.CodeFirstExecutable;
import com.easy.query.core.basic.api.database.DatabaseCodeFirst;
import com.easy.query.core.bootstrapper.EasyQueryBootstrapper;
import com.easy.query.core.configuration.QueryConfiguration;
import com.easy.query.dameng.config.DamengDatabaseConfiguration;
import com.easy.query.mysql.config.MySQLDatabaseConfiguration;
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
//        codeFirstExecutable2.executeWithTransaction(arg->{
//            System.out.println("执行删除");
//            System.out.println(arg.sql);
//        });
        if (databaseCodeFirst.tableExists(MyMigrationBlog0.class)) {
            System.out.println("存在表:MyMigrationBlog0");
            boolean any = easyEntityQuery.queryable(MyMigrationBlog0.class).any();
            CodeFirstExecutable codeFirstExecutable = databaseCodeFirst.dropTables(Arrays.asList(MyMigrationBlog0.class));
            codeFirstExecutable.executeWithTransaction(arg -> {
                System.out.println("执行删除");
                System.out.println(arg.sql);
            });
        } else {
            System.out.println("不存在表:MyMigrationBlog0");
        }

        CodeFirstExecutable codeFirstExecutable = databaseCodeFirst.syncTables(Arrays.asList(MyMigrationBlog0.class));
        codeFirstExecutable.executeWithTransaction(arg -> {
            System.out.println(arg.sql);
            String md5 = MD5Util.getMD5Hash(arg.sql);
            System.out.println("sql-hash:" + md5);
            Assert.assertEquals("1be020b5a8148a98662bf57602f5bce9", md5);
//            arg.commit();
        });
        boolean any = easyEntityQuery.queryable(MyMigrationBlog0.class).any();
        Assert.assertFalse(any);
        CodeFirstExecutable codeFirstExecutable1 = databaseCodeFirst.dropTables(Arrays.asList(MyMigrationBlog0.class));
        codeFirstExecutable1.executeWithTransaction(arg -> {
            System.out.println("执行删除");
            System.out.println(arg.sql);
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
