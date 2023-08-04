package com.easy.query.core.basic.api.insert;

import com.easy.query.core.basic.api.internal.SQLOnDuplicateKeyUpdate;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.parser.core.base.scec.SQLNativePropertyExpressionContext;

import java.util.Collection;

/**
 * create time 2023/6/2 15:55
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ClientInsertable<T> extends Insertable<T, ClientInsertable<T>>, SQLOnDuplicateKeyUpdate<T,ClientInsertable<T>> {
    @Override
    ClientInsertable<T> insert(T entity);
    ClientInsertable<T> columnSQLNative(String property, String sqlSegment, SQLExpression2<SQLNativePropertyExpressionContext, SQLParameter> contextConsume);

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
