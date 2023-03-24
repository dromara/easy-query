package com.easyquery.springbootdemo.interceptor;

import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.abstraction.SqlPredicate;
import com.easy.query.core.interceptor.GlobalPredicateFilterInterceptor;
import com.easy.query.core.query.SqlEntityExpression;
import com.easy.query.core.query.SqlEntityQueryExpression;
import com.easy.query.core.util.EasyUtil;
import org.springframework.stereotype.Component;

/**
 * @FileName: AgeSelectInterceptorStrategy.java
 * @Description: 文件说明
 * @Date: 2023/3/13 08:22
 * @author xuejiaming
 */
@Component
public class AgeSelectInterceptorStrategy implements GlobalPredicateFilterInterceptor {

    @Override
    public String name() {
        return "xxxxxx";
    }

    @Override
    public boolean apply(Class<?> entityClass) {
        return true;
    }

    @Override
    public void configure(Class<?> entityClass, SqlEntityExpression sqlEntityExpression, SqlPredicate<Object> sqlPredicate) {
        Property<Object,?> name = EasyUtil.getPropertyLambda(entityClass, "name", String.class);
        sqlPredicate.isNotNull(name);
    }
}
