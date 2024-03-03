package com.easy.query.api.proxy.key;

/**
 * create time 2024/3/2 19:52
 * 文件说明
 *
 * @author xuejiaming
 */
public class ShortMapKey implements MapKey<Short> {
    private final String name;

    public ShortMapKey(String name){

        this.name = name;
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public Class<Short> getPropType() {
        return Short.class;
    }
}
