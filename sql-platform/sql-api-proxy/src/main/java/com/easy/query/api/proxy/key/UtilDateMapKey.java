package com.easy.query.api.proxy.key;

/**
 * create time 2024/3/2 19:52
 * 文件说明
 *
 * @author xuejiaming
 */
public class UtilDateMapKey implements MapKey<java.util.Date> {
    private final String name;

    public UtilDateMapKey(String name){

        this.name = name;
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public Class<java.util.Date> getPropType() {
        return java.util.Date.class;
    }
}
