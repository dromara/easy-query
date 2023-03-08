package org.easy.test;

import org.easy.query.core.configuration.types.AbstractGlobalQueryFilterConfiguration;
import org.easy.query.core.expression.lambda.Property;
import org.easy.query.core.expression.parser.abstraction.SqlPredicate;
import org.easy.query.core.util.EasyUtil;

/**
 * @FileName: NameQueryFilter.java
 * @Description: 文件说明
 * @Date: 2023/3/8 10:24
 * @Created by xuejiaming
 */
public class NameQueryFilter extends AbstractGlobalQueryFilterConfiguration {
    @Override
    protected void configure0(Class<?> entityClass, SqlPredicate<?> sqlPredicate) {
        Property property = EasyUtil.getLambdaProperty(entityClass, "name", String.class);
        sqlPredicate.isNull(property);
    }

    @Override
    public String queryFilterName() {
        return "nameQueryFilter";
    }

    @Override
    public boolean apply(Class<?> entityClass) {
        return TestUserMysql.class.equals(entityClass);
    }
}
