package com.easy.query.api.proxy.key;

/**
 * create time 2024/3/2 19:52
 * 文件说明
 *
 * @author xuejiaming
 */
public class StringMapKey implements MapKey<String> {
    private final String name;

    public StringMapKey(String name){

        this.name = name;
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public Class<String> getPropType() {
        return String.class;
    }
}
