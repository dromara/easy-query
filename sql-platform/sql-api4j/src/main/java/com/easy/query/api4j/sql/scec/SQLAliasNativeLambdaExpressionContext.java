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
public interface SQLAliasNativeLambdaExpressionContext<T1,TR> extends SQLNativeLambdaExpressionContext<T1>{

    SQLAliasNativeLambdaExpressionContext<T1,TR> expressionAlias(Property<TR, ?> property);



    @Override
    SQLAliasNativeLambdaExpressionContext<T1,TR> expression(Property<T1, ?> property);

    @Override
    <T2> SQLAliasNativeLambdaExpressionContext<T1,TR> expression(EntitySQLTableOwner<T2> table, Property<T2, ?> property);


    @Override
    <TEntity> SQLAliasNativeLambdaExpressionContext<T1,TR> expression(Queryable<TEntity> subQuery);


    @Override
    SQLAliasNativeLambdaExpressionContext<T1,TR> value(Object val);

    /**
     * 请使用format
     *
     * @param constVal
     * @return
     */
    @Deprecated

    @Override
    default SQLAliasNativeLambdaExpressionContext<T1,TR> constValue(Object constVal) {
        return format(constVal);
    }


    @Override
    SQLAliasNativeLambdaExpressionContext<T1,TR> format(Object formatVal);

    @Override
    SQLAliasNativeLambdaExpressionContext<T1,TR> setAlias(String alias);
}
