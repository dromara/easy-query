package com.easy.query.core.basic.plugin.conversion;

/**
 * create time 2023/5/20 21:59
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ValueConverter<TProperty,TProvider> {
    TProvider serialize(TProperty property);
    TProperty deserialize(Class<TProperty> propertyClass,TProvider provider);
}
