package com.easy.query.api.proxy.key;

import java.time.LocalTime;

/**
 * create time 2024/3/2 19:52
 * 文件说明
 *
 * @author xuejiaming
 */
public class LocalTimeMapKey implements MapKey<LocalTime> {
    private final String name;

    public LocalTimeMapKey(String name){

        this.name = name;
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public Class<LocalTime> getPropType() {
        return LocalTime.class;
    }
}
