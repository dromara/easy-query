package com.easy.query.api4j.sql.core;

import com.easy.query.api4j.sql.scec.SQLNativeLambdaExpressionContext;
import com.easy.query.api4j.sql.scec.SQLNativeLambdaExpressionContextImpl;
import com.easy.query.api4j.util.EasyLambdaUtil;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.parser.core.base.core.SQLColumnPropertyNative;

/**
 * create time 2023/8/5 23:42
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLColumnLambdaNative<TEntity,TChain> {
    <T> SQLColumnPropertyNative<T> getSQLColumnPropertyNative();
    TChain castTChain();
    default TChain columnSQL(Property<TEntity,?> property, String sqlSegment, SQLExpression2<SQLNativeLambdaExpressionContext<TEntity>, SQLParameter> contextConsume){
        getSQLColumnPropertyNative()
                .columnSQL(EasyLambdaUtil.getPropertyName(property),sqlSegment,(context, params)->{
                    contextConsume.apply(new SQLNativeLambdaExpressionContextImpl<>(context),params);
                });
        return castTChain();
    }
}
