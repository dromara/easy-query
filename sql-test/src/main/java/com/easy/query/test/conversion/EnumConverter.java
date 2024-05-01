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
public class EnumConverter implements ValueConverter<IEnum<?>,Number> {
    @Override
    public Number serialize(IEnum<?> iEnum, ColumnMetadata columnMetadata) {
        return iEnum.getCode();
    }

    @Override
    public IEnum<?> deserialize(Number integer, ColumnMetadata columnMetadata) {
        return EnumDeserializer.deserialize(EasyObjectUtil.typeCast(columnMetadata.getPropertyType()),integer.intValue());
    }
}
