package com.easy.query.core.common.collection;

import java.util.ArrayList;
import java.util.Collection;

/**
 * create time 2025/7/8 13:30
 * 文件说明
 *
 * @author xuejiaming
 */
public class ArrayListCollectionDescriptor implements CollectionDescriptor {
    @Override
    public Class<? extends Collection> getCollectionType() {
        return ArrayList.class;
    }

    @Override
    public Collection<?> newCollection() {
        return new ArrayList<>();
    }
}
