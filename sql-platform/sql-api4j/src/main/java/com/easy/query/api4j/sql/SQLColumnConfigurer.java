package com.easy.query.api4j.sql;

import com.easy.query.api4j.sql.scec.SQLNativeLambdaExpressionContext;
import com.easy.query.api4j.sql.scec.SQLNativeLambdaExpressionContextImpl;
import com.easy.query.api4j.util.EasyLambdaUtil;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.parser.core.base.ColumnConfigurer;

/**
 * create time 2023/8/7 08:56
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLColumnConfigurer<T> {
    ColumnConfigurer<T> getColumnConfigurer();

    default <TProperty> SQLColumnConfigurer<T> column(Property<T,TProperty> property, String sqlSegment, SQLExpression2<SQLNativeLambdaExpressionContext<T>, SQLParameter> contextConsume){
        getColumnConfigurer().column(EasyLambdaUtil.getPropertyName(property),sqlSegment,(context, sqlParameter)->{
            contextConsume.apply(new SQLNativeLambdaExpressionContextImpl<>(context),sqlParameter);
        });
        return this;
    }
}
