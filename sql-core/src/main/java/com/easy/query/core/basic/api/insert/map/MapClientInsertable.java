package com.easy.query.core.basic.api.insert.map;

import com.easy.query.core.basic.api.insert.ClientInsertable;
import com.easy.query.core.basic.api.insert.Insertable;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.base.ColumnConfigurer;

import java.util.Collection;

/**
 * create time 2023/6/2 15:55
 * 文件说明
 *
 * @author xuejiaming
 */
public interface MapClientInsertable<T> extends Insertable<T, MapClientInsertable<T>> {
    @Override
    MapClientInsertable<T> insert(T entity);
    @Override
    default MapClientInsertable<T> insert(Collection<T> entities) {
        if (entities == null) {
            return this;
        }
        for (T entity : entities) {
            insert(entity);
        }
        return this;
    }
    MapClientInsertable<T> columnConfigure(SQLExpression1<ColumnConfigurer<T>> columnConfigureExpression);
}
