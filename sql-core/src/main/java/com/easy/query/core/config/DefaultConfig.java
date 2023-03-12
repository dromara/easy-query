package com.easy.query.core.config;

/**
 * @FileName: DefaultConfig.java
 * @Description: 文件说明
 * @Date: 2023/2/16 21:47
 * @Created by xuejiaming
 */
public class DefaultConfig implements EasyConfig {
    private final String name;
    private final String driver;
    private final String username;
    private final String password;
    private final String url;

    public DefaultConfig(String name,String driver,String username, String password, String url){
        this.name = name;
        this.driver = driver;

        this.username = username;
        this.password = password;
        this.url = url;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDriver() {
        return driver;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUrl() {
        return url;
    }
}
