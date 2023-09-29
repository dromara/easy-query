package com.easy.query.test.conversion;

import com.alibaba.fastjson2.TypeReference;
import com.easy.query.core.basic.extension.complex.ComplexPropType;
import com.easy.query.test.entity.TopicTypeJsonValue;

import java.lang.reflect.Type;
import java.util.List;

/**
 * create time 2023/9/29 09:32
 * 文件说明
 *
 * @author xuejiaming
 */
public class TopicTypeTitle2ComplexType extends TypeReference<List<TopicTypeJsonValue>> implements ComplexPropType {

    @Override
    public Type getComplexType() {
        return this.getType();
    }
}
