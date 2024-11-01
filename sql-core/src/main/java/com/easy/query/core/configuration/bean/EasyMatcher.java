package com.easy.query.core.configuration.bean;


import java.lang.reflect.Field;

/**
 * create time 2024/11/1 16:48
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EasyMatcher {
    /**
     * 匹配实际的属性
     * @param entityClass 实体类
     * @param field 字段
     * @return 返回null表示没有匹配到
     */
    PropertyDescriptorResult match(Class<?> entityClass, Field field);
}
