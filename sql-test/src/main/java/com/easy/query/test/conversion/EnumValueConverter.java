package com.easy.query.test.conversion;

import com.easy.query.core.basic.extension.conversion.ValueConverter;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * create time 2023/5/22 14:13
 * 文件说明
 *
 * @author xuejiaming
 */
public class EnumValueConverter implements ValueConverter<Enum<?>,Integer> {
    @Override
    public Integer serialize(Enum<?> enumValue, ColumnMetadata columnMetadata) {
        return (Integer) EnumValueDeserializer.serialize(enumValue);
    }

    @Override
    public Enum<?> deserialize(Integer integer, ColumnMetadata columnMetadata) {
        return EnumValueDeserializer.deserialize(EasyObjectUtil.typeCast(columnMetadata.getPropertyType()),integer);
    }
}
