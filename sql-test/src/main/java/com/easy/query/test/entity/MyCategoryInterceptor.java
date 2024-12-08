package com.easy.query.test.entity;

import com.easy.query.core.basic.extension.interceptor.PredicateFilterInterceptor;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.sql.builder.LambdaEntityExpressionBuilder;

/**
 * create time 2024/12/7 23:08
 * 文件说明
 *
 * @author xuejiaming
 */
public class MyCategoryInterceptor implements PredicateFilterInterceptor {
    @Override
    public void configure(Class<?> entityClass, LambdaEntityExpressionBuilder lambdaEntityExpressionBuilder, WherePredicate<Object> wherePredicate) {

        wherePredicate.sqlNativeSegment("1=1");

//        wherePredicate.sqlNativeSegment("{0} = '1'",c->{
//            //c.expression(wherePredicate.getTable(),"id");
//            c.expression("id");
//        });


    }

    @Override
    public String name() {
        return "aaa";
    }

    @Override
    public boolean enable() {
        return false;
    }

    @Override
    public boolean apply(Class<?> entityClass) {
        return entityClass.equals(MyCategory.class);
    }
}
