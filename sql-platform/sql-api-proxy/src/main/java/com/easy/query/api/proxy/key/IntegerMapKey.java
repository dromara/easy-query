package com.easy.query.api.proxy.key;

/**
 * create time 2024/3/2 19:52
 * 文件说明
 *
 * @author xuejiaming
 */
public class IntegerMapKey implements MapKey<Integer> {
    private final String name;

    public IntegerMapKey(String name){

        this.name = name;
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public Class<Integer> getPropType() {
        return Integer.class;
    }
}
