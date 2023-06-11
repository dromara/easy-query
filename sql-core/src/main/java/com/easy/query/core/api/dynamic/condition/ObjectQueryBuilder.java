package com.easy.query.core.api.dynamic.condition;

import java.util.Map;

/**
 * @FileName: EasyDynamicWhereBuilder.java
 * @Description: 文件说明
 * @Date: 2023/3/24 07:54
 * @author xuejiaming
 */
public interface ObjectQueryBuilder {
    ObjectQueryBuilder property(String entityPropertyName, String propertyName);
    ObjectQueryBuilder property(String entityPropertyName, String propertyName1, String propertyName2);
    Map<String, String> build();
}
