package com.easyquery.springbootdemo.interceptor;

import com.easy.query.core.common.bean.FastBean;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.basic.plugin.interceptor.PredicateFilterInterceptor;
import com.easy.query.core.expression.parser.core.SQLWherePredicate;
import com.easy.query.core.expression.sql.builder.LambdaEntityExpressionBuilder;
import com.easy.query.core.util.EasyBeanUtil;
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
        return true;
    }

    @Override
    public void configure(Class<?> entityClass, LambdaEntityExpressionBuilder lambdaEntityExpressionBuilder, SQLWherePredicate<Object> sqlPredicate) {
        FastBean fastBean = EasyBeanUtil.getFastBean(entityClass);
        Property<Object,?> name = fastBean.getBeanGetter("name", String.class);
        sqlPredicate.isNotNull(name);
    }
}
