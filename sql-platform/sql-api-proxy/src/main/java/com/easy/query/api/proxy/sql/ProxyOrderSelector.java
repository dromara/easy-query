package com.easy.query.api.proxy.sql;

import com.easy.query.api.proxy.sql.scec.ProxyConstExpressionContext;
import com.easy.query.api.proxy.sql.scec.ProxyConstExpressionContextImpl;
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

    default ProxyOrderSelector columnConst(String columnConst){
        return columnConst(columnConst,c->{});
    }
    default ProxyOrderSelector columnConst(String columnConst, SQLExpression1<ProxyConstExpressionContext> contextConsume){
        getOrderSelector().columnConst(columnConst,context->{
            contextConsume.apply(new ProxyConstExpressionContextImpl(context));
        });
        return this;
    }

   default ProxyOrderSelector columnFunc(ProxyColumnPropertyFunction proxyColumnPropertyFunction){
       getOrderSelector().columnFunc(proxyColumnPropertyFunction.getColumn().getTable(), proxyColumnPropertyFunction.getColumnPropertyFunction());
       return this;
   }
}
