package com.easy.query.solon.sharding.demo.configuration;

/**
 * create time 2023/11/27 19:48
 * 文件说明
 *
 * @author xuejiaming
 */
public class MyDataSourceNamed implements DataSourceNamed{
    private final String name;

    public MyDataSourceNamed(String name){
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
