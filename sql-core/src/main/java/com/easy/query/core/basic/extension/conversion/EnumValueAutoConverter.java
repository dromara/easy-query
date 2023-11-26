package com.easy.query.core.basic.extension.conversion;

import com.easy.query.core.annotation.NotNull;
import com.easy.query.core.annotation.Nullable;

/**
 * create time 2023/11/22 21:41
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EnumValueAutoConverter<TProperty, TProvider> extends ValueConverter<TProperty, TProvider> {
    boolean apply(@NotNull Class<?> entityClass,@Nullable Class<TProperty> propertyType);
}
