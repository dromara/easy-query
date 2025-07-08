package com.easy.query.core.common.collection;

import java.util.Collection;

/**
 * create time 2025/7/8 13:30
 * 文件说明
 *
 * @author xuejiaming
 */
public interface CollectionDescriptor {
    Class<? extends Collection> getCollectionType();
    Collection<?> newCollection();
}
