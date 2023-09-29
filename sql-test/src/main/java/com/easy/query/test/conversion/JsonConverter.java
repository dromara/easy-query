package com.easy.query.test.conversion;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.easy.query.core.basic.extension.complex.ComplexPropType;
import com.easy.query.core.basic.extension.conversion.ValueConverter;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.util.EasyStringUtil;

/**
 * create time 2023/5/23 22:33
 * 文件说明
 *
 * @author xuejiaming
 */
public class JsonConverter implements ValueConverter<Object, String> {
    @Override
    public String serialize(Object o, ColumnMetadata columnMetadata) {
        if(o==null){
            return null;
        }
        return JSON.toJSONString(o, JSONWriter.Feature.WriteMapNullValue, JSONWriter.Feature.WriteNullListAsEmpty, JSONWriter.Feature.WriteNullStringAsEmpty);
    }

    @Override
    public Object deserialize(String s, ColumnMetadata columnMetadata) {
        if(EasyStringUtil.isBlank(s)){
            return null;
        }
        ComplexPropType complexType = columnMetadata.getComplexPropType();
        if(complexType!=null){
            return JSON.parseObject(s, complexType.getComplexType());
        }
        return JSON.parseObject(s, columnMetadata.getPropertyType());
    }
//    @Override
//    public String serialize(Object o) {
//        return JSON.toJSONString(o, SerializerFeature.WriteMapNullValue,
//                SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteNullStringAsEmpty);
//    }
//
//    @Override
//    public Object deserialize(Class<Object> objectClass, String s) {
//        return JSON.parseObject(s, objectClass);
//    }
}
