package com.easy.query.api.proxy.key;

import java.sql.Time;

/**
 * create time 2024/3/2 19:52
 * 文件说明
 *
 * @author xuejiaming
 */
public class TimeMapKey implements MapKey<Time> {
    private final String name;

    public TimeMapKey(String name){

        this.name = name;
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public Class<Time> getPropType() {
        return Time.class;
    }
}
