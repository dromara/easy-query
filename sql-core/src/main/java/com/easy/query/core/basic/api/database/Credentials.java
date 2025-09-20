package com.easy.query.core.basic.api.database;

/**
 * create time 2025/9/20 13:43
 * 文件说明
 *
 * @author xuejiaming
 */
public class Credentials {

    public final String jdbcUrl;
    public final String username;
    public final String password;

    public  Credentials(String jdbcUrl, String username, String password){
        this.jdbcUrl = jdbcUrl;
        this.username = username;
        this.password = password;
    }
}
