package com.easyquery.springbootdemo.interceptor;

import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.abstraction.SqlPredicate;
import com.easy.query.core.interceptor.select.GlobalSelectInterceptorStrategy;
import com.easy.query.core.query.SqlEntityQueryExpression;
import com.easy.query.core.util.EasyUtil;
import org.springframework.stereotype.Component;

/**
 * @FileName: AgeSelectInterceptorStrategy.java
 * @Description: 文件说明
 * @Date: 2023/3/13 08:22
 * @Created by xuejiaming
 */
@Component
public class AgeSelectInterceptorStrategy implements GlobalSelectInterceptorStrategy {
    @Override
    public void configure(Class<?> entityClass, SqlEntityQueryExpression sqlEntityQueryExpression, SqlPredicate<?> sqlPredicate) {
        Property name = EasyUtil.getLambdaProperty(entityClass, "name", String.class);
        sqlPredicate.isNotNull(name);
    }

    @Override
    public String interceptorName() {
        return "xxxxxx";
    }

    @Override
    public boolean apply(Class<?> entityClass) {
        return true;
    }
}
