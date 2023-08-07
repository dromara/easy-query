package com.easy.query.api4j.sql.core;

import com.easy.query.api4j.sql.scec.SQLNativeLambdaExpressionContext;
import com.easy.query.api4j.sql.scec.SQLNativeLambdaExpressionContextImpl;
import com.easy.query.api4j.util.EasyLambdaUtil;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.base.core.SQLSetPropertyNative;

/**
 * create time 2023/8/5 17:24
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLSetLambdaNative<TEntity,TChain> {

   <T> SQLSetPropertyNative<T> getSQLSetPropertyNative();
   TChain castTChain();
   default TChain setSQLNative(Property<TEntity,?> property, String sqlSegment, SQLExpression1<SQLNativeLambdaExpressionContext<TEntity>> contextConsume){
       getSQLSetPropertyNative().setSQLSegment(EasyLambdaUtil.getPropertyName(property),sqlSegment,(context)->{
           contextConsume.apply(new SQLNativeLambdaExpressionContextImpl<>(context));
       });
       return castTChain();
   }
}
