package com.easy.query.core.expression.parser.core.base.core;

import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.expression.builder.core.SQLColumnNative;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.parser.core.SQLTableOwner;
import com.easy.query.core.expression.parser.core.base.scec.SQLNativePropertyExpressionContext;
import com.easy.query.core.expression.parser.core.base.scec.SQLNativePropertyExpressionContextImpl;

/**
 * create time 2023/8/5 23:38
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLColumnPropertyNative<TChain> extends SQLTableOwner {
   <T> SQLColumnNative<T> getSQLColumnNative();
   TChain castTChain();
   default TChain columnSQL(String property, String sqlSegment, SQLExpression2<SQLNativePropertyExpressionContext, SQLParameter> contextConsume){
       getSQLColumnNative()
               .columnSQL(getTable(),property,sqlSegment,(context,params)->{
                   contextConsume.apply(new SQLNativePropertyExpressionContextImpl(getTable(),context),params);
               });
       return castTChain();
   }
}
