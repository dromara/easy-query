package com.easy.query.api4kt.delete;

import com.easy.query.core.basic.api.delete.Deletable;
import com.easy.query.core.basic.api.internal.ConfigureVersionable;

/**
 * @author xuejiaming
 * @FileName: EasyEntityDeletable.java
 * @Description: 文件说明
 * @Date: 2023/3/6 13:07
 */
public interface KtEntityDeletable<T> extends Deletable<T, KtEntityDeletable<T>>, ConfigureVersionable<KtEntityDeletable<T>> {
}
