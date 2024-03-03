package com.easy.query.api.proxy.key;

import java.sql.Timestamp;

/**
 * create time 2024/3/2 19:52
 * 文件说明
 *
 * @author xuejiaming
 */
public class TimestampMapKey implements MapKey<Timestamp> {
    private final String name;

    public TimestampMapKey(String name){

        this.name = name;
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public Class<Timestamp> getPropType() {
        return Timestamp.class;
    }
}
