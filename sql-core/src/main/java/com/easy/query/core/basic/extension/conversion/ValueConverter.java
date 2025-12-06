package com.easy.query.core.basic.extension.conversion;

import com.easy.query.core.migration.ColumnDbTypeResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.easy.query.core.metadata.ColumnMetadata;

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
     * 参数是否为null都会调用这个方法
     *
     * @param property       属性值
     * @param columnMetadata 对应的列
     * @return
     */
    @Nullable TProvider serialize(@Nullable TProperty property,@NotNull ColumnMetadata columnMetadata);


    /**
     * 参数是否为null都会调用这个方法,但是如果对应的表无法获取就会跳过方法
     *
     * @param provider
     * @param columnMetadata
     * @return
     */
    @Nullable TProperty deserialize(@Nullable TProvider provider,@NotNull ColumnMetadata columnMetadata);

}
