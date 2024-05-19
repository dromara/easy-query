package com.easy.query.api.proxy.entity.insert.extension;

import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.expression.builder.Configurer;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.sql.scec.SQLNativeProxyExpressionContext;
import com.easy.query.core.proxy.sql.scec.SQLNativeProxyExpressionContextImpl;

/**
  * create time 2024/5/19 09:34
  * 文件说明
  * @author xuejiaming
  */
public interface ProxyColumnConfigurer<TProxy extends ProxyEntity<TProxy, T>,T> {
    Configurer getConfigurer();

    default <TProperty> ProxyColumnConfigurer<TProxy,T> column(SQLColumn<TProxy,TProperty> property, String sqlSegment, SQLExpression2<SQLNativeProxyExpressionContext, SQLParameter> contextConsume){
        getConfigurer().column(property.getTable(),property.getValue(),sqlSegment,(context, sqlParameter)->{
            contextConsume.apply(new SQLNativeProxyExpressionContextImpl(context),sqlParameter);
        });
        return this;
    }
}
