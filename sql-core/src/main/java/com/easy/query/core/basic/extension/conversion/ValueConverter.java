package com.easy.query.core.basic.extension.conversion;

/**
 * create time 2023/5/20 21:59
 * 对象属性和数据库列转换器
 *
 * @param <TProperty> 对象属性类型
 * @param <TProvider> 数据库列类型
 * @author xuejiaming
 */

public interface ValueConverter<TProperty,TProvider> {
    /**
     * 参数是否为null都会调用这个方法
     * @param property 属性值
     * @return 数据库列
     */
    TProvider serialize(TProperty property);

    /**
     * 参数是否为null都会调用这个方法,但是如果对应的表无法获取就会跳过方法
     * @param propertyClass 属性字节
     * @param provider 数据库值
     * @return 属性值
     */
    TProperty deserialize(Class<TProperty> propertyClass,TProvider provider);
}
