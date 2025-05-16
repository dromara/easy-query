package com.easy.query.core.proxy;

import com.easy.query.core.annotation.Nullable;
import com.easy.query.core.expression.parser.core.PropColumn;
import com.easy.query.core.expression.parser.core.available.TableAvailable;

/**
 * create time 2023/6/21 23:37
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyEntity<TProxy extends ProxyEntity<TProxy, TEntity>, TEntity>
        extends TableProxy<TProxy, TEntity>, PropColumn, ProxyNavValueAvailable {

    /**
     * 请使用getTable
     *
     * @return
     */
    @Nullable
    TableAvailable getTableOrNull();

    @Override
    default String getValue() {
        return getNavValue();
    }

}
