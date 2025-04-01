package com.easy.query.core.expression.include.getter;

import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyObjectUtil;

import java.util.Collection;

/**
 * create time 2025/3/29 22:05
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractIncludeGetter {
    protected final NavigateMetadata navigateMetadata;

    public AbstractIncludeGetter(NavigateMetadata navigateMetadata){
        this.navigateMetadata = navigateMetadata;
    }

    protected <TNavigateEntity> Collection<TNavigateEntity> createManyCollection() {
        return EasyCollectionUtil.createManyCollection(navigateMetadata);
    }
}
