package com.easy.query.core.metadata;

/**
 * create time 2023/6/17 19:13
 * 文件说明
 *
 * @author xuejiaming
 */
public class NavigateJoinMetadata {

    private final EntityMetadata entityMetadata;
    private final String[] mappingPath;
    private final String property;

    public NavigateJoinMetadata(EntityMetadata entityMetadata,
                                String[] mappingPath,String property) {
        this.entityMetadata = entityMetadata;
        this.mappingPath = mappingPath;
        this.property = property;
    }

    public EntityMetadata getEntityMetadata() {
        return entityMetadata;
    }

    public String[] getMappingPath() {
        return mappingPath;
    }

    public String getProperty() {
        return property;
    }
}
