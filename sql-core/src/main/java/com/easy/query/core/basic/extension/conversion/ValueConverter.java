package com.easy.query.core.basic.extension.conversion;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.easy.query.core.metadata.ColumnMetadata;

/**
 * create time 2023/5/20 21:59
 * 对象属性和数据库列转换器
 *
 * @param <TPropertyType> 对象属性类型
 * @param <TProvider> 数据库列类型
 * @author xuejiaming
 */

public interface ValueConverter<TPropertyType, TProvider> {
    /**
     * 参数是否为null都会调用这个方法
     * 转成数据库写入的实际java值比如枚举转成int或者String
     *
     * @param property       属性值
     * @param columnMetadata 对应的列
     * @return
     */
    @Nullable TProvider serialize(@Nullable TPropertyType property, @NotNull ColumnMetadata columnMetadata);


    /**
     * 参数是否为null都会调用这个方法,但是如果对应的表无法获取就会跳过方法
     * 将数据库取出来的值转成java的值比如int转成枚举
     *
     * @param provider
     * @param columnMetadata
     * @return
     */
    @Nullable TPropertyType deserialize(@Nullable TProvider provider, @NotNull ColumnMetadata columnMetadata);

}
