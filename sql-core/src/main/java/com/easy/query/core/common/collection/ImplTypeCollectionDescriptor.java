package com.easy.query.core.common.collection;

import com.easy.query.core.util.EasyClassUtil;

import java.util.Collection;

/**
 * create time 2025/7/8 13:32
 * 实现类集合
 *
 * @author xuejiaming
 */
public class ImplTypeCollectionDescriptor implements CollectionDescriptor {
    private final Class<? extends Collection> implType;

    public ImplTypeCollectionDescriptor(Class<? extends Collection> implType){
        this.implType = implType;
    }
    @Override
    public Class<? extends Collection> getCollectionType() {
        return implType;
    }

    @Override
    public Collection<?> newCollection() {
        return EasyClassUtil.newInstance(implType);
    }
}
