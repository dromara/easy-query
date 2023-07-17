package com.easy.query.core.metadata;

import com.easy.query.core.basic.api.select.ClientQueryable;

import java.util.ArrayList;
import java.util.List;

/**
 * create time 2023/7/15 20:07
 * 文件说明
 *
 * @author xuejiaming
 */
public class IncludeNavigateParams {
    private final List<Object> relationIds;
    private NavigateMetadata navigateMetadata;
    private ClientQueryable<?> mappingQueryable;
    public IncludeNavigateParams(){
        relationIds =new ArrayList<>();
    }

    public List<Object> getRelationIds() {
        return relationIds;
    }

    public NavigateMetadata getNavigateMetadata() {
        return navigateMetadata;
    }

    public void setNavigateMetadata(NavigateMetadata navigateMetadata) {
        this.navigateMetadata = navigateMetadata;
    }

    public ClientQueryable<?> getMappingQueryable() {
        return mappingQueryable;
    }

    public void setMappingQueryable(ClientQueryable<?> mappingQueryable) {
        this.mappingQueryable = mappingQueryable;
    }
}
