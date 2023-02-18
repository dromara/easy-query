package org.easy.test;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.easy.query.core.config.EasyConfig;
import org.easy.query.core.config.EasyDataSourceFactory;
import org.easy.query.core.exception.JDQCException;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @FileName: DefaultDataSourceFactory.java
 * @Description: 文件说明
 * @Date: 2023/2/16 21:59
 * @Created by xuejiaming
 */
public class DefaultDataSourceFactory implements EasyDataSourceFactory {
    private final DataSource dataSource;
    public DefaultDataSourceFactory(EasyConfig easyConfig){


        // 设置properties
        Properties properties = new Properties();
        properties.setProperty("name", easyConfig.getName());
        properties.setProperty("driverClassName", easyConfig.getDriver());
        properties.setProperty("url",easyConfig.getUrl());
        properties.setProperty("username", easyConfig.getUsername());
        properties.setProperty("password", easyConfig.getPassword());
        int i = Runtime.getRuntime().availableProcessors();
        properties.setProperty("initialSize", String.valueOf(i));
        properties.setProperty("maxActive", String.valueOf(2 * i + 1));
        properties.setProperty("minIdle", String.valueOf(i));
        try {
            this.dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            throw new JDQCException(e);
        }

    }
    @Override
    public DataSource createDataSource() {
        return dataSource;
    }
}
