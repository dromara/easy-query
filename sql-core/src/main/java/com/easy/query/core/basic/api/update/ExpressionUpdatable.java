package com.easy.query.core.basic.api.update;

import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.SqlExpression;
import com.easy.query.core.expression.parser.abstraction.SqlColumnSetter;
import com.easy.query.core.expression.parser.abstraction.SqlPredicate;
import com.easy.query.core.expression.parser.abstraction.internal.ColumnSetter;

/**
 * @FileName: ExpressionUpdatable.java
 * @Description: 文件说明
 * @Date: 2023/2/24 23:21
 * @Created by xuejiaming
 */
public interface ExpressionUpdatable<T> extends Updatable<T> {
   default ExpressionUpdatable<T> set(Property<T, ?> column, Object val){
       return set(true,column,val);
   }
    ExpressionUpdatable<T> set(boolean condition, Property<T, ?> column, Object val);

    default  ExpressionUpdatable<T> set( Property<T, ?> column1, Property<T, ?> column2) {
        return set(true, column1, column2);
    }

    ExpressionUpdatable<T> set(boolean condition,  Property<T, ?> column1, Property<T, ?> column2);

    
    // region 列++ --


    default  ExpressionUpdatable<T> setIncrement(Property<T, Integer> column) {
        return setIncrement(true, column);
    }

    default ExpressionUpdatable<T> setIncrement(boolean condition,Property<T, Integer> column){
        return setIncrementNumber(condition,column,1);
    }

    default  ExpressionUpdatable<T> setIncrement(Property<T, Integer> column,int val) {
        return setIncrementNumber(true, column,val);
    }

    default ExpressionUpdatable<T> setIncrement(boolean condition,Property<T, Integer> column,int val){
        return setIncrementNumber(condition,column,val);
    }

    default  ExpressionUpdatable<T> setIncrement(Property<T, Long> column,long val) {
        return setIncrementNumber(true, column,val);
    }

    default ExpressionUpdatable<T> setIncrement(boolean condition,Property<T, Long> column,long val){
        return setIncrementNumber(condition,column,val);
    }


    default  ExpressionUpdatable<T> setIncrement(Property<T, ? extends Number> column,Number val) {
        return setIncrementNumber(true, column,val);
    }

    ExpressionUpdatable<T> setIncrementNumber(boolean condition,Property<T, ? extends Number> column,Number val);

    default  ExpressionUpdatable<T> setDecrement(Property<T, Integer> column) {
        return setDecrement(true, column);
    }

    default ExpressionUpdatable<T> setDecrement(boolean condition,Property<T, Integer> column){
        return setDecrementNumber(condition,column,1);
    }

    default  ExpressionUpdatable<T> setDecrement(Property<T, Integer> column,int val) {
        return setDecrementNumber(true, column,val);
    }

    default ExpressionUpdatable<T> setDecrement(boolean condition,Property<T, Integer> column,int val){
        return setDecrementNumber(condition,column,val);
    }

    default  ExpressionUpdatable<T> setDecrement(Property<T, Long> column,long val) {
        return setDecrementNumber(true, column,val);
    }

    default ExpressionUpdatable<T> setDecrement(boolean condition,Property<T, Long> column,long val){
        return setDecrementNumber(condition,column,val);
    }


    default  ExpressionUpdatable<T> setDecrement(Property<T, ? extends Number> column,Number val) {
        return setDecrementNumber(true, column,val);
    }

    ExpressionUpdatable<T> setDecrementNumber(boolean condition,Property<T, ? extends Number> column,Number val);
    // endregion
    
   default ExpressionUpdatable<T> where(SqlExpression<SqlPredicate<T>> whereExpression){
       return where(true,whereExpression);
   }
    ExpressionUpdatable<T> where(boolean condition,SqlExpression<SqlPredicate<T>> whereExpression);
}
