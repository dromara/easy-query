package com.easy.query.test;

import com.easy.query.core.interceptor.select.GlobalSelectInterceptorStrategy;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.abstraction.SqlPredicate;
import com.easy.query.core.query.SqlEntityQueryExpression;
import com.easy.query.core.util.EasyUtil;

/**
 * @FileName: NameQueryFilter.java
 * @Description: 文件说明
 * @Date: 2023/3/8 10:24
 * @Created by xuejiaming
 */
public class NameQueryFilter implements GlobalSelectInterceptorStrategy {
    @Override
    public void configure(Class<?> entityClass, SqlEntityQueryExpression sqlEntityQueryExpression, SqlPredicate<?> sqlPredicate) {
        Property property = EasyUtil.getLambdaProperty(entityClass, "name", String.class);
        sqlPredicate.isNull(property);
    }

    @Override
    public String interceptorName() {
        return "nameQueryFilter";
    }

    @Override
    public boolean apply(Class<?> entityClass) {
        return TestUserMysql.class.equals(entityClass);
    }
}
