package com.easy.query.api.proxy.key;

/**
 * create time 2024/3/2 19:52
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLDateMapKey implements MapKey<java.sql.Date> {
    private final String name;

    public SQLDateMapKey(String name){

        this.name = name;
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public Class<java.sql.Date> getPropType() {
        return java.sql.Date.class;
    }
}
