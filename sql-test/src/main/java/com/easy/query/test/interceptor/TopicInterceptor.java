package com.easy.query.test.interceptor;

import com.easy.query.core.api.SQLClientApiFactory;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.extension.interceptor.PredicateFilterInterceptor;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.sql.builder.LambdaEntityExpressionBuilder;
import com.easy.query.test.entity.MyCategory;
import com.easy.query.test.entity.Topic;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * create time 2024/12/3 14:56
 * 文件说明
 *
 * @author xuejiaming
 */
public class TopicInterceptor implements PredicateFilterInterceptor {
    @Override
    public void configure(Class<?> entityClass, LambdaEntityExpressionBuilder lambdaEntityExpressionBuilder, WherePredicate<Object> wherePredicate) {
        SQLClientApiFactory sqlClientApiFactory = lambdaEntityExpressionBuilder.getRuntimeContext().getSQLClientApiFactory();
        ClientQueryable<MyCategory> queryable = sqlClientApiFactory.createQueryable(MyCategory.class, lambdaEntityExpressionBuilder.getRuntimeContext());
        wherePredicate.in("id",queryable.asTreeCTE(op->{
            op.setCTETableName("cte_"+ UUID.randomUUID().toString().replace("-",""));
        }).select("id"));
    }

    @Override
    public String name() {
        return "11";
    }

    @Override
    public boolean apply(@NotNull Class<?> entityClass) {
        return Topic.class.equals(entityClass);
    }
}
