package com.easy.query.core.metadata;

import com.easy.query.core.basic.extension.conversion.ValueConverter;
import com.easy.query.core.expression.lambda.PropertySetterCaller;

import java.util.List;

/**
 * create time 2023/6/17 19:13
 * 文件说明
 *
 * @author xuejiaming
 */
public class NavigateFlatMetadata {

    private final EntityMetadata entityMetadata;
    private final boolean toMany;
    /**
     * 关联关系
     */
    private final String[] mappingPath;
    private final Class<?> navigatePropertyType;
    private final boolean basicType;
    private final String property;
    private final ColumnMetadata columnMetadata;

    public NavigateFlatMetadata(EntityMetadata entityMetadata,
                                boolean toMany,
                                String[] mappingPath,
                                Class<?> navigatePropertyType,
                                boolean basicType,
                                String property, ColumnMetadata columnMetadata) {
        this.entityMetadata = entityMetadata;
        this.toMany = toMany;
        this.mappingPath = mappingPath;
        this.navigatePropertyType = navigatePropertyType;
        this.basicType=basicType;
        this.property = property;
        this.columnMetadata = columnMetadata;
    }

    public EntityMetadata getEntityMetadata() {
        return entityMetadata;
    }

    public boolean isBasicType() {
        return basicType;
    }

    public boolean isToMany() {
        return toMany;
    }

    public String[] getMappingPath() {
        return mappingPath;
    }

    public PropertySetterCaller<Object> getBeanSetter() {
        return columnMetadata.getSetterCaller();
    }

    public Class<?> getNavigatePropertyType() {
        return navigatePropertyType;
    }

    public String getProperty() {
        return property;
    }

    public ValueConverter<?, ?> getValueConverter() {
        return columnMetadata.getValueConverter();
    }

    public ColumnMetadata getColumnMetadata() {
        return columnMetadata;
    }
}
