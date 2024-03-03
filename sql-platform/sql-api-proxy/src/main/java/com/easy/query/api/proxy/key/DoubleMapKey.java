package com.easy.query.api.proxy.key;

/**
 * create time 2024/3/2 19:52
 * 文件说明
 *
 * @author xuejiaming
 */
public class DoubleMapKey implements MapKey<Double> {
    private final String name;

    public DoubleMapKey(String name){

        this.name = name;
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public Class<Double> getPropType() {
        return Double.class;
    }
}
