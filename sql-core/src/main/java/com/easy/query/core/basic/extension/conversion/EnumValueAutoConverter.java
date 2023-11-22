package com.easy.query.core.basic.extension.conversion;

/**
 * create time 2023/11/22 21:41
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EnumValueAutoConverter<TProperty, TProvider> extends ValueConverter<TProperty, TProvider> {
    boolean apply(Class<?> entityClass,Class<TProperty> propertyType);
}
