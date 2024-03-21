package com.easy.query.core.basic.api.insert;

import com.easy.query.core.basic.api.internal.SQLConflictThenable;
import com.easy.query.core.basic.api.internal.SQLOnDuplicateKeyUpdate;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.base.ColumnConfigurer;

import java.util.Collection;

/**
 * create time 2023/6/2 15:55
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ClientInsertable<T> extends Insertable<T, ClientInsertable<T>>, SQLOnDuplicateKeyUpdate<T,ClientInsertable<T>>, SQLConflictThenable<T,ClientInsertable<T>> {
    @Override
    ClientInsertable<T> insert(T entity);
    ClientInsertable<T> columnConfigure(SQLExpression1<ColumnConfigurer<T>> columnConfigureExpression);
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
