package com.easy.query.api4j.delete;

import com.easy.query.core.basic.api.delete.Deletable;
import com.easy.query.core.basic.api.internal.ConfigureVersionable;

import java.util.List;

/**
 * @author xuejiaming
 * @FileName: EasyEntityDeletable.java
 * @Description: 文件说明
 * @Date: 2023/3/6 13:07
 */
public interface EntityDeletable<T> extends Deletable<T, EntityDeletable<T>>, ConfigureVersionable<EntityDeletable<T>> {
    List<T> getEntities();
}
