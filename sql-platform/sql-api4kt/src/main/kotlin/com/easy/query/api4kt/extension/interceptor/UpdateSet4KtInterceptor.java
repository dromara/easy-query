package com.easy.query.api4kt.extension.interceptor;

import com.easy.query.api4kt.sql.SQLKtColumnSetter;
import com.easy.query.api4kt.sql.impl.SQLKtColumnSetterImpl;
import com.easy.query.core.basic.extension.interceptor.UpdateSetInterceptor;
import com.easy.query.core.expression.parser.core.base.ColumnSetter;
import com.easy.query.core.expression.sql.builder.EntityUpdateExpressionBuilder;

/**
 * create time 2023/6/2 17:16
 * 文件说明
 *
 * @author xuejiaming
 */
public interface UpdateSet4KtInterceptor extends UpdateSetInterceptor {
    @Override
    default void configure(Class<?> entityClass, EntityUpdateExpressionBuilder entityUpdateExpressionBuilder, ColumnSetter<Object> columnSetter) {
        configure(entityClass, entityUpdateExpressionBuilder, new SQLKtColumnSetterImpl<>(columnSetter));
    }

    void configure(Class<?> entityClass, EntityUpdateExpressionBuilder entityUpdateExpressionBuilder, SQLKtColumnSetter<Object> sqlColumnSetter);
}
