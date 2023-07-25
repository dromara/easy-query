package com.easy.query.solon.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.noear.solon.annotation.Bean;
import org.noear.solon.annotation.Configuration;
import org.noear.solon.annotation.Inject;

import javax.sql.DataSource;

/**
 * create time 2023/7/25 13:46
 * 文件说明
 *
 * @author xuejiaming
 */
@Configuration
public class WebConfiguration {
    @Bean(value = "db1")
    public DataSource db1DataSource(@Inject("${db1}") HikariDataSource dataSource){
        return dataSource;
    }
}
