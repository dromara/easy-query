package com.easy.query.test.mytest;

import com.easy.query.core.basic.plugin.interceptor.PredicateFilterInterceptor;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.sql.builder.LambdaEntityExpressionBuilder;
import com.easy.query.core.util.EasyBeanUtil;
import org.junit.Ignore;

/**
 * @FileName: NameQueryFilter.java
 * @Description: 文件说明
 * @Date: 2023/3/8 10:24
 * @author xuejiaming
 */
@Ignore
public class NameQueryFilter implements PredicateFilterInterceptor {
    @Override
    public void configure(Class<?> entityClass, LambdaEntityExpressionBuilder lambdaEntityExpressionBuilder, WherePredicate<Object> sqlWherePredicate) {
        sqlWherePredicate.isNull("namex");
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
