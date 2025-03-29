package com.easy.query.core.expression.include.getter;

import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.util.EasyClassUtil;
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
    protected Class<?> collectionType;

    public AbstractIncludeGetter(NavigateMetadata navigateMetadata){
        this.navigateMetadata = navigateMetadata;
    }
    protected Class<?> getCollectionType() {
        if (collectionType == null) {
            collectionType = EasyClassUtil.getCollectionImplType(navigateMetadata.getNavigateOriginalPropertyType());
        }
        return collectionType;
    }

    protected <TNavigateEntity> Collection<TNavigateEntity> createManyCollection() {
        Class<?> collectionType = getCollectionType();
        return EasyObjectUtil.typeCastNullable(EasyClassUtil.newInstance(collectionType));
    }
}
