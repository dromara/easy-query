package com.easy.query.test;

import com.easy.query.api.proxy.client.DefaultEasyEntityQuery;
import com.easy.query.api.proxy.client.DefaultEasyProxyQuery;
import com.easy.query.api.proxy.client.EasyEntityQuery;
import com.easy.query.api.proxy.client.EasyProxyQuery;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.bootstrapper.EasyQueryBootstrapper;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.mysql.config.MySQLDatabaseConfiguration;
import com.easy.query.test.entity.Topic;
import com.zaxxer.hikari.HikariDataSource;

/**
 * create time 2024/5/27 22:31
 * 文件说明
 *
 * @author xuejiaming
 */
public class AbcMain {
    public static void main(String[] args) {

        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/easy-query-test?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true&rewriteBatchedStatements=true");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setMaximumPoolSize(20);
        //采用控制台输出打印sql
        LogFactory.useStdOutLogging();
        //property的api
        EasyQueryClient easyQueryClient = EasyQueryBootstrapper.defaultBuilderConfiguration()
                .setDefaultDataSource(dataSource)
                .optionConfigure(op -> {
                    op.setPrintSql(true);
                    op.setKeepNativeStyle(true);
                })
                .useDatabaseConfigure(new MySQLDatabaseConfiguration())
                .build();
        Topic topic = new Topic();
        String id = topic.getId();

        EasyEntityQuery easyEntityQuery = new DefaultEasyEntityQuery(easyQueryClient);
        //不需要实现接口但是使用方式有点区别
        EasyProxyQuery easyProxyQuery = new DefaultEasyProxyQuery(easyQueryClient);

        //根据id查询第一条
        Topic topic1 = easyEntityQuery.queryable(Topic.class)
                .whereById("1").firstOrNull();

        //根据id查询并且断言仅一条
        Topic topic2 = easyEntityQuery.queryable(Topic.class)
                .whereById("1").singleOrNull();

        String ids="1,2,3";
        //根据id查询自定义条件返回第一条
        Topic topic3 = easyEntityQuery.queryable(Topic.class)
                .where(o -> {
                    o.id().eq("1");
                    o.id().in(ids.split(","));
                })
                .firstOrNull();
    }
}
