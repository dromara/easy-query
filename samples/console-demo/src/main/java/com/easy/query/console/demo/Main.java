package com.easy.query.console.demo;

import com.easy.query.api.proxy.client.DefaultEasyProxyQuery;
import com.easy.query.api.proxy.client.EasyProxyQuery;
import com.easy.query.console.demo.proxy.TopicProxy;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.bootstrapper.EasyQueryBootstrapper;
import com.easy.query.core.configuration.nameconversion.NameConversion;
import com.easy.query.core.configuration.nameconversion.impl.UnderlinedNameConversion;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.mysql.config.MySQLDatabaseConfiguration;
import com.zaxxer.hikari.HikariDataSource;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        //设置日志打印为控制台
        LogFactory.useStdOutLogging();
        //初始化连接池
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/console-demo?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true&rewriteBatchedStatements=true");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setMaximumPoolSize(20);

        //初始化属性模式
        EasyQueryClient easyQueryClient = EasyQueryBootstrapper.defaultBuilderConfiguration()
                .setDefaultDataSource(dataSource)
                .optionConfigure(op -> {
                    op.setDeleteThrowError(true);//设置不允许物理删除
                    op.setPrintSql(true);//设置以log.info模式打印执行sql信息
                })
                .replaceService(NameConversion.class, UnderlinedNameConversion.class)//替换框架内部的属性和列转换模式改为大写转下划线
                .useDatabaseConfigure(new MySQLDatabaseConfiguration())//设置方言语法等为mysql的
                .build();
        //创建代理模式api查询
        EasyProxyQuery easyProxyQuery = new DefaultEasyProxyQuery(easyQueryClient);
        List<Topic> topics = easyProxyQuery.queryable(TopicProxy.DEFAULT)
                .where((filter, o) -> filter.eq(o.id(), "123").like(o.name(), "您好"))
                .orderByAsc((order, o) -> order.columns(o.createTime(), o.id()))
                .select((selector, o) -> selector.columns(o.no(), o.id(), o.name()))
                .toList();
    }
}