package com.easy.query.api.proxy.core.base;


import com.easy.query.core.expression.parser.core.available.TableAvailable;

import java.io.Serializable;

/**
 * create time 2023/6/21 16:41
 * 文件说明
 *
 * @author xuejiaming
 */
public interface TableProxy<TProxy extends TableProxy<TProxy, TEntity>, TEntity> extends Serializable {

    default boolean isDefault() {
        return getTable() == null;
    }

    TableAvailable getTable();

    Class<TEntity> getEntityClass();

    TProxy create(TableAvailable table);

}
