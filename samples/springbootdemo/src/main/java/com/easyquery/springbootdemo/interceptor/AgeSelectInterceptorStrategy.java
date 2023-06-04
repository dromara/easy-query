package com.easyquery.springbootdemo.interceptor;

import com.easy.query.core.basic.extension.interceptor.PredicateFilterInterceptor;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.sql.builder.LambdaEntityExpressionBuilder;
import com.easyquery.springbootdemo.domain.TestUserMysql0;
import org.springframework.stereotype.Component;

/**
 * @FileName: AgeSelectInterceptorStrategy.java
 * @Description: 文件说明
 * @Date: 2023/3/13 08:22
 * @author xuejiaming
 */
@Component
public class AgeSelectInterceptorStrategy implements PredicateFilterInterceptor {

    @Override
    public String name() {
        return "xxxxxx";
    }

    @Override
    public boolean apply(Class<?> entityClass) {
        return TestUserMysql0.class.isAssignableFrom(entityClass);
    }

    @Override
    public void configure(Class<?> entityClass, LambdaEntityExpressionBuilder lambdaEntityExpressionBuilder, WherePredicate<Object> wherePredicate) {
        wherePredicate.isNotNull("name");
    }
}
