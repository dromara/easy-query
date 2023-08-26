package com.easy.query.api4j.sql.scec;

import com.easy.query.api4j.select.Queryable;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;

/**
 * create time 2023/7/29 23:38
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLNativeLambdaExpressionContext<T1> {
    SQLNativeLambdaExpressionContext<T1> expression(Property<T1, ?> property);

    <TEntity> SQLNativeLambdaExpressionContext<T1> expression(Queryable<TEntity> subQuery);

    <T2> SQLNativeLambdaExpressionContext<T1> expression(EntitySQLTableOwner<T2> table, Property<T2, ?> property);

    SQLNativeLambdaExpressionContext<T1> value(Object val);

    /**
     * 请使用format
     *
     * @param constVal
     * @return
     */
    @Deprecated
    default SQLNativeLambdaExpressionContext<T1> constValue(Object constVal) {
        return format(constVal);
    }

    SQLNativeLambdaExpressionContext<T1> format(Object formatVal);

    SQLNativeLambdaExpressionContext<T1> setAlias(String alias);
}
