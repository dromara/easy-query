package com.easy.query.core.basic.api.insert;

import com.easy.query.core.basic.api.internal.SQLConflictThenable;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.parser.core.base.ColumnConfigurer;

import java.util.Collection;
import java.util.List;

/**
 * create time 2023/6/2 15:55
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ClientInsertable<T> extends Insertable<T, ClientInsertable<T>>,  SQLConflictThenable<T,ClientInsertable<T>> {
    List<T> getEntities();
    @Override
    ClientInsertable<T> insert(T entity);
    ClientInsertable<T> columnConfigure(SQLActionExpression1<ColumnConfigurer<T>> columnConfigureExpression);
    @Override
    default ClientInsertable<T> insert(Collection<T> entities) {
        if (entities == null) {
            return this;
        }
        for (T entity : entities) {
            insert(entity);
        }
        return this;
    }
}
