package com.easy.query.core.basic.extension.logicdel.abstraction;

import com.easy.query.core.basic.extension.logicdel.LogicDeleteMetadataBuilder;
import com.easy.query.core.basic.extension.logicdel.LogicDeleteBuilder;
import com.easy.query.core.basic.extension.logicdel.LogicDeleteStrategy;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.parser.core.base.ColumnSetter;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.metadata.LogicDeleteMetadata;
import org.jetbrains.annotations.NotNull;

/**
 * 逻辑删除抽象类
 */
public abstract class AbstractLogicDeleteStrategy implements LogicDeleteStrategy {

    @Override
    public LogicDeleteMetadata configureBuild(LogicDeleteMetadataBuilder builder) {
        boolean isPropLogicDeleteBuilder = builder instanceof LogicDeleteBuilder;
        if(!isPropLogicDeleteBuilder){
            throw new EasyQueryInvalidOperationException("not support logic delete builder:"+builder.getClass().getName());
        }
        LogicDeleteBuilder propLogicDeleteBuilder = (LogicDeleteBuilder) builder;
        String propertyName = propLogicDeleteBuilder.getPropertyName();
        SQLActionExpression1<WherePredicate<Object>> predicateFilterExpression = getPredicateFilterExpression(propLogicDeleteBuilder, propertyName);
        SQLActionExpression1<ColumnSetter<Object>> deletedSQLExpression = getDeletedSQLExpression(propLogicDeleteBuilder, propertyName);
        return builder.build(predicateFilterExpression, deletedSQLExpression);
    }

    protected abstract SQLActionExpression1<WherePredicate<Object>> getPredicateFilterExpression(LogicDeleteBuilder builder, String propertyName);

    /**
     *
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
     * 包括线程上下文的 ThreadLocal 也必须要在lambda内获取而不是提前获取
     * @param builder
     * @param propertyName
     * @return
     */
    protected abstract SQLActionExpression1<ColumnSetter<Object>> getDeletedSQLExpression(LogicDeleteBuilder builder, String propertyName);

    @Override
    public boolean apply(@NotNull Class<?> entityClass) {
        return false;
    }
}
