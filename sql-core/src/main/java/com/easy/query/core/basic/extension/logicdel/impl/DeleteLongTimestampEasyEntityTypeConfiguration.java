package com.easy.query.core.basic.extension.logicdel.impl;

import com.easy.query.core.basic.extension.logicdel.LogicDeleteBuilder;
import com.easy.query.core.basic.extension.logicdel.LogicDeleteStrategyEnum;
import com.easy.query.core.basic.extension.logicdel.abstraction.AbstractLogicDeleteStrategy;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.base.ColumnSetter;
import com.easy.query.core.expression.parser.core.base.WherePredicate;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author xuejiaming
 * @FileName: TimestampGlobalEntityTypeConfiguration.java
 * @Description: 文件说明
 * @Date: 2023/3/6 22:45
 */
public class DeleteLongTimestampEasyEntityTypeConfiguration extends AbstractLogicDeleteStrategy {
    private static final Set<Class<?>> allowedPropertyTypes = new HashSet<>(Arrays.asList(Long.class, long.class));

    @Override
    public String getStrategy() {
        return LogicDeleteStrategyEnum.DELETE_LONG_TIMESTAMP.getStrategy();
    }

    @Override
    public Set<Class<?>> allowedPropertyTypes() {
        return allowedPropertyTypes;
    }


    @Override
    protected SQLExpression1<WherePredicate<Object>> getPredicateFilterExpression(LogicDeleteBuilder builder, String propertyName) {
        return o -> o.eq(propertyName, 0);
    }

    @Override
    protected SQLExpression1<ColumnSetter<Object>> getDeletedSQLExpression(LogicDeleteBuilder builder, String propertyName) {


        //错误的用法因为now已经获取是个定值所以每次都是这个值应该在表达式内部如果表达式内部需要的操作复杂可以通过{}展开编写
//        long now = System.currentTimeMillis();
//        return o->o.set(lambdaProperty, now);
        //也可以展开这么写
//        return o->{
//            //some other code
//            long now = System.currentTimeMillis();//这边可以提取参数因为在lambda表达式内部所以会在需要时执行
//            o.set(lambdaProperty, now);
//        };
        return o -> o.set(propertyName, System.currentTimeMillis());
    }
}
