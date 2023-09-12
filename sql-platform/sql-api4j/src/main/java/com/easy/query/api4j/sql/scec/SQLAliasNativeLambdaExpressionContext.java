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
public interface SQLAliasNativeLambdaExpressionContext<T1> extends SQLNativeLambdaExpressionContext<T1>{

    <TR> SQLAliasNativeLambdaExpressionContext<T1> expressionAlias(Property<TR, ?> property);



    @Override
    SQLAliasNativeLambdaExpressionContext<T1> expression(Property<T1, ?> property);

    @Override
    <T2> SQLAliasNativeLambdaExpressionContext<T1> expression(EntitySQLTableOwner<T2> table, Property<T2, ?> property);


    @Override
    <TEntity> SQLAliasNativeLambdaExpressionContext<T1> expression(Queryable<TEntity> subQuery);


    @Override
    SQLAliasNativeLambdaExpressionContext<T1> value(Object val);

    /**
     * 请使用format
     *
     * @param constVal
     * @return
     */
    @Deprecated

    @Override
    default SQLAliasNativeLambdaExpressionContext<T1> constValue(Object constVal) {
        return format(constVal);
    }


    @Override
    SQLAliasNativeLambdaExpressionContext<T1> format(Object formatVal);

    @Override
    SQLAliasNativeLambdaExpressionContext<T1> setAlias(String alias);
}
