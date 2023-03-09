package org.easy.query.core.configuration.types;

import org.easy.query.core.configuration.global.select.GlobalQueryFilterStrategy;
import org.easy.query.core.expression.lambda.Property;
import org.easy.query.core.expression.parser.abstraction.SqlPredicate;
import org.easy.query.core.query.SqlEntityQueryExpression;
import org.easy.query.core.util.EasyUtil;

/**
 * @FileName: EasyEntityQueryFilterConfiguration.java
 * @Description: 文件说明
 * @Date: 2023/3/7 22:29
 * @Created by xuejiaming
 */
public class EasyEntityQueryFilterConfiguration implements GlobalQueryFilterStrategy {
    @Override
    public String queryFilterName() {
        return null;
    }

    @Override
    public boolean apply(Class<?> entityClass) {
        return GlobalQueryFilterStrategy.class.isAssignableFrom(entityClass);
    }

    @Override
    public void configure(Class<?> entityClass, SqlEntityQueryExpression sqlEntityQueryExpression, SqlPredicate<?> sqlPredicate) {

        Property tenantProperty = EasyUtil.getLambdaProperty(entityClass, "tenantId", String.class);

        sqlPredicate.eq(tenantProperty, "123");
    }
}
