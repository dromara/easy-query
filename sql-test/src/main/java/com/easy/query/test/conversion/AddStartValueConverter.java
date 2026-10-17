package com.easy.query.test.conversion;

import com.easy.query.core.basic.extension.conversion.ValueConverter;
import com.easy.query.core.metadata.ColumnMetadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * create time 2026/10/17 22:04
 * 文件说明
 *
 * @author xuejiaming
 */
public class AddStartValueConverter  implements ValueConverter<Object,Object> {
    @Nullable
    @Override
    public Object serialize(@Nullable Object property, @NotNull ColumnMetadata columnMetadata) {
        return property;
    }

    @Nullable
    @Override
    public Object deserialize(@Nullable Object o, @NotNull ColumnMetadata columnMetadata) {
        return o+"*";
    }
}
