package com.easy.query.core.basic.extension.navigate;

import com.easy.query.core.annotation.IncludeOnProperty;

import java.util.List;
import java.util.Objects;

/**
 * create time 2026/6/14 21:58
 * 文件说明
 *
 * @author xuejiaming
 */
public class MemoryFilterConfiguration {
    /**
     * 导航属性依赖的属性
     */
    private final String[] dependencies;
    private final IncludeOnProperty[] includeOnProperties;

    public MemoryFilterConfiguration(IncludeOnProperty[] includeOnProperties) {
        String[] dependencies = new String[includeOnProperties.length];
        for (int i = 0; i < includeOnProperties.length; i++) {
            dependencies[i] = includeOnProperties[i].name();
        }
        this.dependencies = dependencies;
        this.includeOnProperties = includeOnProperties;
    }

    public boolean canInclude(List<Object> values) {
        for (int i = 0; i < includeOnProperties.length; i++) {
            boolean whenAssert = includeConditionAssert(includeOnProperties[i], values.get(i));
            if (!whenAssert) {
                return false;
            }
        }
        return true;
    }

    private boolean includeConditionAssert(IncludeOnProperty includeOnProperty, Object value) {
        if (includeOnProperty.matchNull()) {
            return Objects.isNull(value)||Objects.equals(value,"");
        }
        if (value == null) {
            return false;
        }
        if(includeOnProperty.valueContains()){
            if (value instanceof String) {
                return includeOnProperty.value().contains((String) value);
            }
            return includeOnProperty.value().contains(value.toString());
        }
        if (value instanceof String) {
            return Objects.equals(value, includeOnProperty.value());
        }
        return Objects.equals(value.toString(), includeOnProperty.value());
    }

    public String[] getDependencies() {
        return dependencies;
    }

    public IncludeOnProperty[] getIncludeConditions() {
        return includeOnProperties;
    }
}
