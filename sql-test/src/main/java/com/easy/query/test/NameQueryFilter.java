package com.easy.query.test;

import com.easy.query.core.basic.plugin.interceptor.EasyPredicateFilterInterceptor;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.core.SqlWherePredicate;
import com.easy.query.core.expression.sql.builder.LambdaEntityExpressionBuilder;
import com.easy.query.core.util.BeanUtil;
import com.easy.query.core.util.EasyUtil;

/**
 * @FileName: NameQueryFilter.java
 * @Description: 文件说明
 * @Date: 2023/3/8 10:24
 * @author xuejiaming
 */
public class NameQueryFilter implements EasyPredicateFilterInterceptor {
    @Override
    public void configure(Class<?> entityClass, LambdaEntityExpressionBuilder lambdaEntityExpressionBuilder, SqlWherePredicate<Object> sqlWherePredicate) {
        Property<Object,?> property = BeanUtil.getFastBean(entityClass).getBeanGetter("name", String.class);
        sqlWherePredicate.isNull(property);
    }

    @Override
    public String name() {
        return "nameQueryFilter";
    }

    @Override
    public boolean apply(Class<?> entityClass) {
        return TestUserMysql.class.equals(entityClass);
    }
}
