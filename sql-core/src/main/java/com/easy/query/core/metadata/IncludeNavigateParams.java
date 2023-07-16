package com.easy.query.core.metadata;

import com.easy.query.core.util.EasyCollectionUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * create time 2023/7/15 20:07
 * 文件说明
 *
 * @author xuejiaming
 */
public class IncludeNavigateParams {
    private final List<Object> relationKeys;
    private NavigateMetadata navigateMetadata;
    public IncludeNavigateParams(){
        relationKeys=new ArrayList<>();
    }

    public List<Object> getRelationKeys() {
        return relationKeys;
    }

    public NavigateMetadata getNavigateMetadata() {
        return navigateMetadata;
    }

    public void setNavigateMetadata(NavigateMetadata navigateMetadata) {
        this.navigateMetadata = navigateMetadata;
    }
    public IncludeNavigateParams cloneParams(){
        IncludeNavigateParams includeNavigateParams = new IncludeNavigateParams();
        if(EasyCollectionUtil.isNotEmpty(relationKeys)){
            includeNavigateParams.getRelationKeys().addAll(relationKeys);
        }
        includeNavigateParams.setNavigateMetadata(navigateMetadata);
        return includeNavigateParams;
    }
}
