package com.easy.query.test.conversion;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.easy.query.core.basic.extension.complex.ComplexPropType;
import com.easy.query.core.basic.extension.conversion.ValueConverter;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.util.EasyStringUtil;
import org.jetbrains.annotations.NotNull;

/**
 * create time 2023/5/23 22:33
 * 文件说明
 *
 * @author xuejiaming
 */
public class JsonConverter implements ValueConverter<Object, String> {
    @Override
    public String serialize(Object o, @NotNull ColumnMetadata columnMetadata) {
        if(o==null){
            return null;
        }
        return JSON.toJSONString(o, JSONWriter.Feature.WriteMapNullValue, JSONWriter.Feature.WriteNullListAsEmpty, JSONWriter.Feature.WriteNullStringAsEmpty);
    }

    @Override
    public Object deserialize(String s, @NotNull ColumnMetadata columnMetadata) {
        if(EasyStringUtil.isBlank(s)){
            return null;
        }
        ComplexPropType complexType = columnMetadata.getComplexPropType();
        return JSON.parseObject(s, complexType.complexType());
    }
}
