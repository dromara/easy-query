package com.easy.query.core.common.collection;

import java.util.Collection;
import java.util.HashSet;

/**
 * create time 2025/7/8 13:30
 * 文件说明
 *
 * @author xuejiaming
 */
public class HasSetCollectionDescriptor implements CollectionDescriptor {
    @Override
    public Class<? extends Collection> getCollectionType() {
        return HashSet.class;
    }

    @Override
    public Collection<?> newCollection() {
        return new HashSet<>();
    }
}
