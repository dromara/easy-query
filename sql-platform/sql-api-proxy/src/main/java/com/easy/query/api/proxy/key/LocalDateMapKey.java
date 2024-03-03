package com.easy.query.api.proxy.key;

import java.time.LocalDate;

/**
 * create time 2024/3/2 19:52
 * 文件说明
 *
 * @author xuejiaming
 */
public class LocalDateMapKey implements MapKey<LocalDate> {
    private final String name;

    public LocalDateMapKey(String name){

        this.name = name;
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public Class<LocalDate> getPropType() {
        return LocalDate.class;
    }
}
