//package com.easy.query.test;
//
//import com.easy.query.api.proxy.client.DefaultEasyEntityQuery;
//import com.easy.query.api.proxy.client.EasyEntityQuery;
//import com.easy.query.core.api.client.EasyQueryClient;
//import com.easy.query.core.bootstrapper.EasyQueryBootstrapper;
//import com.easy.query.core.logging.LogFactory;
//import com.easy.query.mysql.config.MySQLDatabaseConfiguration;
//import com.easy.query.test.entity.Topic;
//import com.zaxxer.hikari.HikariDataSource;
//import org.junit.Test;
//
//import java.util.List;
//
///**
// * create time 2024/2/23 21:08
// * 文件说明
// *
// * @author xuejiaming
// */
//public class MyUnitTest {
//    static EasyEntityQuery easyEntityQuery;
//    static {
//        HikariDataSource dataSource = new HikariDataSource();
//        dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/easy-query-test?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true&rewriteBatchedStatements=true");
//        dataSource.setUsername("root");
//        dataSource.setPassword("root");
//        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
//        dataSource.setMaximumPoolSize(20);
//        //采用控制台输出打印sql
//        LogFactory.useStdOutLogging();
//        //property的api
//        EasyQueryClient easyQueryClient = EasyQueryBootstrapper.defaultBuilderConfiguration()
//                .setDefaultDataSource(dataSource)
//                .optionConfigure(op -> {
//                    op.setPrintSql(true);
//                    op.setKeepNativeStyle(true);
//                })
//                .useDatabaseConfigure(new MySQLDatabaseConfiguration())
//                .build();
//        easyEntityQuery=new DefaultEasyEntityQuery(easyQueryClient);
//    }
//    @Test
//    public void Test(){
//        List<Topic> list = easyEntityQuery.queryable(Topic.class)
//                .where(t -> t.id().eq("123")).toList();
//    }
//}
