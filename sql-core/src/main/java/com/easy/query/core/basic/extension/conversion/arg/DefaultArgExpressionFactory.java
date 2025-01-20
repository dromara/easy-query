package com.easy.query.core.basic.extension.conversion.arg;

import java.util.HashMap;
import java.util.Map;

/**
 * create time 2025/1/20 23:33
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultArgExpressionFactory implements ArgExpressionFactory{
    private static final Map<Class<? extends ArgExpression>,ArgExpression> ARG_EXPRESSION_MAP=new HashMap<>();
    static {
        ARG_EXPRESSION_MAP.put(StringArgExpression.class,new StringArgExpression());
        ARG_EXPRESSION_MAP.put(IntegerArgExpression.class,new IntegerArgExpression());
    }
    @Override
    public ArgExpression create(Class<? extends ArgExpression> argExpressionClass) {
        return null;
    }
}
