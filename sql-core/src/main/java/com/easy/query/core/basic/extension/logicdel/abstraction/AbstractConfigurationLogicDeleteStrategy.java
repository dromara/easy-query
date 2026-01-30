package com.easy.query.core.basic.extension.logicdel.abstraction;

import com.easy.query.core.basic.extension.logicdel.LogicDeleteMetadataBuilder;
import com.easy.query.core.basic.extension.logicdel.LogicDeleteStrategy;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.parser.core.base.ColumnSetter;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.metadata.LogicDeleteMetadata;
import org.jetbrains.annotations.NotNull;

/**
 * 逻辑删除抽象类
 */
public abstract class AbstractConfigurationLogicDeleteStrategy implements LogicDeleteStrategy {

    @Override
    public LogicDeleteMetadata configureBuild(LogicDeleteMetadataBuilder builder) {
        SQLActionExpression1<WherePredicate<Object>> predicateFilterExpression = getPredicateFilterExpression();
        SQLActionExpression1<ColumnSetter<Object>> deletedSQLExpression = getDeletedSQLExpression();
        return builder.build(predicateFilterExpression, deletedSQLExpression);
    }

    protected abstract SQLActionExpression1<WherePredicate<Object>> getPredicateFilterExpression();

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
     * @return
     */
    protected abstract SQLActionExpression1<ColumnSetter<Object>> getDeletedSQLExpression();

    @Override
    public abstract boolean apply(@NotNull Class<?> entityClass);
}
