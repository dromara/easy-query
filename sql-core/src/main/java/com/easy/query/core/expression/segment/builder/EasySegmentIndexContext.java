package com.easy.query.core.expression.segment.builder;

import com.easy.query.core.expression.segment.SQLEntitySegment;
import com.easy.query.core.expression.segment.index.SegmentIndex;
import com.easy.query.core.util.EasyMapUtil;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * create time 2023/9/1 23:01
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasySegmentIndexContext implements SegmentIndexContext, SegmentIndex {
    private final Map<Class<?>, Set<String>> entityProperties=new LinkedHashMap<>();
    @Override
    public void add(SQLEntitySegment sqlEntitySegment) {
        Set<String> propertySet = EasyMapUtil.computeIfAbsent(entityProperties,sqlEntitySegment.getTable().getEntityClass(), key -> new HashSet<>());
        propertySet.add(sqlEntitySegment.getPropertyName());
    }

    @Override
    public boolean contains(Class<?> entityClass, String propertyName) {
        Set<String> propertySet = entityProperties.get(entityClass);
        if (propertySet != null) {
            return propertySet.contains(propertyName);
        }
        return false;
    }
}
