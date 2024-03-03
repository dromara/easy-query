package com.easy.query.api.proxy.key;

/**
 * create time 2024/3/2 19:52
 * 文件说明
 *
 * @author xuejiaming
 */
public class ByteMapKey implements MapKey<Byte> {
    private final String name;

    public ByteMapKey(String name){

        this.name = name;
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public Class<Byte> getPropType() {
        return Byte.class;
    }
}
