package com.easy.query.api4j.delete;

import com.easy.query.api4j.sql.SQLWherePredicate;
import com.easy.query.core.basic.api.delete.Deletable;
import com.easy.query.core.basic.api.internal.Versionable;
import com.easy.query.core.expression.lambda.SQLExpression1;

import java.util.Collection;

/**
 * @FileName: EasyExpressionDelete.java
 * @Description: 文件说明
 * @Date: 2023/2/28 12:24
 * @author xuejiaming
 */
public interface ExpressionDeletable<T> extends Deletable<T, ExpressionDeletable<T>>, Versionable<ExpressionDeletable<T>> {
    default ExpressionDeletable<T> where(SQLExpression1<SQLWherePredicate<T>> whereExpression){
        return where(true,whereExpression);
    }
    ExpressionDeletable<T> where(boolean condition, SQLExpression1<SQLWherePredicate<T>> whereExpression);


   default Deletable<T, ExpressionDeletable<T>> whereById(Object id){
       return whereById(true,id);
   }
    Deletable<T, ExpressionDeletable<T>> whereById(boolean condition,Object id);
   default Deletable<T, ExpressionDeletable<T>> whereByIds(Object ...ids){
       return whereByIds(true,ids);
   }
    Deletable<T, ExpressionDeletable<T>> whereByIds(boolean condition,Object ...ids);
   default <TProperty> Deletable<T, ExpressionDeletable<T>> whereByIdCollection(Collection<TProperty> ids){
       return whereByIdCollection(true,ids);
   }
    <TProperty> Deletable<T, ExpressionDeletable<T>> whereByIdCollection(boolean condition, Collection<TProperty> ids);
}
