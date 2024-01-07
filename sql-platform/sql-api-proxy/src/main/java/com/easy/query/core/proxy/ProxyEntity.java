package com.easy.query.core.proxy;

import java.util.function.Consumer;

/**
 * create time 2023/6/21 23:37
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyEntity<TProxy extends ProxyEntity<TProxy, TEntity>, TEntity>
        extends TableProxy<TProxy, TEntity> {

}
