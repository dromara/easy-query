package com.easy.query.api.proxy.key;

import java.time.LocalDateTime;

/**
 * create time 2024/3/2 19:52
 * 文件说明
 *
 * @author xuejiaming
 */
public class LocalDateTimeMapKey implements MapKey<LocalDateTime> {
    private final String name;

    public LocalDateTimeMapKey(String name){

        this.name = name;
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public Class<LocalDateTime> getPropType() {
        return LocalDateTime.class;
    }
}
