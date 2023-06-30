package com.easy.query.core.proxy;


import com.easy.query.core.expression.parser.core.available.TableAvailable;

import java.io.Serializable;

/**
 * create time 2023/6/21 16:41
 * 文件说明
 *
 * @author xuejiaming
 */
public interface TableProxy<TProxy extends TableProxy<TProxy, TEntity>, TEntity> extends BeanProxy, Serializable {

    default boolean isDefault() {
        return getTable() == null;
    }

    TableAvailable getTable();

    Class<TEntity> getEntityClass();

    TProxy create(TableAvailable table);

    default TEntity createEntity() {
        return null;
    }

}
