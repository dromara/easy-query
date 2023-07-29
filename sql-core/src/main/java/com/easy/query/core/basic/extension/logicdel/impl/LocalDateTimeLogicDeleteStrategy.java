package com.easy.query.core.basic.extension.logicdel.impl;

import com.easy.query.core.basic.extension.logicdel.LogicDeleteBuilder;
import com.easy.query.core.basic.extension.logicdel.LogicDeleteStrategyEnum;
import com.easy.query.core.basic.extension.logicdel.abstraction.AbstractLogicDeleteStrategy;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.base.ColumnSetter;
import com.easy.query.core.expression.parser.core.base.WherePredicate;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @FileName: LocalDateTimeGlobalEntityTypeConfiguration.java
 * @Description: 文件说明
 * @Date: 2023/3/6 22:45
 * @author xuejiaming
 */
public class LocalDateTimeLogicDeleteStrategy extends AbstractLogicDeleteStrategy {
    private static final Set<Class<?>> allowedPropertyTypes =new HashSet<>(Collections.singletonList(LocalDateTime.class));
    @Override
    public String getStrategy() {
        return LogicDeleteStrategyEnum.LOCAL_DATE_TIME.getStrategy();
    }

    @Override
    public Set<Class<?>> allowedPropertyTypes() {
        return allowedPropertyTypes;
    }

    @Override
    protected SQLExpression1<WherePredicate<Object>> getPredicateFilterExpression(LogicDeleteBuilder builder, String propertyName) {
        return o -> o.isNull(propertyName);
    }

    /**
     * 错误的用法
     * <pre>{@code
     *     long now = LocalDateTime.now();
     *     return o->o.set(lambdaProperty, now);
     * }</pre>
     * 正确的用法
     * <pre>{@code
     *     return o->o.set(lambdaProperty, LocalDateTime.now());
     * }</pre>
     *
     * 因为局部变量如果是被先提取那么他就是一个确认值,因为lambda具有延迟执行的特性所以必须要在执行时获取对应的值
     * @param builder
     * @param propertyName
     * @return
     */
    @Override
    protected SQLExpression1<ColumnSetter<Object>> getDeletedSQLExpression(LogicDeleteBuilder builder, String propertyName) {

        //错误的用法因为now已经获取是个定值所以每次都是这个值应该在表达式内部如果表达式内部需要的操作复杂可以通过{}展开编写
//        long now = LocalDateTime.now();
//        return o->o.set(lambdaProperty, now);
        //也可以展开这么写
//        return o->{
//            //some other code
//            long now = LocalDateTime.now();//这边可以提取参数因为在lambda表达式内部所以会在需要时执行
//            o.set(lambdaProperty, now);
//        };
        return o -> o.set(propertyName, LocalDateTime.now());
    }
}
