package com.easy.query.api4kt.sql;

import com.easy.query.api4kt.sql.scec.SQLNativeLambdaKtExpressionContext;
import com.easy.query.api4kt.sql.scec.SQLNativeLambdaKtExpressionContextImpl;
import com.easy.query.api4kt.util.EasyKtLambdaUtil;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.parser.core.base.ColumnConfigurer;
import kotlin.reflect.KProperty1;

/**
 * create time 2023/8/7 08:56
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtColumnConfigurer<T> {
    ColumnConfigurer<T> getColumnConfigurer();

    default <TProperty> SQLKtColumnConfigurer<T> column(KProperty1<? super T,TProperty> property, String sqlSegment, SQLExpression2<SQLNativeLambdaKtExpressionContext<T>, SQLParameter> contextConsume){
        getColumnConfigurer().column(EasyKtLambdaUtil.getPropertyName(property),sqlSegment,(context, sqlParameter)->{
            contextConsume.apply(new SQLNativeLambdaKtExpressionContextImpl<>(context),sqlParameter);
        });
        return this;
    }
}
