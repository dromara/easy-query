package com.easy.query.api.proxy.delete;

import com.easy.query.core.basic.api.delete.Deletable;
import com.easy.query.core.basic.api.internal.ConfigureVersionable;

/**
 * @author xuejiaming
 * @FileName: EasyEntityDeletable.java
 * @Description: 文件说明
 * @Date: 2023/3/6 13:07
 */
public interface ProxyEntityDeletable<T> extends Deletable<T, ProxyEntityDeletable<T>>, ConfigureVersionable<ProxyEntityDeletable<T>> {
}
