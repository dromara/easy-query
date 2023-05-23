package com.easy.query.test.conversion;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.easy.query.core.basic.plugin.conversion.ValueConverter;

/**
 * create time 2023/5/23 22:33
 * 文件说明
 *
 * @author xuejiaming
 */
public class JsonConverter implements ValueConverter<Object,String> {
    @Override
    public String serialize(Object o) {
        return JSON.toJSONString(o, SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteNullStringAsEmpty);
    }

    @Override
    public Object deserialize(Class<Object> objectClass, String s) {
        return JSON.parseObject(s, objectClass);
    }
}
