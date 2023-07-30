package com.easy.query.api.proxy.sql;

import com.easy.query.api.proxy.sql.scec.SQLNativeProxyExpressionContext;
import com.easy.query.api.proxy.sql.scec.SQLNativeProxyExpressionContextImpl;
import com.easy.query.core.expression.builder.OrderSelector;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.proxy.SQLColumn;

/**
 * create time 2023/6/23 15:01
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyOrderSelector {
    OrderSelector getOrderSelector();


    default ProxyOrderSelector columns(SQLColumn<?>... columns) {
        if (columns != null) {
            for (SQLColumn<?> sqlColumn : columns) {
                column(sqlColumn);
            }
        }
        return this;
    }


   default ProxyOrderSelector column(SQLColumn<?> column){
       getOrderSelector().column(column.getTable(), column.value());
       return this;
   }

    default ProxyOrderSelector sqlNativeSegment(String sqlSegment){
        return sqlNativeSegment(sqlSegment,c->{});
    }
    default ProxyOrderSelector sqlNativeSegment(String sqlSegment, SQLExpression1<SQLNativeProxyExpressionContext> contextConsume){
        getOrderSelector().sqlNativeSegment(sqlSegment, context->{
            contextConsume.apply(new SQLNativeProxyExpressionContextImpl(context));
        });
        return this;
    }

   default ProxyOrderSelector columnFunc(ProxyColumnPropertyFunction proxyColumnPropertyFunction){
       getOrderSelector().columnFunc(proxyColumnPropertyFunction.getColumn().getTable(), proxyColumnPropertyFunction.getColumnPropertyFunction());
       return this;
   }
}
