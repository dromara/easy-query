package com.easy.query.core.basic.extension.conversion;

import com.easy.query.core.metadata.ColumnMetadata;
import org.jetbrains.annotations.NotNull;

/**
 * create time 2023/5/20 22:38
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultValueConverter implements ValueConverter<Object,Object> {
    public static final ValueConverter<?,?> INSTANCE=new DefaultValueConverter();


    @Override
    public Object serialize(Object o, @NotNull ColumnMetadata columnMetadata) {
        return o;
    }

    @Override
    public Object deserialize(Object o, @NotNull ColumnMetadata columnMetadata) {
        return o;
    }
}
