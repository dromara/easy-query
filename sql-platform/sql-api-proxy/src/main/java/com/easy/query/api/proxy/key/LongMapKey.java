package com.easy.query.api.proxy.key;

import java.time.LocalTime;

/**
 * create time 2024/3/2 19:52
 * 文件说明
 *
 * @author xuejiaming
 */
public class LongMapKey implements MapKey<Long> {
    private final String name;

    public LongMapKey(String name){

        this.name = name;
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public Class<Long> getPropType() {
        return Long.class;
    }
}
