package com.easy.query.core.basic.plugin.conversion;

/**
 * create time 2023/5/20 22:38
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultValueConverter implements ValueConverter<Object,Object> {
    public static final ValueConverter<?,?> INSTANCE=new DefaultValueConverter();
    @Override
    public Object serialize(Object o) {
        return o;
    }

    @Override
    public Object deserialize(Class<Object> propertyClass,Object o) {
        return o;
    }
}
