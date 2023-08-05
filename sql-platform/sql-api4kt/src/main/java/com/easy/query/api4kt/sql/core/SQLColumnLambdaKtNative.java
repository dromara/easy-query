package com.easy.query.api4kt.sql.core;

import com.easy.query.api4kt.sql.scec.SQLNativeLambdaKtExpressionContext;
import com.easy.query.api4kt.sql.scec.SQLNativeLambdaKtExpressionContextImpl;
import com.easy.query.api4kt.util.EasyKtLambdaUtil;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.parser.core.base.core.SQLColumnPropertyNative;
import kotlin.reflect.KProperty1;

/**
 * create time 2023/8/5 23:46
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLColumnLambdaKtNative<TEntity,TChain> {
    <T> SQLColumnPropertyNative<T> getSQLColumnPropertyNative();
    TChain castTChain();
    default TChain columnSQL(KProperty1<TEntity,?> property, String sqlSegment, SQLExpression2<SQLNativeLambdaKtExpressionContext<TEntity>, SQLParameter> contextConsume){
        getSQLColumnPropertyNative()
                .columnSQL(EasyKtLambdaUtil.getPropertyName(property),sqlSegment,(context, params)->{
                    contextConsume.apply(new SQLNativeLambdaKtExpressionContextImpl<>(context),params);
                });
        return castTChain();
    }
}
