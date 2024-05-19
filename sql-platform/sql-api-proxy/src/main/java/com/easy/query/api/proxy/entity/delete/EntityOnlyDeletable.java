package com.easy.query.api.proxy.entity.delete;

import com.easy.query.core.basic.api.delete.Deletable;
import com.easy.query.core.basic.api.internal.ConfigureVersionable;
import com.easy.query.core.basic.api.internal.SQLBatchExecute;

/**
 * create time 2024/5/19 09:14
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityOnlyDeletable<T>  extends Deletable<T, EntityOnlyDeletable<T>>, ConfigureVersionable<EntityOnlyDeletable<T>>, SQLBatchExecute<EntityOnlyDeletable<T>> {
}
