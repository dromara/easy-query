package com.easy.query.core.expression.sql.builder.internal;

import com.easy.query.core.expression.sql.builder.ExpressionContext;

/**
 * create time 2024/7/4 08:40
 * 文上下文配置
 *
 * @author xuejiaming
 */
public class ContextConfigurerImpl implements ContextConfigurer{
    private final ExpressionContext expressionContext;

    public ContextConfigurerImpl(ExpressionContext expressionContext){

        this.expressionContext = expressionContext;
    }
    @Override
    public EasyBehavior getBehavior() {
        return expressionContext.getBehavior();
    }

    @Override
    public void setGroupSize(Integer groupSize) {
        expressionContext.setGroupSize(groupSize);
    }

    @Override
    public Integer getGroupSize() {
        return expressionContext.getGroupSize();
    }
}
