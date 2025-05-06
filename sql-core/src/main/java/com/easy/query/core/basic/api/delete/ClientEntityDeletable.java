package com.easy.query.core.basic.api.delete;

import com.easy.query.core.basic.api.internal.ConfigureVersionable;
import com.easy.query.core.basic.api.internal.SQLBatchExecute;

import java.util.List;

/**
 * @author xuejiaming
 * @FileName: EasyEntityDeletable.java
 * @Description: 文件说明
 * @Date: 2023/3/6 13:07
 */
public interface ClientEntityDeletable<T> extends Deletable<T, ClientEntityDeletable<T>>
        , ConfigureVersionable<ClientEntityDeletable<T>>, SQLBatchExecute<ClientEntityDeletable<T>> {
    List<T> getEntities();
}
