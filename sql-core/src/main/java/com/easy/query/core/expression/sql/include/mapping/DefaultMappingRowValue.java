package com.easy.query.core.expression.sql.include.mapping;

import com.easy.query.core.expression.sql.include.RelationValue;
import com.easy.query.core.metadata.NavigateMetadata;

/**
 * create time 2025/3/1 08:31
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultMappingRowValue implements MappingRowValue{
    private final NavigateMetadata navigateMetadata;
    private final Object entity;

    public DefaultMappingRowValue(NavigateMetadata navigateMetadata,Object entity){
        this.navigateMetadata = navigateMetadata;
        this.entity = entity;
    }
    @Override
    public RelationValue getTargetValue(String selfProp) {
        return null;
    }
}
