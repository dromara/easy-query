package com.easy.query.core.basic.plugin.conversion;

/**
 * create time 2023/5/20 21:59
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ValueConverter<TProperty,TProvider> {
    /**
     * 参数是否为null都会调用这个方法
     * @param property
     * @return
     */
    TProvider serialize(TProperty property);

    /**
     * 参数是否为null都会调用这个方法,但是如果对应的表无法获取就会跳过方法
     * @param propertyClass
     * @param provider
     * @return
     */
    TProperty deserialize(Class<TProperty> propertyClass,TProvider provider);
}
