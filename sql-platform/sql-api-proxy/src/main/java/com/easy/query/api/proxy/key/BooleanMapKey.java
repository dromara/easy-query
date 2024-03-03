package com.easy.query.api.proxy.key;

/**
 * create time 2024/3/2 19:52
 * 文件说明
 *
 * @author xuejiaming
 */
public class BooleanMapKey implements MapKey<Boolean> {
    private final String name;

    public BooleanMapKey(String name){

        this.name = name;
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public Class<Boolean> getPropType() {
        return Boolean.class;
    }
}
