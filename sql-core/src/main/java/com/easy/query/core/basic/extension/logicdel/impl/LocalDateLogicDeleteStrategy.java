package com.easy.query.core.basic.extension.logicdel.impl;

import com.easy.query.core.basic.extension.logicdel.LogicDeleteBuilder;
import com.easy.query.core.basic.extension.logicdel.LogicDeleteStrategyEnum;
import com.easy.query.core.basic.extension.logicdel.abstraction.AbstractLogicDeleteStrategy;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.parser.core.base.ColumnSetter;
import com.easy.query.core.expression.parser.core.base.WherePredicate;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * create time 2023/3/6 22:45
 * 日期删除策略
 *
 * @author xuejiaming
 */
public  class LocalDateLogicDeleteStrategy extends AbstractLogicDeleteStrategy {
    private static final Set<Class<?>> allowedPropertyTypes =new HashSet<>(Collections.singletonList(LocalDate.class));
    @Override
    public String getStrategy() {
        return LogicDeleteStrategyEnum.LOCAL_DATE.getStrategy();
    }

    @Override
    public Set<Class<?>> allowedPropertyTypes() {
        return allowedPropertyTypes;
    }


    @Override
    protected SQLActionExpression1<WherePredicate<Object>> getPredicateFilterExpression(LogicDeleteBuilder builder, String propertyName) {
        return o -> o.isNull(propertyName);
    }

    @Override
    protected SQLActionExpression1<ColumnSetter<Object>> getDeletedSQLExpression(LogicDeleteBuilder builder, String propertyName) {
        //错误的用法因为now已经获取是个定值所以每次都是这个值应该在表达式内部如果表达式内部需要的操作复杂可以通过{}展开编写
//        long now = LocalDate.now();
//        return o->o.set(lambdaProperty, now);
        //也可以展开这么写
//        return o->{
//            //some other code
//            long now = LocalDate.now();//这边可以提取参数因为在lambda表达式内部所以会在需要时执行
//            o.set(lambdaProperty, now);
//        };
        return o -> o.set(propertyName, LocalDate.now());
    }
}
