package com.easy.query.core.basic.extension.conversion;

import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * create time 2023/5/20 21:59
 * 对象属性和数据库列转换器
 *
 * @param <TProperty> 对象属性类型
 * @param <TProvider> 数据库列类型
 * @author xuejiaming
 */

public interface ValueConverter<TProperty, TProvider> {
    /**
     * 这个方法将在不久后被去掉
     * 请使用 {@link ValueConverter#serialize(Object, ColumnMetadata)}
     * 参数是否为null都会调用这个方法
     *
     * @param property 属性值
     * @return 数据库列
     */
    @Deprecated
    default TProvider serialize(TProperty property) {
        return EasyObjectUtil.typeCastNullable(property);
    }

    /**
     * 参数是否为null都会调用这个方法
     *
     * @param property       属性值
     * @param columnMetadata 对应的列
     * @return
     */
    default TProvider serialize(TProperty property, ColumnMetadata columnMetadata) {
        return serialize(property);
    }

    /**
     * 这个方法将在不久后被去掉
     * 请使用 {@link ValueConverter#deserialize(Object, ColumnMetadata)} 方法
     * 参数是否为null都会调用这个方法,但是如果对应的表无法获取就会跳过方法
     *
     * @param propertyClass 属性字节
     * @param provider      数据库值
     * @return 属性值
     */
    @Deprecated
    default TProperty deserialize(Class<TProperty> propertyClass, TProvider provider) {
        return EasyObjectUtil.typeCastNullable(provider);
    }

    /**
     * 参数是否为null都会调用这个方法,但是如果对应的表无法获取就会跳过方法
     *
     * @param provider
     * @param columnMetadata
     * @return
     */
    default TProperty deserialize(TProvider provider, ColumnMetadata columnMetadata) {
        return deserialize(EasyObjectUtil.typeCastNullable(columnMetadata.getPropertyType()), provider);
    }
}
