package com.easy.query.core.metadata;

/**
 * create time 2024/5/15 08:16
 * 文件说明
 *
 * @author xuejiaming
 */
public class MappingPathNode {
    private final String mappingPath;
    private final boolean isBasicType;

    public MappingPathNode(String mappingPath, boolean isBasicType){

        this.mappingPath = mappingPath;
        this.isBasicType = isBasicType;
    }

    public String getMappingPath() {
        return mappingPath;
    }

    public boolean isBasicType() {
        return isBasicType;
    }
}
