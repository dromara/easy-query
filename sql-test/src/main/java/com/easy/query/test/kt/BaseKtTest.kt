package com.easy.query.test.kt

import com.easy.query.api.proxy.client.DefaultEasyEntityQuery
import com.easy.query.api.proxy.client.DefaultEasyProxyQuery
import com.easy.query.api.proxy.client.EasyEntityQuery
import com.easy.query.api.proxy.client.EasyProxyQuery
import com.easy.query.api4j.client.EasyQuery
import com.easy.query.api4kt.client.DefaultEasyKtQuery
import com.easy.query.api4kt.client.EasyKtQuery
import com.easy.query.core.api.client.EasyQueryClient
import com.easy.query.core.bootstrapper.EasyQueryBootstrapper
import com.easy.query.core.configuration.EasyQueryShardingOption
import com.easy.query.core.logging.LogFactory
import com.easy.query.mysql.config.MySQLDatabaseConfiguration
import com.easy.query.pgsql.config.PgSQLDatabaseConfiguration
import com.zaxxer.hikari.HikariDataSource

open class BaseKtTest {

    // 伴生对象，用于模拟静态初始化代码块
    companion object {
        var dataSource: HikariDataSource? = null
        var easyQueryClient: EasyQueryClient? = null
        var easyKtQuery: EasyKtQuery? = null
        var easyEntityQuery: EasyEntityQuery? = null
        var easyProxyQuery: EasyProxyQuery? = null
        init {
            // 在这里编写静态初始化代码
            LogFactory.useStdOutLogging();
            var hikariDataSource = HikariDataSource()
            hikariDataSource.jdbcUrl =
                "jdbc:mysql://127.0.0.1:3306/easy-query-test?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true&rewriteBatchedStatements=true";
            hikariDataSource.username = "root";
            hikariDataSource.password = "root";
            hikariDataSource.driverClassName = "com.mysql.cj.jdbc.Driver";
            hikariDataSource.maximumPoolSize = 20;
            LogFactory.useStdOutLogging();


            easyQueryClient = EasyQueryBootstrapper.defaultBuilderConfiguration()
                .setDefaultDataSource(hikariDataSource)
                .useDatabaseConfigure(MySQLDatabaseConfiguration())
                .build()
            easyKtQuery= DefaultEasyKtQuery(easyQueryClient);
            easyEntityQuery= DefaultEasyEntityQuery(easyQueryClient);
            easyProxyQuery= DefaultEasyProxyQuery(easyQueryClient);
        }

        // 伴生对象的其他成员（可以包括属性、方法等）
        fun staticFunction() {
            println("这是一个静态函数")
        }
    }
}