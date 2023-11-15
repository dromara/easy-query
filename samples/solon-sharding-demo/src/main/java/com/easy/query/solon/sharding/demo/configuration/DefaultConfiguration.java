package com.easy.query.solon.sharding.demo.configuration;

import com.easy.query.core.configuration.QueryConfiguration;
import com.easy.query.solon.annotation.Db;
import com.easy.query.solon.sharding.demo.encrypt.JavaEncryptionStrategy;
import com.easy.query.solon.sharding.demo.encrypt.MySQLAESColumnValueSQLConverter;
import com.zaxxer.hikari.HikariDataSource;
import org.noear.solon.annotation.Bean;
import org.noear.solon.annotation.Configuration;
import org.noear.solon.annotation.Inject;

import javax.sql.DataSource;

/**
 * create time 2023/8/12 21:42
 * 文件说明
 *
 * @author xuejiaming
 */
@Configuration
public class DefaultConfiguration {
    @Bean(name = "db1",typed=true)
    public DataSource db1DataSource(@Inject("${db1}") HikariDataSource dataSource){
        return dataSource;
    }
    @Bean
    public void db1QueryConfiguration(@Db("db1") QueryConfiguration configuration){
        configuration.applyEncryptionStrategy(new JavaEncryptionStrategy());
        configuration.applyColumnValueSQLConverter(new MySQLAESColumnValueSQLConverter());
    }
//        /**
//     * 添加分表或者分库的路由,分库数据源
//     * @param runtimeContext
//     */
//    @Bean
//    public void db1QueryRuntimeContext(@Db("db1") QueryRuntimeContext runtimeContext,@Db("db1") EasyQuery easyQuery){
//        TableRouteManager tableRouteManager = runtimeContext.getTableRouteManager();
//        tableRouteManager.addRoute(new OrderTestTableRoute(easyQuery));
//    }
}
