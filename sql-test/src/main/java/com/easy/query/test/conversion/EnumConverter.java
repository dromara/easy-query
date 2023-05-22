package com.easy.query.test.conversion;

import com.easy.query.core.basic.plugin.conversion.ValueConverter;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * create time 2023/5/22 14:13
 * 文件说明
 *
 * @author xuejiaming
 */
public class EnumConverter implements ValueConverter<IEnum<?>,Integer> {
    @Override
    public Integer serialize(IEnum<?> anEnum) {
        return anEnum.getCode();
    }

    @Override
    public IEnum<?> deserialize(Class<IEnum<?>> propertyClass,Integer integer) {
        return EnumDeserializer.deserialize(EasyObjectUtil.typeCast(propertyClass),integer);
    }
}
